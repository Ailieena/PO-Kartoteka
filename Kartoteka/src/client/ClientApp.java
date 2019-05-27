package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import oczko.OczkoAction;
import oczko.OczkoGame;
import oczko.OczkoGameClientImpl;
import oczko.OczkoTurnDTOInterface;
import remoteinterface.ServerAPI;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

enum Suit {
    Clubs('c'), Diamonds('d'), Hearts('h'), Spades('s');
    private char suitID;

    private Suit(char id) {
        this.suitID = id;
    }

    @Override
    public String toString() {
        return String.valueOf(suitID);
    }
}

class Card {
    static double cardHeight = 100;
    private static Image back = new Image("client/assets/Backs/Card-Back-01.png", cardHeight, cardHeight, true, true);
    static double cardWidth = back.getWidth();
    static double cardOverlapH = -2 * cardWidth / 3;
    static double cardOverlapV = -cardHeight + cardWidth / 3;
    private Image front;
    private Suit suit;
    private int value;

    private static Map<String, Integer> valueFromString = Map.ofEntries(
            Map.entry("2", 2),
            Map.entry("3", 3),
            Map.entry("4", 4),
            Map.entry("5", 5),
            Map.entry("6", 6),
            Map.entry("7", 7),
            Map.entry("8", 8),
            Map.entry("9", 9),
            Map.entry("10", 10),
            Map.entry("Walet", 11),
            Map.entry("Dama", 12),
            Map.entry("Król", 13),
            Map.entry("As", 1)
    );

    private static Map<String, Suit> suitFromString = Map.of(
            "Pik", Suit.Spades,
            "Kier", Suit.Hearts,
            "Karo", Suit.Diamonds,
            "Trefl", Suit.Clubs
    );


    Card(kartoteka.Card rmiCard) throws RemoteException {
        this(
                valueFromString.get(rmiCard.getValue()),
                suitFromString.get(rmiCard.getColor())
        );

    }

    Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
        this.front = new Image("client/assets/Cards/Classic/" + suit + value + ".png", cardHeight, cardHeight, true, true);
    }

    public Image getBack() {
        return back;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public Image getFront() {
        return front;
    }

    public static ArrayList<Card> createDeck(int lowest) {
        ArrayList<Card> deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (int i = lowest; i <= 13; i++) {
                deck.add(new Card(i, suit));
            }
            deck.add(new Card(1, suit));
        }
        return deck;
    }

    @Override
    public String toString() {
        String figure;
        String[] figures = {"Ace", "Jack", "Queen", "King"};
        if (value == 1) figure = "Ace";
        else if (value > 10) figure = figures[value % 10];
        else figure = String.valueOf(value);
        return figure + " of " + suit.name();
    }
}

class Hand {
    private ArrayList<Card> cards;
    private Pane assignedContainer;
    private boolean visible;
    private boolean rotated;
    private boolean clickable;
    private ArrayList<Hand> hands;

    public Hand(Pane container, boolean visible, boolean clickable) {
        this.visible = visible;
        this.clickable = clickable;
        this.cards = new ArrayList<Card>();
        this.assignedContainer = container;
        if (container instanceof VBox) {
            this.rotated = true;
            ((VBox) this.assignedContainer).setSpacing(Card.cardOverlapV);
            ((VBox) this.assignedContainer).setAlignment(Pos.CENTER);
        }
        if (container instanceof HBox) {
            this.rotated = false;
            ((HBox) this.assignedContainer).setSpacing(Card.cardOverlapH);
            ((HBox) this.assignedContainer).setAlignment(Pos.CENTER);
        }
    }

    private void clickHandler(Card evokedCard) {
        if (ClientApp.currentHand != this.hands.indexOf(this)) return;
        if (this.hands.get(4).cards.size() == 4) {
            this.hands.get(4).clear();
        }
        this.removeCard(evokedCard);
        this.hands.get(4).addCard(evokedCard);
        ClientApp.currentHand = (ClientApp.currentHand + 1) % 4;
    }

    private void updateContainer() {
        assignedContainer.getChildren().clear();
        for (Card card : cards) {
            Image _img = (visible ? card.getFront() : card.getBack());
            ImageView img = new ImageView(_img);
            if (rotated) img.setRotate(90);
            if (clickable) img.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                clickHandler(card);
                mouseEvent.consume();
            });
            assignedContainer.getChildren().add(img);
        }
    }

    public void setHands(ArrayList<Hand> hands) {
        this.hands = hands;
    }

    public void addCard(Card card) {
        cards.add(card);
        updateContainer();
    }

    public void removeCard(Card card) {
        cards.remove(card);
        updateContainer();
    }

    public void drawCards(List<Card> deck, int n) {
        while (deck.size() > 0 && n > 0) {
            cards.add(deck.remove(0));
            n--;
        }
        updateContainer();
    }

    public void clear() {
        cards.clear();
        updateContainer();
    }

    public boolean isVisible() {
        return visible;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

}

public class ClientApp extends Application {
    static int windowWidth = 800;
    static int windowHeight = 600;
    static int currentHand = 0;

    private Stage primaryStage;

    private final FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
    private final FXMLLoader roomSelectorLoader = new FXMLLoader(getClass().getResource("SelectRoomWindow.fxml"));
    private final FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("ClientApp.fxml"));

    private final LoginWindow loginWindow = new LoginWindow(this);
    private final SelectRoomWindow roomSelectorWindow = new SelectRoomWindow(this);

    private Scene loginScene;
    private Scene choiceScene;
    private Scene gameScene;
    private ServerAPI server;
    private OczkoGame game;
    private OczkoGameClientImpl callbackClient;
    private int clientID;
    private Hand myHand;
    private Hand leftHand;
    private Hand rightHand;
    private Hand topHand;
    private List<Hand> availableHands;
    private Map<Integer, Hand> hands = new HashMap<>();

    @FXML
    private Button onPassBtn;

    @FXML
    private Button onGetCardBtn;

    public ClientApp() {
        loginLoader.setController(loginWindow);
        roomSelectorLoader.setController(roomSelectorWindow);
    }

    private void dealCards(ArrayList<Hand> hands, ArrayList<Card> deck, int n) {
        Collections.shuffle(deck);
        for (Hand hand : hands) hand.drawCards(deck, n);
    }

    @FXML
    private void exitApp(ActionEvent event) throws RemoteException {
        System.out.println("Closing " + game);
        if (game != null) {
            game.disconnect(callbackClient);
            callbackClient = null;
            game = null;
        }

//        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        primaryStage.setScene(choiceScene);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void startGame(int roomID) throws IOException, NotBoundException {

        game = (OczkoGame) Naming.lookup("rmi://localhost:1900/game/" + roomID);
        callbackClient = new OczkoGameClientImpl((dto) -> {
            try {
                onMyTurn(dto);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Joining game " + game);

        clientID = game.join(callbackClient);

        if(gameLoader.getController() != this) {
            gameLoader.setController(this);
            VBox root = gameLoader.load();
            gameScene = new Scene(root);
            HBox windowContent = (HBox) root.getChildren().get(0);
            AnchorPane table = (AnchorPane) windowContent.getChildren().get(0);
            AnchorPane sideBar = (AnchorPane) windowContent.getChildren().get(1);

            myHand = new Hand((HBox) table.getChildren().get(3), true, false);
            leftHand = new Hand((VBox) table.getChildren().get(1), false, false);
            rightHand = new Hand((VBox) table.getChildren().get(2), false, false);
            topHand = new Hand((HBox) table.getChildren().get(0), false, false);
        }
        primaryStage.setScene(gameScene);

        int maxNumberOfPlayers = game.getMaxNumberOfPlayers();

        availableHands = new ArrayList<>();


        if(maxNumberOfPlayers == 2) {
            availableHands.add(topHand);
        }
        if(maxNumberOfPlayers == 3) {
            availableHands.addAll(List.of(leftHand, topHand));
        }
        if(maxNumberOfPlayers == 4) {
            availableHands.addAll(List.of(leftHand, topHand, rightHand));
        }

//        disableButtons();

    }

    @FXML
    private void onGetCard() throws RemoteException {
        disableButtons();
        kartoteka.Card rmiCard = game.requestCard(clientID);
        System.out.println("Get next card. Got: " + rmiCard);
        Card card = new Card(rmiCard);
        myHand.addCard(card);

    }

    @FXML
    private void onPass() throws RemoteException {
        disableButtons();
        System.out.println("Skip. Waiting for the game to finish");
        game.requestPass(clientID);

    }

    private void disableButtons() {
        onPassBtn.setDisable(true);
        onGetCardBtn.setDisable(true);
    }

    private void enableButtons() {
        onPassBtn.setDisable(false);
        onGetCardBtn.setDisable(false);
    }

    private void onWin(int winnerID) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over!");
        alert.setHeaderText(null);

        if(winnerID == clientID) {
            alert.setContentText("Wygrałeś/wygrałaś! Gratulacje! :)");
        } else {
            alert.setContentText("Wygrał gracz " + winnerID);
        }

        alert.showAndWait();
    }

    private void onMyTurn(OczkoTurnDTOInterface action) throws RemoteException {
        System.out.println(action.getPlayer() + " " + action.getAction() + " " + action.isMyTurn());
        int playerID = action.getPlayer();

        if (action.getAction() == OczkoAction.WIN) {
            Platform.runLater(() -> {
                disableButtons();
                onWin(playerID);
            });

            return;
        }

        if(action.isMyTurn()) {
            Platform.runLater(() -> {
                enableButtons();
            });

        }



        if(playerID < 0) {
            return;
        }

        if(playerID != clientID && !hands.containsKey(playerID)) {
            hands.put(playerID, availableHands.remove(0));

        }

        if(playerID != clientID) {
            Platform.runLater(()->{
                Hand hand = hands.get(playerID);

                // Dummy invisible card
                hand.addCard(new Card(2, Suit.Clubs));
            });
        }



    }

    public void setServer(ServerAPI server) {
        this.server = server;
    }

    public void startRoomSelect() throws IOException {
        Parent roomSelect = roomSelectorLoader.load();
        roomSelectorWindow.setServer(server);
        roomSelectorWindow.loadRooms();

        if(choiceScene == null) choiceScene = new Scene(roomSelect);
        primaryStage.setScene(choiceScene);
        primaryStage.show();

    }

    public void startLogin() throws IOException {
        Parent loginRoot = loginLoader.load();
        primaryStage.setTitle("Kartoteka");

        if(loginScene == null) loginScene = new Scene(loginRoot);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        startLogin();
    }

}

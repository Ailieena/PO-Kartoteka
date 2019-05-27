package poker;

import client.ClientApp;
import game.CardSet;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.*;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.util.Duration;
import poker.PokerClient;
import poker.PokerServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

public class PokerStage {

    static int currentHand = 0;
    PokerServer g = null;
    PokerClient c = null;
    boolean turnBeginning = true;

    public Scene getScene() throws Exception {
        VBox root = FXMLLoader.load(getClass().getResource("PokerStage.fxml"));
        Scene scene = new Scene(root);

        HBox windowContent = (HBox) root.getChildren().get(0);
        AnchorPane table = (AnchorPane) windowContent.getChildren().get(0);
        AnchorPane sideBar = (AnchorPane) windowContent.getChildren().get(1);
        ObservableList<Node> buttons = sideBar.getChildren();

        TextField betField = (TextField) buttons.get(4);

        ((Button) buttons.get(0)).setOnAction(e -> {
            try {
                step(0);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        ((Button) buttons.get(1)).setOnAction(e -> {
            try {
                step(1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        ((Button) buttons.get(2)).setOnAction(e -> {
            try {
                step(2);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        ((Button) buttons.get(5)).setOnAction(e -> {
            try {
                step(Integer.valueOf(betField.getText()) - c.getMaxBet() + 2);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Hand myHand = new Hand((HBox) table.getChildren().get(3), true, false);
        Hand leftHand = new Hand((VBox) table.getChildren().get(1), false, false);
        Hand rightHand = new Hand((VBox) table.getChildren().get(2), false, false);
        Hand topHand = new Hand((HBox) table.getChildren().get(0), false, false);
        Hand tableHand = new Hand((HBox) table.getChildren().get(4), true, false);

        Text leftStar = (Text) table.getChildren().get(5);
        Text bottomStar = (Text) table.getChildren().get(6);
        Text topStar = (Text) table.getChildren().get(7);

        Text betBottom = (Text) table.getChildren().get(8);
        Text betLeft = (Text) table.getChildren().get(9);
        Text betRight = (Text) table.getChildren().get(10);

        Text coinBottom = (Text) table.getChildren().get(11);
        Text coinLeft = (Text) table.getChildren().get(12);
        Text coinRight = (Text) table.getChildren().get(13);

        Text pot = (Text) table.getChildren().get(14);

        ArrayList<Hand> hands = new ArrayList<Hand>(Arrays.asList(myHand, leftHand, topHand, rightHand, tableHand));
        for (Hand hand : hands) hand.setHands(hands);

        setup();


        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000 / 3), e -> {
            update(new Hand[]{myHand, leftHand, topHand}, tableHand, new Text[]{bottomStar, leftStar, topStar},
                    new Text[]{betBottom, betLeft, betRight}, betField, new Text[]{coinBottom, coinLeft, coinRight},
                    pot);
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        return scene;
    }

    public void update(Hand[] hands, Hand board, Text[] stars, Text[] bets, TextField betField, Text[] coins, Text pot) {
        try {
            if (c != null) {
                if (c.isGameActive()) {
                    int id = c.getPlayersId();
                    int[] betsVal = c.getCurrentBet();
                    int[] coinsVal = c.getCoins();
                    CardSet[] cs = c.getPlayersCards();

                    pot.setText("Pot: " + c.getPot());

                    for (int i = 0; i < 3; i++) {
                        if (cs[(id + i) % 3] == null) {
                            bets[i].setVisible(false);
                        } else {
                            bets[i].setVisible(true);
                            bets[i].setText(Integer.toString(betsVal[(id + i) % 3]));
                        }
                    }

                    for (int i = 0; i < 3; i++) {
                        if (cs[(id + i) % 3] == null) {
                            coins[i].setVisible(false);
                        } else {
                            coins[i].setVisible(true);
                            coins[i].setText("Coins: " + coinsVal[(id + i) % 3]);
                        }
                    }

                    if (c.isYourTurn()) {
                        if (turnBeginning) {
                            betField.setText(String.valueOf(c.getMaxBet()));
                            turnBeginning = false;
                        }
                        stars[2].setVisible(false);
                        stars[1].setVisible(false);
                        stars[0].setVisible(true);
                    } else {
                        turnBeginning = true;
                        betField.setText(String.valueOf(c.getMaxBet()));
                        stars[0].setVisible(false);
                        stars[1].setVisible(false);
                        stars[2].setVisible(false);
                        stars[(c.getCurrentPlayer() - id + 3) % 3].setVisible(true);
                    }
                    board.clear();
                    hands[0].clear();
                    hands[1].clear();
                    hands[2].clear();
                    if (cs[id] != null) {
                        for (game.Card card : cs[id].getSetList()) {
                            hands[0].addCard(new Card(card.getValue() + 1, valToSuit(card.getSuit())));
                        }
                    }
                    if (cs[(id + 1) % 3] != null) {
                        for (game.Card card : cs[(id + 1) % 3].getSetList()) {
                            hands[1].addCard(new Card(card.getValue() + 1, valToSuit(card.getSuit())));
                        }
                    }
                    if (cs[(id + 2) % 3] != null) {
                        for (game.Card card : cs[(id + 2) % 3].getSetList()) {
                            hands[2].addCard(new Card(card.getValue() + 1, valToSuit(card.getSuit())));
                        }
                    }
                    CardSet cs2 = c.getBoardCards();
                    for (game.Card card : cs2.getSetList()) {
                        board.addCard(new Card(card.getValue() + 1, valToSuit(card.getSuit())));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setup() {
        try {
            g = (PokerServer) Naming.lookup("rmi://localhost:1900/game_poker");
            c = (PokerClient) g.getClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Suit valToSuit(int v) {
        if (v == 0) return Suit.Clubs;
        else if (v == 1) return Suit.Diamonds;
        else if (v == 2) return Suit.Hearts;
        else return Suit.Spades;
    }

    public void step(int action) throws RemoteException {
        if (c != null) {
            c.step(action);
        }
    }


}

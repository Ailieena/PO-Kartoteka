package gui.blackjack;

import gui.Card;
import gui.CustomScene;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import oczko.*;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class OczkoGameScreen implements CustomScene {

    Scene scene;

    private static final String OCZEKIWANIE_NA_GRACZY = "Oczekiwanie na pozostałych graczy";
    private static final String OCZEKIWANIE_NA_TWOJA_TURE = "Oczekiwanie na twoją turę";
    private static final String TWOJA_TURA = "Twoja tura!";

    private int width = 900;
    private int height = 600;
    private List<Card> cards = new ArrayList<>();
    private BorderPane layout = new BorderPane();
    private VBox cardArea = new VBox();
    private VBox centerArea = new VBox();
    private HBox cardLayout = new HBox();
    private HBox buttonLayout = new HBox();
    private Button getNextCardBtn = new Button("Daj kartę");
    private Button skipTurnBtn = new Button("Pass");

    private VBox leftCardArea = new VBox();
    private VBox rightCardArea = new VBox();

    private Text message = new Text(OCZEKIWANIE_NA_GRACZY);
    private Text log = new Text("");

    private EventHandler<MouseEvent> getNextCardEvent;
    private EventHandler<MouseEvent> skipTurnEvent;
    private OczkoGameClient callbackClient;
    private OczkoGame gameClient;
    private int clientID;

    public OczkoGameScreen(OczkoGame gameClient) throws FileNotFoundException, RemoteException {
        this.gameClient = gameClient;
        this.callbackClient = new OczkoGameClientImpl((dto) -> {
            try {
                onMyTurn(dto);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        clientID = this.gameClient.join(this.callbackClient);

        cardArea.setAlignment(Pos.CENTER);
        buttonLayout.setAlignment(Pos.CENTER);
        cardLayout.setAlignment(Pos.CENTER);

        message.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 16 ));

        buttonLayout.getChildren().addAll(getNextCardBtn, skipTurnBtn);

        getNextCardEvent = e -> {
            onGetNextCardClicked();
        };

        skipTurnEvent = e -> {
            try {
                onSkipTurnClicked();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        };


        getNextCardBtn.addEventHandler(MouseEvent.MOUSE_RELEASED, getNextCardEvent);
        skipTurnBtn.addEventHandler(MouseEvent.MOUSE_RELEASED, skipTurnEvent);


        cardArea.getChildren().addAll(cardLayout, buttonLayout, log);

        layout.setStyle("-fx-background-color: #3a9b71");
        layout.setBottom(cardArea);
        layout.setCenter(message);

        leftCardArea.setSpacing(-100);
        rightCardArea.setSpacing(-100);

        layout.setLeft(leftCardArea);
        layout.setRight(rightCardArea);

        scene = new Scene(layout, width, height);

        disableButtons();

    }

    private void setMessage(String s) {
        message.setText(s);
    }

    private void disableButtons() {
        getNextCardBtn.setDisable(true);
        skipTurnBtn.setDisable(true);
    }

    private void enableButtons() {
        getNextCardBtn.setDisable(false);
        skipTurnBtn.setDisable(false);
    }

    private void handleWin(int winner) {
        if(winner == clientID) {
            setMessage("Gratulacje!");
        } else {
            setMessage("Gracz " + winner + " wygrał");
        }

        disableButtons();
    }

    private void onMyTurn(OczkoTurnDTOInterface action) throws RemoteException {
        System.out.println(action.getPlayer() + " " + action.getAction() + " " + action.isMyTurn());
        if (action.getAction() == OczkoAction.WIN) {
            Platform.runLater(() -> {
                log.setText("WIN");
                try {
                    handleWin(action.getPlayer());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });

            return;
        }

        if(action.isMyTurn()) {
            Platform.runLater(() -> {
                setMessage(TWOJA_TURA);
                enableButtons();
                log.setText("");
            });

        } else {
            Platform.runLater(()->{
                try{
                    if(action.getPlayer() == 1) {

                        leftCardArea.getChildren().add(createReversedCard().getImageView());
                    } else if(action.getPlayer() == 2) {
                        rightCardArea.getChildren().add(createReversedCard().getImageView());
                    }
                } catch (FileNotFoundException | RemoteException ex) {
                    System.out.println("File not found");
                }
            });


            log.setText("Gracz " + action.getPlayer() + " wykonal " + action.getAction().toString());
        }
    }

    private void onSkipTurnClicked() throws RemoteException {

        setMessage(OCZEKIWANIE_NA_GRACZY);
        gameClient.requestPass(clientID);
        disableButtons();
    }

    private void onGetNextCardClicked() {

        try {
            String card = gameClient.requestCard(clientID);
            addCard(new Card("Client/img/"+card+".png"));
        } catch(Exception ex) {}
        setMessage(OCZEKIWANIE_NA_TWOJA_TURE);
        disableButtons();
    }

    private Card createReversedCard() throws FileNotFoundException {
        Card c = new Card("C:\\Users\\Karolina\\Frytki\\Client\\img\back.png");
        c.rotate(90);

        c.setMovable(false);
        return c;
    }

    public void addCard(Card card) {
        card.setMovable(false);
        cards.add(card);

        cardLayout.getChildren().add(card.getImageView());
    }

    public Scene getScene() {
        return scene;
    }
}

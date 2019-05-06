import javafx.application.*;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

enum Suit {
    Clubs('c'), Diamonds('d'), Hearts('h'), Spades('s');
    private char suitID;
    private Suit(char id) {this.suitID = id;}
    @Override
    public String toString() {return String.valueOf(suitID);}
}

class Card {
    static double cardHeight = 100;
    private static Image back = new Image("assets/Backs/Card-Back-01.png", cardHeight, cardHeight, true, true);
    static double cardWidth = back.getWidth();
    private Image front;
    private Suit suit;
    private int value;

    Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
        this.front = new Image("assets/Cards/Classic/"+suit+value+".png", cardHeight, cardHeight, true, true);
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

    @Override
    public String toString() {
        String figure;
        String[] figures = {"Ace", "Jack", "Queen", "King"};
        if(value == 1) figure = "Ace";
        else if(value > 10) figure = figures[value%10];
        else figure = String.valueOf(value);
        return figure + " of " + suit.name();
    }
}

class Hand {
    private ArrayList<Card> cards;
    private boolean visible;

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public Hand(boolean visible, Card... cards) {
        this.visible = visible;
        this.cards = new ArrayList<>(Arrays.asList(cards));
    }

    public Hand(boolean visible, List<Card> cards) {
        this.visible = visible;
        this.cards = new ArrayList<>(cards);
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
    private HBox tableDeck;
    static ArrayList<Card> cards = new ArrayList<>();
    static {
        for(int i=1; i<=13; i++) {
            cards.add(new Card(i, Suit.Clubs));
            cards.add(new Card(i, Suit.Diamonds));
            cards.add(new Card(i, Suit.Hearts));
            cards.add(new Card(i, Suit.Spades));
        }
    }

    private void dealCards(HBox me, HBox opponent) {

        Collections.shuffle(cards);
        HBox[] t = {me, opponent};
        for(int k=0; k<2; k++) {
            HBox box = t[k];
            for(int i=0; i<13; i++) {
                Card card = cards.get(13*k+i);
                ImageView img = new ImageView(card.getFront());
                img.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if(img.getParent().equals(this.tableDeck)) return;
                    System.out.println(card);
                    if(this.tableDeck.getChildren().size() == 2) this.tableDeck.getChildren().clear();
                    box.getChildren().remove(img);
                    this.tableDeck.getChildren().add(img);
                    event.consume();
                });
                box.getChildren().add(img);
            }
        }
    }

    @FXML
    private void exitApp(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("My App");
        VBox root = FXMLLoader.load(getClass().getResource("ClientApp.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        HBox windowContent = (HBox) root.getChildren().get(0);
        AnchorPane table = (AnchorPane) windowContent.getChildren().get(0);
        AnchorPane sideBar = (AnchorPane) windowContent.getChildren().get(1);
        ObservableList<Node> buttons = sideBar.getChildren();

        HBox myDeck = (HBox) table.getChildren().get(3);
        myDeck.setSpacing(-48);
        myDeck.setAlignment(Pos.CENTER);

        HBox opponentDeck = (HBox) table.getChildren().get(0);
        opponentDeck.setSpacing(-48);
        opponentDeck.setAlignment(Pos.CENTER);

        tableDeck = (HBox) table.getChildren().get(4);
        tableDeck.setAlignment(Pos.CENTER);

        dealCards(myDeck, opponentDeck);


        primaryStage.show();
    }

}

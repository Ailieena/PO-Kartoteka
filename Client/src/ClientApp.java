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
    static double cardOverlapH = -2*cardWidth/3;
    static double cardOverlapV = -cardHeight+cardWidth/3;
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

    public static ArrayList<Card> createDeck(int lowest) {
        ArrayList<Card> deck = new ArrayList<>();
        for(Suit suit : Suit.values()) {
            for(int i=lowest; i<=13; i++) {
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
        if(value == 1) figure = "Ace";
        else if(value > 10) figure = figures[value%10];
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
        if(container instanceof VBox) {
            this.rotated = true;
            ((VBox)this.assignedContainer).setSpacing(Card.cardOverlapV);
            ((VBox)this.assignedContainer).setAlignment(Pos.CENTER);
        }
        if(container instanceof HBox) {
            this.rotated = false;
            ((HBox)this.assignedContainer).setSpacing(Card.cardOverlapH);
            ((HBox)this.assignedContainer).setAlignment(Pos.CENTER);
        }
    }

    private void clickHandler(Card evokedCard) {
        if(ClientApp.currentHand != this.hands.indexOf(this)) return;
        if(this.hands.get(4).cards.size() == 4) {
            this.hands.get(4).clear();
        }
        this.removeCard(evokedCard);
        this.hands.get(4).addCard(evokedCard);
        ClientApp.currentHand = (ClientApp.currentHand+1)%4;
    }

    private void updateContainer() {
        assignedContainer.getChildren().clear();
        for(Card card : cards) {
            Image _img = (visible ? card.getFront() : card.getBack());
            ImageView img = new ImageView(_img);
            if(rotated) img.setRotate(90);
            if(clickable) img.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
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
        while(deck.size() > 0 && n > 0) {
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

    private void dealCards(ArrayList<Hand> hands, ArrayList<Card> deck, int n) {
        Collections.shuffle(deck);
        for(Hand hand : hands) hand.drawCards(deck, n);
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

        Hand myHand = new Hand((HBox) table.getChildren().get(3), true, true);
        Hand leftHand = new Hand((VBox) table.getChildren().get(1), false, true);
        Hand rightHand = new Hand((VBox) table.getChildren().get(2), true, true);
        Hand topHand = new Hand((HBox) table.getChildren().get(0), true, true);
        Hand tableHand = new Hand((HBox) table.getChildren().get(4), true, false);
        ArrayList<Hand> hands = new ArrayList<Hand>(Arrays.asList(myHand, leftHand, topHand, rightHand, tableHand));
        for(Hand hand : hands) hand.setHands(hands);


        ArrayList<Card> deck = Card.createDeck(2);
        dealCards(hands, deck, 13);

        primaryStage.show();
    }

}

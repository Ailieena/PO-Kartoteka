package game;

import java.io.Serializable;

public class Card implements Serializable {

    private int value;
    private int suit;

    public Card(int value, int suit) {
        this.value = value;
        this.suit = suit;
    }

    public int compareTo(Card card) {
        return getValue() - card.getValue();
    }

    public boolean equals(Object o) {
        if (!(o instanceof Card)) return false;
        return (((Card) o).getValue() == getValue() && ((Card) o).getSuit() == getSuit());
    }

    public int getValue() {
        return value;
    }

    public int getSuit() {
        return suit;
    }

    public void set(int value, int suit) {
        this.value = value;
        this.suit = suit;
    }

    public Card clone() {
        return new Card(value, suit);
    }

    public String toString() {
        return "(" + value + "," + suit + ")";
    }

}

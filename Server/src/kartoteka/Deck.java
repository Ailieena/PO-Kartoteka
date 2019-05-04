package kartoteka;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Deck {
    LinkedList<Card> deck;

    public Deck() {
        deck = new LinkedList<>();
    }

    public void addCard(Card card) {
        deck.add(card);
    }

    public boolean removeCard(Card c) {
        return deck.removeFirstOccurrence(c);
    }

    public boolean contains(Card card) {
        return deck.contains(card);
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card takeCard() {
        return deck.pollFirst();
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }
}

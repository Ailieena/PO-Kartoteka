package kartoteka;

import java.util.Collections;
import java.util.LinkedList;

public class Deck {
    LinkedList<CardImpl> deck;

    public Deck() {
        deck = new LinkedList<>();
    }

    public void addCard(CardImpl cardImpl) {
        deck.add(cardImpl);
    }

    public boolean removeCard(CardImpl c) {
        return deck.removeFirstOccurrence(c);
    }

    public boolean contains(CardImpl cardImpl) {
        return deck.contains(cardImpl);
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public CardImpl takeCard() {
        return deck.pollFirst();
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }
}

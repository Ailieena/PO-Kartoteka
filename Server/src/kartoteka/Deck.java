package kartoteka;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Deck {
    LinkedList<Card> cards = null;
    LinkedList<Card> deck = null;

    public void shuffleAllCards() {
        deck = new LinkedList<>();
        for(Card c : cards) {
            deck.add(c.clone());
        }
        shuffle();
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

    public boolean contains(Card card) {
        return deck.contains(card);
    }

    public void addCard(Card card) {
        if(!contains(card)) deck.add(card);
    }

    public void giveCard(Player p, Card c) {
    }


}

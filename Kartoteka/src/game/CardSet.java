package game;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;

public class CardSet implements Serializable {

    private LinkedList<game.Card> deck;

    public CardSet() {
        deck = new LinkedList<>();
    }

    public void addCard(Card c) {
        deck.add(c);
    }

    public void addSet(CardSet d) {
        for (Card c : d.getSetList()) {
            addCard(c.clone());
        }
    }

    public void clear() {
        deck.clear();
    }

    public boolean containsCard(Card c) {
        return deck.contains(c);
    }

    public LinkedList<Card> getSetList() {
        return deck;
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }

    public boolean removeCard(Card c) {
        return deck.removeFirstOccurrence(c);
    }

    public Card removeCard(int idx) {
        return deck.remove(idx);
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public int size() {
        return deck.size();
    }

    public Card takeTopCard() {
        return deck.poll();
    }

    public CardSet takeTopCard(int k) {
        CardSet cs = new CardSet();
        for (int i = 0; i < k && !isEmpty(); i++) {
            cs.addCard(takeTopCard());
        }
        return cs;
    }

    public String toString() {
        return deck.toString();
    }
}

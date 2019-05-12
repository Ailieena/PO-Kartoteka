package kartoteka;

import java.util.LinkedList;
import java.util.Random;

public class Player {
    LinkedList<Card> hand;
    Game game;

    public Player(Game game) {
        hand = new LinkedList<>();
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public LinkedList<Card> getHand() {
        return hand;
    }

    public int getHandSize() {
        return hand.size();
    }

    public void addCard(Card c) {
        hand.add(c);
    }

    public boolean removeCard(Card c) {
       return hand.removeFirstOccurrence(c);
    }

    public Card removeCard(int index) {
        return hand.remove(index);
    }

    public Card randomCard() {
        if(getHandSize() < 0) return null;

        Random rand = new Random();
        return hand.get(rand.nextInt(hand.size()));
    }

    public void makeMove(int k) {
        this.game.makeMove(this, k);
    }

    public String toString() {
        return hand.toString();
    }
}

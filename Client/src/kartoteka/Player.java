package kartoteka;

import java.util.LinkedList;
import java.util.Random;

public class Player {
    LinkedList<CardImpl> hand;
    Game game;

    public Player(Game game) {
        hand = new LinkedList<>();
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public LinkedList<CardImpl> getHand() {
        return hand;
    }

    public int getHandSize() {
        return hand.size();
    }

    public void addCard(CardImpl c) {
        hand.add(c);
    }

    public boolean removeCard(CardImpl c) {
       return hand.removeFirstOccurrence(c);
    }

    public CardImpl removeCard(int index) {
        return hand.remove(index);
    }

    public CardImpl randomCard() {
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

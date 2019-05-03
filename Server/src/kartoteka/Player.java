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

    public int size() {
        return hand.size();
    }

    public Card randomCard() {
        if(size() < 0) return null;
        Random rand = new Random();
        return hand.get(rand.nextInt(hand.size()));
    }

    public void makeMove(int k) {

    }
}

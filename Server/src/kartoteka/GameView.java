package kartoteka;

import java.util.LinkedList;

public abstract class GameView {
    public abstract int getPlayerId();
    public abstract LinkedList<Card> getPlayerHand();
    public abstract int[] getHandSizes();
    public abstract boolean isGameOver();
    public abstract int getWinner();
}

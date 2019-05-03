package kartoteka;

import java.util.LinkedList;

/*

    NOT DONE YET

 */

public abstract class Game {

    LinkedList<Player> players;
    int maxPlayers;
    int actualPlayer;

    public Game(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int playersNumber() {
        return players.size();
    }

    public void makeMove(Player p, int move) {
        if(players.indexOf(p) != actualPlayer) return;
        step(p, move);
    }

    public abstract void step(Player p, int move);
}

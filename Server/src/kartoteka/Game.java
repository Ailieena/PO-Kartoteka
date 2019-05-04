package kartoteka;

import java.util.LinkedList;

/*

    NOT DONE YET

 */

public abstract class Game {

    int maxPlayers;
    Player[] players;
    Deck board;
    Deck hiddenCards;

    public Game(int maxPlayers, Deck board, Deck hiddenCards) {
        this.maxPlayers = maxPlayers;
        players = new Player[maxPlayers];
        this.board = board;
        this.hiddenCards = hiddenCards;
    }

    public int playersNumber() {
        int n = 0;
        for(Player p : players) {
            if(p != null) n++;
        }
        return n;
    }

    public void makeMove(Player p, int move) {
        if(players[getActualPlayer()] != p) return;
        step(p, move);
        nextPlayer();
    }

    public void joinGame(Player player, int place) {
        if(place >= maxPlayers || players[place] != null) return;
        players[place] = player;
    }

    public abstract void step(Player p, int move);
    public abstract int nextPlayer();
    public abstract int getActualPlayer();
    public abstract boolean isGameOver();
    public abstract int getWinner();


}

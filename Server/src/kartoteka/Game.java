package kartoteka;

import java.util.LinkedList;

/*

    NOT DONE YET

 */

public abstract class Game {

    int maxPlayers;
    Player[] players;
    Deck deck;
    Deck hiddenCards;

    public Game(int maxPlayers, Deck board, Deck hiddenCards) {
        this.maxPlayers = maxPlayers;
        players = new Player[maxPlayers];
        this.deck = board;
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

    public boolean joinGame(Player player, int place) {
        if (place >= maxPlayers || players[place] != null) return false;
        players[place] = player;
        return true;
    }

    public Player getPlayer(int id) {
        return players[id];
    }

    public int getDeckSize() {
        return deck.deck.size();
    }

    public Deck getDeck() {
        return deck;
    }

    public Deck getHiddenCards() {
        return hiddenCards;
    }

    public GameView getGameView(int id) {
        boolean isGameOver = isGameOver();
        int winner = getWinner();
        int[] handSizes = new int[maxPlayers];

        for(int i=0; i<this.maxPlayers; i++) {
            if(players[i] == null) handSizes[i] = 0;
            else handSizes[i] = players[i].getHandSize();
        }

        return new GameView() {
            @Override
            public int getPlayerId() {
                return id;
            }

            @Override
            public LinkedList<Card> getPlayerHand() {
                return players[id].getHand();
            }

            @Override
            public int[] getHandSizes() {
                return handSizes;
            }

            @Override
            public boolean isGameOver() {
                return isGameOver;
            }

            @Override
            public int getWinner() {
                return winner;
            }
        };
    }

    public abstract void step(Player p, int move);
    public abstract int nextPlayer();
    public abstract int getActualPlayer();
    public abstract boolean isGameOver();
    public abstract int getWinner();
}

package poker;

import game.*;

import java.rmi.RemoteException;

public class Poker extends GameServer implements PokerServer {

    private int smallBlind;
    private int bigBlind;
    private int currentPlayer;
    private boolean isGameActive;
    private boolean[] isActive;
    private int beginId;
    private int bets[];
    private int coins[];
    private int startingCoins;
    private int pot;
    private int round;
    private int winner;

    private int lastPlayer;

    private CardSet deck;
    private CardSet board;
    private CardSet[] cards;

    public Poker(int maxPlayers, int smallBlind, int bigBlind, int startingCoins) throws RemoteException {
        super(maxPlayers);
        this.smallBlind = smallBlind;
        this.bigBlind = bigBlind;
        this.startingCoins = startingCoins;
        isGameActive = false;
        beginId = -1;
        cards = new CardSet[maxPlayers];
        isActive = new boolean[maxPlayers];
        bets = new int[maxPlayers];
        coins = new int[maxPlayers];

        for (int i = 0; i < maxPlayers; i++) {
            coins[i] = 0;
        }
    }

    @Override
    public Client getNewClient() throws RemoteException {
        return new PokerGameClient(this);
    }

    @Override
    public void onJoin(int id) {
        coins[id] = startingCoins;
        try {
            if (getPlayersNumber() >= 3) {
                reset();
                Thread.sleep(2000);
                isGameActive = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCurrentPlayerId() {
        return currentPlayer;
    }

    @Override
    public int nextPlayer() {
        currentPlayer = getNextActivePlayerId(currentPlayer);
        return currentPlayer;
    }

    @Override
    public void reset() {
        board = new CardSet();
        deck = new StandardDeck();
        deck.shuffle();

        for (int i = 0; i < getMaxPlayers(); i++) {
            if (isInGame(i)) {
                isActive[i] = true;
                cards[i] = new CardSet();
                cards[i].addSet(deck.takeTopCard(2));
                bets[i] = 0;
            } else {
                cards[i] = null;
            }
        }

        beginId = getNextPlayerId(beginId);
        lastPlayer = getNextActivePlayerId(beginId);
        bets[beginId] = smallBlind;
        coins[beginId] -= smallBlind;
        bets[getNextActivePlayerId(beginId)] = bigBlind;
        coins[getNextActivePlayerId(beginId)] -= bigBlind;
        pot = smallBlind + bigBlind;
        currentPlayer = getNextActivePlayerId(getNextActivePlayerId(beginId));

        round = 0;
        winner = -1;
    }

    @Override
    public boolean step(int id, int action) {
        /*

        0: pass
        1: check
        2: call
        3>= : bet

         */

        if (action == 0) {
            isActive[id] = false;
            cards[id] = null;
            try {

                if (isGameOver()) {
                    for (int i = 0; i < getMaxPlayers(); i++) {
                        if (isActive[i]) {
                            winner = i;
                            break;
                        }
                    }
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (action == 1) {
            if (bets[id] < getMaxBet()) return false;
        } else if (action == 2) {
            if (bets[id] == getMaxBet()) return false;
            else {
                pot += getMaxBet() - bets[id];
                coins[id] -= getMaxBet() - bets[id];
                bets[id] = getMaxBet();
            }
        } else {
            int bet = action - 2;
            pot += getMaxBet() + bet - bets[id];
            coins[id] -= getMaxBet() + bet - bets[id];
            bets[id] = getMaxBet() + bet;
            lastPlayer = getPreviousActivePlayerId(id);
            System.out.println(lastPlayer);
        }

        if (currentPlayer == lastPlayer) {
            currentPlayer = beginId;
            while (!isActive[currentPlayer]) {
                currentPlayer = getNextActivePlayerId(currentPlayer);
            }
            lastPlayer = getPreviousActivePlayerId(beginId);
            for (int i = 0; i < getMaxPlayers(); i++) {
                bets[i] = 0;
            }
            round++;

            if (round == 1) {
                board.addSet(deck.takeTopCard(3));
            } else if (round < 4) {
                board.addCard(deck.takeTopCard());
            } else {
                winner = bestHand();
            }
        } else nextPlayer();
        return true;
    }

    @Override
    public void onGameOver() {

        try {
            coins[getWinner()] += pot;
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("New game");

        reset();
    }

    @Override
    public boolean isGameOver() throws RemoteException {
        return (getActivePlayersNumber() <= 1 || round == 4);
    }

    @Override
    public int getWinner() throws RemoteException {
        return winner;
    }

    @Override
    public boolean isGameActive() throws RemoteException {
        return isGameActive;
    }

    @Override
    public CardSet getBoardCards() throws RemoteException {
        return board;
    }

    @Override
    public CardSet[] getPlayersCards(int id) {
        CardSet[] cs = new CardSet[getMaxPlayers()];
        for (int i = 0; i < getMaxPlayers(); i++) {
            if (isActive[i]) cs[i] = cards[i];
        }
        return cs;
    }

    public int getNextPlayerId(int id) {
        int idx = (id + 1) % getMaxPlayers();
        while (!isInGame(idx)) {
            idx = (idx + 1) % getMaxPlayers();
        }
        return idx;
    }

    public int getNextActivePlayerId(int id) {
        int idx = (id + 1) % getMaxPlayers();
        while (!(isActive[idx])) {
            idx = (idx + 1) % getMaxPlayers();
        }
        return idx;
    }

    public int getPreviousActivePlayerId(int id) {
        int idx = (id + getMaxPlayers() - 1) % getMaxPlayers();
        while (!(isInGame(idx) && isActive[idx])) {
            idx = (idx + getMaxPlayers() - 1) % getMaxPlayers();
        }
        return idx;
    }

    public int getActivePlayersNumber() {
        int count = 0;

        for (int i = 0; i < getMaxPlayers(); i++) {
            if (isInGame(i) && isActive[i]) {
                count++;
            }
        }

        return count;
    }

    public int bestHand() {
        int winner = -1;
        int maxSum = -1;
        for (int i = 0; i < getMaxPlayers(); i++) {
            if (isActive[i]) {
                CardSet cs = getPlayersCards(i)[i];
                int sum = 0;
                for (game.Card c : cs.getSetList()) {
                    sum += c.getValue();
                }
                if (sum > maxSum) {
                    winner = i;
                }
            }
        }
        return winner;
    }

    @Override
    public int getMaxBet() {
        int m = 0;
        for (int i = 0; i < getMaxPlayers(); i++) {
            if (bets[i] > m) m = bets[i];
        }
        return m;
    }

    @Override
    public int[] getCoins(Client c) throws RemoteException {
        return coins;
    }

    @Override
    public int[] getCurrentBet(Client c) throws RemoteException {
        return bets;
    }

    @Override
    public int getRound() throws RemoteException {
        return round;
    }

    @Override
    public int getPot() throws RemoteException {
        return pot;
    }
}

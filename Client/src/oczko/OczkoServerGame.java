package oczko;

import kartoteka.Card;
import kartoteka.CardImpl;
import kartoteka.StandardDeck;
import remoteinterface.GameClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class OczkoServerGame extends UnicastRemoteObject implements OczkoGame {

    private List<OczkoGameClient> clients = new ArrayList<>();
    private static final int MAX_PLAYERS = 2;

    private final int id;
    private int currentPlayer = -1;
    private StandardDeck deck = new StandardDeck();

    private Map<Integer, Boolean> isActive = new HashMap<>();
    private Map<Integer, List<Card>> cards = new HashMap<>();
    private static Map<String, Integer> cardToScore = Map.ofEntries(
            Map.entry("2", 2),
            Map.entry("3", 3),
            Map.entry("4", 4),
            Map.entry("5", 5),
            Map.entry("6", 6),
            Map.entry("7", 7),
            Map.entry("8", 8),
            Map.entry("9", 9),
            Map.entry("10", 10),
            Map.entry("Walet", 2),
            Map.entry("Dama", 3),
            Map.entry("Król", 4),
            Map.entry("As", 11)
    );

    private OczkoAction previousAction = OczkoAction.START;

    public OczkoServerGame() throws RemoteException {
        super();
        Random k = new Random();
        id = k.nextInt((20 - 1) + 1) + 20;
        deck.shuffle();
    }

    @Override
    public int join(GameClient client) throws RemoteException {
        if(clients.size() > MAX_PLAYERS) {
            return -1;
        }

        OczkoGameClient gameClient = (OczkoGameClient) client;

        isActive.put(clients.size(), true);
        cards.put(clients.size(), new ArrayList<>());

        clients.add(gameClient);

        System.out.println("Client joined " + clients.size() + "/" + MAX_PLAYERS);

        if(clients.size() == MAX_PLAYERS) {
            playTurn();
        }

        return clients.size() - 1;
    }

    @Override
    public void disconnect(GameClient client) throws RemoteException {
        System.out.println("Disconnecting " + client);
        clients.remove(client);
    }

    @Override
    public boolean hasMaxPlayers() throws RemoteException {
        return clients.size() >= MAX_PLAYERS;
    }

    private void playTurn() throws RemoteException {

        OczkoTurnDTO turn = new OczkoTurnDTO(currentPlayer, previousAction, true);

        currentPlayer++;
        currentPlayer %= clients.size();

        OczkoGameClient player = clients.get(currentPlayer);
        player.nextTurn(turn);

    }

    @Override
    public int getMaxNumberOfPlayers() {
        return MAX_PLAYERS;
    }

    @Override
    public Card requestCard(int clientID) throws RemoteException {
        if(clientID != currentPlayer || !isActive.get(clientID).booleanValue()) {
            // TODO: ERROR
            return null;
        }

        previousAction = OczkoAction.GET_CARD;

        OczkoGameClient player = clients.get(currentPlayer);
        OczkoGameClient nextPlayer = clients.get((currentPlayer + 1) % clients.size());

        for(OczkoGameClient client : clients) {
            if(player != client && client != nextPlayer) {
                OczkoTurnDTO turn = new OczkoTurnDTO(currentPlayer, previousAction, false);
                client.nextTurn(turn);
            }
        }


        Card card = deck.takeCard();
        cards.get(clientID).add(card);

        playTurn();
        if(calculateScore(cards.get(clientID)) >= 21) {
            isActive.put(clientID, false);
            checkGameOver();
        }

        return card;
    }

    @Override
    public void requestPass(int clientID) throws RemoteException  {
        if(clientID != currentPlayer || !isActive.get(clientID)) {
            // TODO: ERROR
            return;
        }
        isActive.put(clientID, false);
        if(!checkGameOver())  playTurn();
    }

    private int calculateScore(List<Card> cards) throws RemoteException {
        int sum = 0;

        for(Card c : cards) {
            sum += cardToScore.get(c.getValue());
        }

        return sum;
    }

    private int findWinner() throws RemoteException {
        Map<Integer, Integer> scores = new TreeMap<>();

        for(Map.Entry<Integer, List<Card>> e : cards.entrySet()) {
            int score = calculateScore(e.getValue());
            if(score > 21) {
                score = -1;
            }
            scores.put(e.getKey(), score);
        }

        int maxVal = Collections.max(scores.values());
        for(Map.Entry<Integer, Integer> e : scores.entrySet()) {
            if(e.getValue().equals(maxVal)) {
                return e.getKey();
            }
        }

        return -1;
    }

    private boolean checkGameOver() throws RemoteException {
        for(Map.Entry<Integer, Boolean> e : isActive.entrySet()) {
            if(e.getValue().booleanValue()) {
                return false;
            }
        }

        // gameover
        int winner = findWinner();
        OczkoTurnDTO turn = new OczkoTurnDTO(winner, OczkoAction.WIN, false);
        for(OczkoGameClient client : clients) {
            client.nextTurn(turn);
        }
        return true;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getGameType() throws RemoteException {
        return 1;
    }
}

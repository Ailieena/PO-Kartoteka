package oczko;

import game.GameClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class OczkoServerGame extends UnicastRemoteObject implements OczkoGame  {

    private List<OczkoGameClient> clients = new ArrayList<>();
    private static final int MAX_PLAYERS = 3;

    private final int id;
    private int currentPlayer = -1;
    private List<Integer> deck = new ArrayList<>();

    private Map<Integer, Boolean> isActive = new HashMap<>();
    private Map<Integer, List<Integer>> cards = new HashMap<>();

    private OczkoAction previousAction = OczkoAction.START;

    public OczkoServerGame() throws RemoteException {
        super();
        Random k = new Random();
        id = k.nextInt((20 - 1) + 1) + 20;
        initializeDeck();
    }


    private void initializeDeck() {
        for(int i = 1; i <= 52; i++) {
            deck.add(i);
        }

        Collections.shuffle(deck);
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

    private void playTurn() throws RemoteException {

        OczkoTurnDTO turn = new OczkoTurnDTO(currentPlayer, previousAction, true);

        currentPlayer++;
        currentPlayer %= clients.size();

        OczkoGameClient player = clients.get(currentPlayer);
        player.nextTurn(turn);

    }

    @Override
    public int getNumberOfPlayers() {
        return clients.size();
    }

    @Override
    public String requestCard(int clientID) throws RemoteException {
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


        int cardID = deck.remove(0);
        cards.get(clientID).add(cardID);

        playTurn();
        if(calculateScore(cards.get(clientID)) >= 21) {
            isActive.put(clientID, false);
            checkGameOver();
        }

        return "" +cardID;
    }

    @Override
    public void requestPass(int clientID) throws RemoteException  {
        if(clientID != currentPlayer || !isActive.get(clientID)) {
            // TODO: ERROR
            return;
        }
        isActive.put(clientID, false);
        checkGameOver();
    }

    private int calculateScore(List<Integer> cards) {
        int sum = 0;

        for(int c : cards) {

            if(c >= 1 && c <= 4) {
                sum += 11;
            }

            if(c >= 5 && c <= 8) {
                sum += 4;
            }

            if(c >= 9 && c <= 12) {
                sum += 3;
            }

            if(c >= 13 && c <= 16) {
                sum += 2;
            }

            if(c >= 17 && c <= 20) {
                sum += 10;
            }
            if(c >= 21 && c <= 24) {
                sum += 9;
            }
            if(c >= 25 && c <= 28) {
                sum += 8;
            }
            if(c >= 29 && c <= 32) {
                sum += 7;
            }
            if(c >= 33 && c <= 36) {
                sum += 6;
            }
            if(c >= 37 && c <= 40) {
                sum += 5;
            }
            if(c >= 41 && c <= 44) {
                sum += 4;
            }
            if(c >= 45 && c <= 48) {
                sum += 3;
            }
            if(c >= 49 && c <= 52) {
                sum += 2;
            }
        }

        return sum;
    }

    private int findWinner() {
        Map<Integer, Integer> scores = new TreeMap<>();

        for(Map.Entry<Integer, List<Integer>> e : cards.entrySet()) {
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

    private void checkGameOver() throws RemoteException {
        for(Map.Entry<Integer, Boolean> e : isActive.entrySet()) {
            if(e.getValue().booleanValue()) {
                return;
            }
        }

        // gameover
        int winner = findWinner();
        OczkoTurnDTO turn = new OczkoTurnDTO(winner, OczkoAction.WIN, false);
        for(OczkoGameClient client : clients) {
            client.nextTurn(turn);
        }
    }







    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getGameType() throws RemoteException {
        return 1;
    }





    @Override
    public void play() throws RemoteException {

    }

    @Override
    public int getCard(GameClient dummyGame) throws RemoteException {
        return 0;
    }


    @Override
    public boolean gameOver() throws RemoteException {
        return false;
    }

    @Override
    public boolean hasMaxPlayers() throws RemoteException {
        return false;
    }



}

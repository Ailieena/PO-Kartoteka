package game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameClient extends UnicastRemoteObject implements Client {

    private Game game;

    protected GameClient(Game game) throws RemoteException {
        this.game = game;
    }

    @Override
    public int getPlayersId() throws RemoteException {
        return game.getClientId(this);
    }

    @Override
    public boolean isGameActive() throws RemoteException {
        return game.isGameActive();
    }

    @Override
    public boolean isYourTurn() throws RemoteException {
        return game.isClientsTurn(this);
    }

    @Override
    public boolean step(int action) throws RemoteException {
        return game.step(this, action);
    }

    @Override
    public boolean isGameOver() throws RemoteException {
        return game.isGameOver();
    }

    @Override
    public int getCurrentPlayer() throws RemoteException {
        return game.getCurrentPlayerId();
    }

    @Override
    public int getWinner() throws RemoteException {
        return game.getWinner();
    }

    @Override
    public CardSet getBoardCards() throws RemoteException {
        return game.getBoardCards();
    }

    @Override
    public CardSet[] getPlayersCards() throws RemoteException {
        return game.getPlayersCards(this);
    }
}

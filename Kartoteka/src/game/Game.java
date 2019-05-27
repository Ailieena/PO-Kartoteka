package game;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Game extends Remote {

    Client getClient() throws RemoteException;

    int getClientId(Client c) throws RemoteException;

    boolean step(Client c, int action) throws RemoteException;

    boolean isGameOver() throws RemoteException;

    boolean isClientsTurn(Client c) throws RemoteException;

    boolean isGameActive() throws RemoteException;

    int getCurrentPlayerId() throws RemoteException;

    int getWinner() throws RemoteException;

    void disconnect(Client c) throws RemoteException;

    CardSet getBoardCards() throws RemoteException;

    CardSet[] getPlayersCards(Client c) throws RemoteException;
}

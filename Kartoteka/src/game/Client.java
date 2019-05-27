package game;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    int getPlayersId() throws RemoteException;

    boolean isGameActive() throws RemoteException;

    boolean isYourTurn() throws RemoteException;

    boolean step(int action) throws RemoteException;

    boolean isGameOver() throws RemoteException;

    int getCurrentPlayer() throws RemoteException;

    int getWinner() throws RemoteException;

    CardSet getBoardCards() throws RemoteException;

    CardSet[] getPlayersCards() throws RemoteException;
}

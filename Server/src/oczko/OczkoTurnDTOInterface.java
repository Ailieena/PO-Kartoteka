package oczko;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OczkoTurnDTOInterface extends Remote {
    public boolean isMyTurn() throws RemoteException;

    public int getPlayer() throws RemoteException;

    public OczkoAction getAction() throws RemoteException;
}

package oczko;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class OczkoTurnDTO extends UnicastRemoteObject implements OczkoTurnDTOInterface {
    private int player;
    private OczkoAction action;
    private boolean myTurn;

    public OczkoTurnDTO(int player, OczkoAction action, boolean myTurn) throws RemoteException {
        super();
        this.player = player;
        this.action = action;
        this.myTurn = myTurn;
    }

    public boolean isMyTurn() throws RemoteException {
        return myTurn;
    }

    public int getPlayer() throws RemoteException  {
        return player;
    }

    public OczkoAction getAction() throws RemoteException  {
        return action;
    }
}

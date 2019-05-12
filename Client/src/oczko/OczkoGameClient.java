package oczko;

import oczko.OczkoTurnDTOInterface;
import remoteinterface.GameClient;

import java.rmi.RemoteException;

public interface OczkoGameClient extends GameClient {

    void nextTurn(OczkoTurnDTOInterface dto) throws RemoteException;
}

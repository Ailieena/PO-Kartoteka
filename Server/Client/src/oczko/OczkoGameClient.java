package oczko;

import game.GameClient;

import java.rmi.RemoteException;

public interface OczkoGameClient extends GameClient {

    void nextTurn(OczkoTurnDTOInterface dto) throws RemoteException;
}

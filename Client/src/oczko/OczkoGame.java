package oczko;

import kartoteka.Card;
import remoteinterface.Game;

import java.rmi.RemoteException;

public interface OczkoGame extends Game {
    int getMaxNumberOfPlayers() throws RemoteException;

    Card requestCard(int clientID) throws RemoteException;

    void requestPass(int clientID) throws RemoteException;
}

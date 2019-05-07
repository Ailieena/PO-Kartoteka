package oczko;

import game.Game;

import java.rmi.RemoteException;

public interface OczkoGame extends Game {
    int getNumberOfPlayers() throws RemoteException;

    String requestCard(int clientID) throws RemoteException;

    void requestPass(int clientID) throws RemoteException;
}

package poker;

import game.Client;

import java.rmi.RemoteException;

public interface PokerClient extends Client {
    int getPot() throws RemoteException;

    int[] getCoins() throws RemoteException;

    int getMaxBet() throws RemoteException;

    int[] getCurrentBet() throws RemoteException;

    int getRound() throws RemoteException;
}

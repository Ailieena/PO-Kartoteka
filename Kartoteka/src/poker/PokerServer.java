package poker;

import game.Client;
import game.Game;

import java.rmi.RemoteException;

public interface PokerServer extends Game {
    int getPot() throws RemoteException;

    int getMaxBet() throws RemoteException;

    int[] getCoins(Client c) throws RemoteException;

    int[] getCurrentBet(Client c) throws RemoteException;

    int getRound() throws RemoteException;
}

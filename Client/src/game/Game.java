package game;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Game extends Remote
{
	//serwer
	public int getId() throws RemoteException;
	public int getGameType() throws RemoteException;
	int join(GameClient client) throws RemoteException;
	void play() throws RemoteException;
	int getCard(GameClient dummyGame) throws RemoteException;
	boolean gameOver() throws RemoteException;
	boolean hasMaxPlayers() throws RemoteException;
}

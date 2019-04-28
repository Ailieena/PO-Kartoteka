import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Random;

public interface Game extends Remote
{
	//serwer
	public int getId() throws RemoteException;
	void join(DummyGameClientInterface client) throws RemoteException;
	void play() throws RemoteException;
	int getCard(DummyGameClientInterface dummyGame) throws RemoteException;
	boolean gameOver() throws RemoteException;
	boolean hasMaxPlayers() throws RemoteException;
}

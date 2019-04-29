import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Random;

public interface Game extends Remote
{
	public int getId() throws RemoteException;
	public int getGameType() throws RemoteException;
	void join(GameClientInterface client) throws RemoteException;
	void play(GameClientInterface dummyGame) throws RemoteException;
	int getCard(GameClientInterface game) throws RemoteException;
	boolean gameOver() throws RemoteException;
	boolean hasMaxPlayers() throws RemoteException;
	public void passTurn(GameClientInterface player) throws RemoteException;
}

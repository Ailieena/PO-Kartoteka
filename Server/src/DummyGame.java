import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Scanner;

public class DummyGame extends UnicastRemoteObject implements Game
{
	GameClientInterface[] clients = new GameClientInterface[3];
	int clientCount = 0;
	int id;
	int i = 0;
	String gameName = "dummyGame";
	DummyGame() throws RemoteException
	{
		Random k = new Random();
		id = k.nextInt((20 - 1) + 1) + 20;
	}
	public int getId() throws RemoteException
	{
		return id;
	}
	public int getGameType() throws RemoteException
	{
		return 0;
	}
	@Override
	public void join(GameClientInterface client) throws RemoteException {
		if(clientCount >= 3)
		{
			client.notify("W pokoju jest ju� 3 graczy");
			return;
		}
		clients[clientCount] = client;
		clientCount++;
		
	}
	Scanner s = new Scanner(System.in);
	
	@Override
	public void play() throws RemoteException {
		
	}
	public int getCard(GameClientInterface dummyGame) throws RemoteException
	{
		if(i > 52)
		{
			notifyAll("Koniec talii");
			return -1;
		}
		System.out.println(dummyGame.getId() + " poprosil o karte");
		notifyAllBut( dummyGame.getId() + " dosta� kart� " + i, dummyGame.getId());
		return i++;
	}
	public void notifyAll(String s) throws RemoteException
	{
		for(int i = 0; i < clientCount; i++)
		{
			clients[i].notify(s);
		}
	}
	public void notifyAllBut(String s, String id) throws RemoteException
	{
		//System.out.println("No notify to " + id);
		for(int i = 0; i < clientCount; i++)
		{
			//System.out.println("notify do " + clients[i].getId());
			if(!clients[i].getId().equals(id))
				clients[i].notify(s);
		}
	}
	
	public boolean gameOver()
	{
		if(i > 52)
		{
			return true;
		}
		return false;
	}
	@Override
	public boolean hasMaxPlayers() throws RemoteException
	{
		if(clientCount >= 3)
		{
			return true;
		}
		return false;
	}


}
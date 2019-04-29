import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

public class Oczko extends UnicastRemoteObject implements Game
{
	GameClientInterface[] clients = new GameClientInterface[3];
	GraczOczko[] gracze = new GraczOczko[3];
	int clientCount = 0;
	int id;
	int i = 0;
	int value = 0;
	int currentPlayer = 0;
	String gameName = "Oczko";
	TaliaOczko t = new TaliaOczko();
	
	Oczko() throws RemoteException
	{
		Random k = new Random();
		id = k.nextInt(50);
	}
	@Override
	public int getId() throws RemoteException {
		return id;
	}

	@Override
	public int getGameType() throws RemoteException {
		return 1;
	}

	@Override
	public void join(GameClientInterface client) throws RemoteException 
	{
		if(clientCount >= 3)
		{
			client.notify("W pokoju jest ju� 3 graczy");
			return;
		}
		gracze[clientCount] = new GraczOczko(client);
		clients[clientCount] = client;
		clients[0].myTurn();
		//gracze[clientCount].addPoints(t.getCard());
		clientCount++;
	}
	@Override
	public void passTurn(GameClientInterface player) throws RemoteException
	{
		notifyAllBut(clients[currentPlayer].getId() + " zrezygnowa� z karty " + value, clients[currentPlayer].getId());
		gracze[currentPlayer].setNotPlaying();
		for(int j = currentPlayer+1; j < currentPlayer+clientCount; j++)
		{
			if(gracze[j%clientCount].isPlaying())
			{
				currentPlayer = j%clientCount;
				continue;
			}
		}
		clients[currentPlayer].myTurn();
		if(gameOver())
		{
			int k = whoWon();
			notifyAll(clients[k].getId() + " wygra�! " );
			return;
		}

	}
	@Override
	public void play(GameClientInterface player) throws RemoteException 
	{
		System.out.println("play");
		notifyAllBut(clients[currentPlayer].getId() + " dosta� kart� " + value, clients[currentPlayer].getId());
		gracze[currentPlayer].addPoints(value);
		System.out.println("a");
		if(gracze[currentPlayer].getPoints() >= 21)
		{
			gracze[currentPlayer].setNotPlaying();
		}
		System.out.println("B");
		
		for(int j = currentPlayer+1; j < currentPlayer+clientCount; j++)
		{
			if(gracze[j%clientCount].isPlaying())
			{
				currentPlayer = j%clientCount;
				break;
			}
		}
		System.out.println("tura gracza " + clients[currentPlayer].getId());
		clients[currentPlayer].myTurn();
		 
		if(gameOver())
		{
			System.out.println("gameOver");
			int k = whoWon();
			notifyAll(clients[k].getId() + " wygra�! " );
			return;
		}
		System.out.println("c");
		
	}
	public void notifyAll(String s) throws RemoteException
	{
		for(int j = 0; j < clientCount; j++)
		{
			clients[i].notify(s);
		}
	}
	public void notifyAllBut(String s, String id) throws RemoteException
	{
		//System.out.println("No notify to " + id);
		for(int j = 0; j < clientCount; j++)
		{
			//System.out.println("notify do " + clients[i].getId());
			if(!clients[i].getId().equals(id))
				clients[i].notify(s);
		}
	}
	@Override
	public int getCard(GameClientInterface player) throws RemoteException 
	{
		if(i > 52)
		{
			notifyAll("Koniec talii");
			return -1;
		}
		int val = t.getCard();
		notifyAllBut(player.getId() + " dosta� kart� " + val, player.getId());
		value = val;
		return val;
	}
	private int whoWon()
	{
		int k = 0;
		int bestResult = 0;
		for(int j = 0; j < clientCount; j++)
		{
			if(gracze[j].getPoints() > bestResult && gracze[j].getPoints() <= 21)
			{
				k = j;
				bestResult = gracze[j].getPoints();
			}
		}
		return k;
	}
	@Override
	public boolean gameOver() throws RemoteException {
		boolean b = true;
		for(int j = 0; j < clientCount; j++)
		{
			if(gracze[j].isPlaying())
			{
				b = false;
			}
		}
		return b;
	}

	@Override
	public boolean hasMaxPlayers() throws RemoteException {
		if(clientCount >= 3)
		{
			return true;
		}
		return false;
	}

}

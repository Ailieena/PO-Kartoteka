import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class DummyGameClient extends UnicastRemoteObject implements DummyGameClientInterface
{
	Game game;
	String clientName;
	DummyGameClient(Game g, String i) throws RemoteException
	{
		game = g;
		clientName = i;
		g.join(this);
	}
	Scanner s = new Scanner(System.in);
	
	public void play() throws RemoteException 
	{
			while(!game.gameOver())
			{
				int c = s.nextInt();
				if(c == 0)
				{
					return;
				}
				int card = game.getCard(this);
				System.out.println("Otzymalam karte o numerze: "+ card);
				
			}
			System.out.println("Koniec talii");
	}
	public void notify(String s) throws RemoteException
	{
		System.out.println(s);
	}
	@Override
	public String getId() throws RemoteException {
		
		return clientName;
	}

}

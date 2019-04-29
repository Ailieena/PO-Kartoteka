import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Scanner;

public class OczkoClient extends UnicastRemoteObject implements GameClientInterface
{
	Scanner s;
	Game game;
	String clientName;
	boolean myTurn;
	OczkoClient(Game g, String i) throws RemoteException
	{
		s = new Scanner(System.in); 
		game = g;
		clientName = i;
		myTurn = false;
		g.join(this);

	}
	
	@Override
	public void notify(String s) throws RemoteException 
	{
		System.out.println(s);
	}
	@Override
	public String getId() throws RemoteException 
	{
		return clientName;
	}
	public void myTurn()
	{
		System.out.println("Twoja tura!");
		myTurn = true;		
	}
	
	@Override
	public void play() throws RemoteException 
	{
		while(true)
		{
			//System.out.println(myTurn);
			if(myTurn)
			{
				
				System.out.println("Czy checz zachowa� kart� "+ game.getCard(this) + " ? Y\\N");
				String c = s.nextLine();
				System.out.println("c: " + c);
				if(c.equals("Y"))
				{
					game.play(this);
				}
				else
				{
					game.passTurn(this);
				}
				if(game.gameOver())
				{
					return;
				}
				System.out.println("skonczyla sie twoja tura");
				myTurn = false;
			}
			if(game.gameOver())
			{
				return;
			}
		}
		
	}
	
}

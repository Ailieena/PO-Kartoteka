import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

public class OczkoClient extends UnicastRemoteObject implements GameClientInterface
{
	Game game;
	String clientName;
	boolean myTurn;
	OczkoClient(Game g, String i) throws RemoteException
	{
		game = g;
		clientName = i;
		g.join(this);
		myTurn = false;
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
		
		
	}

	@Override
	public void play() throws RemoteException 
	{
		
		
	}
	
}

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

public class OczkoClient extends UnicastRemoteObject implements GameClientInterface
{
	Game game;
	String clientName;
	OczkoClient(Game g, String i) throws RemoteException
	{
		game = g;
		clientName = i;
		g.join(this);
	}

	@Override
	public void notify(String s) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getId() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void play() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}

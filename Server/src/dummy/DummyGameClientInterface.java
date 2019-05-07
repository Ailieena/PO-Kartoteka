package dummy;

import game.GameClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DummyGameClientInterface extends Remote, GameClient
{
	public String getId() throws RemoteException;
	public void play() throws RemoteException;
	public void notify(String s) throws RemoteException;
	
}

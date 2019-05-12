package remoteinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Game extends Remote
{
	int getId() throws RemoteException;
	int getGameType() throws RemoteException;
	int join(GameClient client) throws RemoteException;
	void disconnect(GameClient client) throws RemoteException;

	boolean hasMaxPlayers() throws RemoteException;
}

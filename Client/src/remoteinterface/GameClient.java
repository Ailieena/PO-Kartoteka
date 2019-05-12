package remoteinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameClient extends Remote {
    public String getId() throws RemoteException;
}

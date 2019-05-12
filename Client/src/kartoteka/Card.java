package kartoteka;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Card extends Remote {


    String getValue() throws RemoteException;

    String getColor() throws RemoteException;
}

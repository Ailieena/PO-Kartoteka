import java.rmi.*;
import java.util.List; 
public interface EntryPoint extends Remote 
{ 
    // Declaring the method prototype 
	public void create() throws RemoteException; 
	public String join(int id) throws RemoteException; 
    public List<Integer> list(String search) throws RemoteException;
	public boolean getGame(int id) throws RemoteException; 
} 
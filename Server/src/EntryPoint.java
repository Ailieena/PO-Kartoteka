import java.rmi.*;
import java.util.List;
import java.util.Map; 
public interface EntryPoint extends Remote 
{ 
    // Declaring the method prototype
	public int create(int type) throws RemoteException; 
	public String join(int id) throws RemoteException; 
    public List<Integer> list(String search) throws RemoteException; 
    public boolean getGame(int id) throws RemoteException; 
    public void delteGame(int id) throws RemoteException; 
    public Map<Integer, String> getTypeOfGames() throws RemoteException;
} 
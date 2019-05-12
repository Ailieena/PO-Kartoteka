package lobby;

import java.rmi.*;
import java.util.List;
import java.util.Map; 
public interface EntryPoint extends Remote 
{ 
    // Declaring the method prototype
	public int create() throws RemoteException; 

    public List<Integer> list(String search) throws RemoteException; 
    public boolean getGame(int id) throws RemoteException; 
    public void deleteGame(int id) throws RemoteException;
    public Map<Integer, String> getTypeOfGames() throws RemoteException;
} 
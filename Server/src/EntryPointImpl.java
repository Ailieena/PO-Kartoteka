import java.net.MalformedURLException;
import java.rmi.*; 
import java.util.Random;

import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map; 
public class EntryPointImpl extends UnicastRemoteObject 
                         implements EntryPoint 
{ 
	Map<Integer, String> gameTypes;
	List<Game> games;
	EntryPointImpl() throws RemoteException 
    { 
        super(); 
        games = new ArrayList<Game>();
        gameTypes = new HashMap<Integer, String>();
        gameTypes.put(0, "DummyGame");
        gameTypes.put(1, "Oczko");
    } 
	public Map<Integer, String> getTypeOfGames()
	{
		return gameTypes;
		
	}
	@Override
	public int create(int type) throws RemoteException {	
		// ---- poprawi�
		Game r;
		if(type == 0)
		{
			r = new DummyGame();
		}
		else if(type == 1)
		{
			r = new Oczko();
		}
		else
		{
			return -1; 
		}
		games.add(r);
		try {
			Naming.rebind("rmi://localhost:1900"+ 
			        "/game"+ r.getId(), r);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		return r.getId();
		
	}

	@Override
	public String join(int id) throws RemoteException {
	 
		return "joined";
	}
	
	public boolean getGame(int id)  throws RemoteException
	{
		Iterator<Game> it = games.iterator();
		while(it.hasNext())
		{
			Game r = it.next();
			if(r.getId() == id)
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<Integer> list(String search) throws RemoteException {
		Iterator<Game> it = games.iterator();
		List<Integer> ids = new ArrayList<Integer>();
		while(it.hasNext())
		{
			Game r = it.next();
			if(!r.gameOver() && !r.hasMaxPlayers())
				ids.add(r.getId());
		}
		return ids;
	}

	@Override
	public void delteGame(int id) throws RemoteException 
	{
		for(int i = 0; i < games.size(); i++)
		{
			if(games.get(i).getId() == id)
			{
				games.remove(i);
				try {
					Naming.unbind("rmi://localhost:1900"+ 
					        "/game"+ id);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					e.printStackTrace();
				}
			}
		}
	} 
} 
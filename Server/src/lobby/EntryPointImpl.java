package lobby;

import game.Game;
import oczko.OczkoServerGame;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
public class EntryPointImpl extends UnicastRemoteObject 
                         implements EntryPoint
{ 
	Map<Integer, String> gameTypes;
	List<Game> games;
	public EntryPointImpl() throws RemoteException
    { 
        super(); 
        games = new ArrayList<Game>();
        gameTypes = new HashMap<Integer, String>();
        gameTypes.put(0, "dummy.DummyGame");
        gameTypes.put(1, "Oczko");
    } 
	public Map<Integer, String> getTypeOfGames()
	{
		return gameTypes;
		
	}
	@Override
	public int create() throws RemoteException {
		Game r = new OczkoServerGame();
		games.add(r);
		try {
			Naming.rebind("rmi://localhost:1900"+ 
			        "/game/"+ r.getId(), r);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		return r.getId();
		
	}


	private Game findGame(int id) throws RemoteException {
		for(Game g : games) {
			if (g.getId() == id) {
				return g;
			}

		}
		return null;
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
	public void deleteGame(int id) throws RemoteException
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
package server;

import oczko.OczkoGame;
import oczko.OczkoServerGame;
import remoteinterface.ServerAPI;
import remoteinterface.Game;

import java.net.MalformedURLException;
import java.rmi.*;

import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ServerAPIImpl extends UnicastRemoteObject
                         implements ServerAPI
{ 
	Map<Integer, String> gameTypes;
	List<Game> games;
	public ServerAPIImpl() throws RemoteException
    { 
        super(); 
        games = new ArrayList<Game>();
        gameTypes = new HashMap<Integer, String>();

        gameTypes.put(1, "Oczko");

    } 
	public Map<Integer, String> getTypeOfGames()
	{
		return gameTypes;
		
	}
	@Override
	public int create() throws RemoteException {
		OczkoGame og = new OczkoServerGame();
		games.add(og);
		try {
			Naming.rebind("rmi://localhost:1900/game/" + og.getId(), og);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return og.getId();
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
			if(!r.hasMaxPlayers())
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
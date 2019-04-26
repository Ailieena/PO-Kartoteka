import java.net.MalformedURLException;
import java.rmi.*; 
import java.util.Random;

import java.rmi.server.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List; 
public class EntryPointImpl extends UnicastRemoteObject 
                         implements EntryPoint 
{ 
	List<Game> games;
	EntryPointImpl() throws RemoteException 
    { 
        super(); 
        games = new ArrayList<Game>();
    } 

	@Override
	public void create() throws RemoteException {
		DummyGame r = new DummyGame();
		games.add(r);
		try {
			Naming.rebind("rmi://localhost:1900"+ 
			        "/game"+ r.id, r);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
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
			DummyGame r = (DummyGame) it.next();
			if(r.id == id)
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
			DummyGame r = (DummyGame) it.next();
			ids.add(r.id);
			System.out.println(r.gameName + " " + r.id);
		}
		
		
		return ids;
	} 
} 
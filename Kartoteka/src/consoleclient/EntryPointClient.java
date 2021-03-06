package consoleclient;


import remoteinterface.ServerAPI;
import remoteinterface.Game;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class EntryPointClient {
	ServerAPI access;
	String login;
	public void join(int id) throws RemoteException, MalformedURLException, NotBoundException
	{
		Game game = (Game)Naming.lookup("rmi://localhost:1900"+
                "/game"+id); 
		/*DummyGameClient gameClient;
		gameClient = new DummyGameClient(game, login);
		gameClient.play();*/
		access.deleteGame(id);
		
	}
	
	public void run()
	{
		Scanner s = new Scanner(System.in); 
        try
        { 
            // lookup method to find reference of remote object 
            access = 
                (ServerAPI)Naming.lookup("rmi://localhost:1900"+
                                      "/start"); 
            System.out.println("Podaj login: ");
            login = s.nextLine();
            String operation;
        	while(true)
        	{
        		System.out.println("PODAJ KOMEND�: ");
    	    	operation = s.nextLine();
    	    	if(operation.equals("CREATE"))
    	    	{
    	    		System.out.println("wybierz w kt�r� gr� chcesz gra�: ");
    	    		System.out.println(access.getTypeOfGames());
    	    		int type = s.nextInt();
    	    		int id = access.create();
    	    		System.out.println("Twoja gra ma id : " + id);
    	    		join(id);
    	
    	    	}
    	    	else if(operation.equals("LIST"))
    	    	{
    	    		System.out.println(access.list(" "));
    	    	}
    	    	else if(operation.equals("JOIN"))
    	    	{
    	    		System.out.println("WYBIERZ Z POKOI: ");
    	    		System.out.println(access.list(" "));
    	    		int id = s.nextInt();
    	    		if(access.getGame(id))
	    	    	{
	    	    		join(id);
    	    		}
    	    		else
    	    		{
    	    			System.out.println("gra nie istnieje");
    	    		}
    	    	}
    	    	else
    	    	{
    	    		System.out.println("niepoprawna komenda");
    	    	}
        	}
        } 
        catch(Exception ae) 
        { 
            System.out.println(ae); 
        } 
		
	}
	
}

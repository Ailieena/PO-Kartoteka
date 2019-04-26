import java.rmi.Naming;
import java.util.Scanner;

public class EntryPointClient {

	public void run()
	{
		Scanner s = new Scanner(System.in);
        String login; 
        try
        { 
            // lookup method to find reference of remote object 
            EntryPoint access = 
                (EntryPoint)Naming.lookup("rmi://localhost:1900"+ 
                                      "/start"); 
            System.out.println("Podaj login: ");
            login = s.nextLine();
            String operation;
        	while(true)
        	{
        		System.out.println("PODAJ KOMENDE: ");
    	    	operation = s.nextLine();
    	    	if(operation.equals("CREATE"))
    	    	{
    	    		access.create();
    	    	}
    	    	else if(operation.equals("LIST"))
    	    	{
    	    		System.out.println(access.list(" "));
    	    	}
    	    	else if(operation.equals("JOIN"))
    	    	{
    	    		int id = s.nextInt();
    	    		while(true)
    	    		{
	    	    		if(access.getGame(id))
	    	    		{
		    	    		Game game = (Game)Naming.lookup("rmi://localhost:1900"+ 
		    	                                          "/game"+id); 
		    	    		DummyGameClient gameClient = new DummyGameClient(game, login);
		    	    		gameClient.play();
		    	    		break;
	    	    		}
	    	    		else
	    	    		{
	    	    			System.out.println("gra nie istnieje");
	    	    		}
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

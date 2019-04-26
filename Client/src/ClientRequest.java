import java.rmi.*;
import java.util.Scanner; 

public class ClientRequest
{ 

    public static void main(String args[]) 
    { 
    	EntryPointClient entry = new EntryPointClient();
    	entry.run();
    } 
    
} 
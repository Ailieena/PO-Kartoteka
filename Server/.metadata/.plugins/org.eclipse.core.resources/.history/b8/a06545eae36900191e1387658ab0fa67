import java.rmi.*; 
import java.rmi.registry.*; 
public class SearchServer 
{ 
    public static void main(String args[]) 
    { 
    	System.out.println("odpalam");
        try
        {
            EntryPoint obj = new EntryPointImpl(); 
            LocateRegistry.createRegistry(1900);  
            Naming.rebind("rmi://localhost:1900"+ 
                          "/start",obj); 
            
            
        } 
        catch(Exception ae) 
        { 
            System.out.println(ae); 
        } 
    } 
} 
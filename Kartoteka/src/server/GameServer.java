package server;

import remoteinterface.ServerAPI;

import java.rmi.*;
import java.rmi.registry.*;
public class GameServer
{ 
    public static void main(String args[]) 
    { 
    	System.out.println("odpalam");
        try
        {
            ServerAPI obj = new ServerAPIImpl();
            LocateRegistry.createRegistry(1900);  
            Naming.rebind("rmi://localhost:1900"+ 
                          "/start",obj);

            obj.createPoker();

            //System.out.println("Created room: " + obj.create());
        } 
        catch(Exception ae) 
        { 
            System.out.println(ae); 
        } 
    } 
} 
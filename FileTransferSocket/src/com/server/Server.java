package com.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;

/**Server class 
 * @author Bisheswor Devkota
 * @version 1.0
 * @since 12/3/2016
 *
 */
public class Server {


    public static void main(String[] args) throws Exception {
    	  if (args[0].equalsIgnoreCase("start") &&  portSetCorrectly(args[1])  ){     
    	        FileImplementor object = new FileImplementor();
    	        try
    	        {
    	        	//If it's already running let the user know
    	        	int port=Integer.parseInt(args[1]);  
    	        	LocateRegistry.createRegistry(port);
    	        	

        	        String url = "rmi://" + "localhost" + ":" + port + "/FILESERVER";
        	        Naming.rebind(url, object);       
        	        System.out.println("Server is ready. Please open a client and run the commands.");
    	          
    	        } catch (ExportException ex)
    	        {
    	        	System.out.println("Exception occurred.File Server failed.Server may be already running.");
    	        }
  		}
    	  else{
    		  System.out.println("Please use Server start <portnumber> to start the server");
    	  }
    }
    //utility method to check if the server start has the right port number
    /**
     * @param value
     * @return true if port is set correctly
     */
    public static boolean portSetCorrectly(String value){
    	if(value==null){
    		return false;
    	}
    	else{
    		try{
        		Integer.parseInt(value); 
        		return true;
        	}
        	catch(NumberFormatException ex){
        		return false;
        	}
    	}
    	
    	
    }

}

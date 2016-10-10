package com.valant.test.inventory.javaapp;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.valant.test.dbresources.ItemsInventory;
import com.valant.test.dto.Item;

import java.net.InetAddress;

public class StartInventoryService {

	/*
	 * This entry point for Test Inventory Services. In this class, Jetty Server is started with Servlet Handler set to
	 * Jersey Rest Services Implementation. All requests to this ip:port are directed to this Handler Servlet.
	 */

    public static void main( String[] args ) throws Exception
    {
    	int port = 8080;
    	String machineIp = InetAddress.getLocalHost().getHostAddress();
    	if(args.length > 1) {
    		try {
    			port = Integer.parseInt(args[0]);
    		} catch(Exception e) {
    			//In case of NumberFormatException or any other exception use default port
    			e.printStackTrace(System.out);
    		}
    	}
    	 initializeItemInventory();
    	
    	System.out.println("Starting Inventory Service at : " + machineIp + ":" + port);
        Server server = new Server(port);
        System.out.println("Started Inventory Service at : " + machineIp + ":" + port);
       
	    ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
	    context.addServlet(RestHandler.getRestHandler(), "/*");
	    server.start();
	    server.join();      
	   
    }
    /*
     * This method is being used to add inventory items for testing and is not part of any function or feature
     * 
     */
    private static void initializeItemInventory() {
    	System.out.println("Adding Test Items");
    	Item i1 = new Item("Tylenol","Tylenol Tablets 250g","12-12-2016", "Pain");
    	Item i2 = new Item("Advil","Ibrufin Tablets 250g","12-12-2016", "Pain");
    	Item i3 = new Item("Icyhot","Pain relieving gel","10-08-2016", "Pain");
    	
    	ItemsInventory.addItem(i1);
    	ItemsInventory.addItem(i2);
    	ItemsInventory.addItem(i3);
    }

}

package com.valant.test.inventory.javaapp;


import org.eclipse.jetty.servlet.ServletHolder;

import com.sun.jersey.spi.container.servlet.ServletContainer;

public class RestHandler {

	public static ServletHolder getRestHandler() {
	
		ServletHolder sh = new ServletHolder(ServletContainer.class);    
	    sh.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
	    sh.setInitParameter("com.sun.jersey.config.property.packages", "com.valant.test.inventory.services");
	    sh.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");	  
	    return sh;
	}
}

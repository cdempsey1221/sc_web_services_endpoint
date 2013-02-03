package com.shakercafe.service.infrastructure;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.shakercafe.service.config.AppConfig;

public class JettyServerBootstrap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Server server = new Server( 8090 );

	    // Register and map the dispatcher servlet
	    final ServletHolder servletHolder = new ServletHolder( new CXFServlet() );
	    final ServletContextHandler context = new ServletContextHandler();   
	    context.setContextPath( "/" );
	    context.addServlet( servletHolder, "/rest/*" );  
	    context.addEventListener( new ContextLoaderListener() );

	    context.setInitParameter( "contextClass", AnnotationConfigWebApplicationContext.class.getName() );
	    context.setInitParameter( "contextConfigLocation", AppConfig.class.getName() );

	    server.setHandler( context );
	    
	    try {
			server.start();
			server.join(); 
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

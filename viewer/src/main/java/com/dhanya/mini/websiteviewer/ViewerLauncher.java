package com.dhanya.mini.websiteviewer;

import java.io.IOException;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.dhanya.mini.websiteviewer.config.ViewerAppBeans;

/**
 * Launcher for weebly viewer application.
 * 
 * @author Leela
 */
public class ViewerLauncher {	
	
	private static String CONTEXT_PATH = "/";

	private static final String MAPPING_URL = "/*";
	
	private static final int DEFAULT_PORT = 9002;

	public static void main(String[] args) throws Exception {
		
		int defaultPort = DEFAULT_PORT;
		if (args != null && args.length == 1) {
			String stringPortNum = args[0];
			defaultPort = Integer.valueOf(stringPortNum);
        }
		
		Server server = new Server(defaultPort);
		Handler[] handlers = getHandler().getHandlers();
		server.setHandler(handlers[0]);
		server.start();
		server.join();
	}

	private static HandlerList getHandler() throws IOException {
		HandlerList handlers = new HandlerList();
		handlers.addHandler(getServletContextHandler(getContext()));
		return handlers;
	}

	private static ServletContextHandler getServletContextHandler(WebApplicationContext context) throws IOException {
		WebAppContext contextHandler = new WebAppContext();
		contextHandler.setContextPath(CONTEXT_PATH);

		DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
		ServletHolder servletHolder = new ServletHolder(dispatcherServlet);

		contextHandler.addServlet(servletHolder, MAPPING_URL);
		contextHandler.addEventListener(new ContextLoaderListener(context));
		contextHandler.setResourceBase("src/main/webapp");
		return contextHandler;
	}

	private static WebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(ViewerAppBeans.class);
		return context;	
	}
}
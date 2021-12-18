package org.training.campus.onlineshop;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Runner {

	private static final String CONTEXT = "/context";

	public static void main(String[] args) {
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.addServlet(AllRequestsServlet.class, "/*");
		context.setContextPath(CONTEXT);

		var server = new Server(8080);
		var handlerList = new HandlerList();
		handlerList.addHandler(context);
		server.setHandler(handlerList);

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

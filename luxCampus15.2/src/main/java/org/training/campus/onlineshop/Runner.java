package org.training.campus.onlineshop;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Runner {

	public static void main(String[] args) {
		AllRequestsServlet allRequestsServlet = new AllRequestsServlet();

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.addServlet(new ServletHolder(allRequestsServlet), "/*");

		Server server = new Server(8080);
		server.setHandler(context);

		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

package org.training.campus.onlineshop;

import java.io.FileReader;
import java.util.Properties;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.training.campus.onlineshop.controller.ListAllProductsServlet;

public class Runner {

	public static void main(String[] args) {

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

		try (FileReader reader = new FileReader("properties")) {

			var props = new Properties();
			props.load(reader);

			context.setContextPath(props.getProperty("context"));

			ServletHolder holder = context.addServlet(ListAllProductsServlet.class, "/*");
			context.setInitParameter("user", props.getProperty("user"));
			context.setInitParameter("password", props.getProperty("password"));
			context.setInitParameter("url", props.getProperty("url"));
			
			var server = new Server(8080);
			var handlerList = new HandlerList();
			handlerList.addHandler(context);
			server.setHandler(handlerList);

			server.start();
			server.join();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

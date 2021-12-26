package org.training.campus.onlineshop;

import java.util.Objects;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.training.campus.onlineshop.controller.AbstractServlet;
import org.training.campus.onlineshop.controller.CreateProductServlet;
import org.training.campus.onlineshop.controller.DeleteProductServlet;
import org.training.campus.onlineshop.controller.ListAllProductsServlet;
import org.training.campus.onlineshop.controller.ModifyProductServlet;
import org.training.campus.onlineshop.controller.SaveProductServlet;

public class Runner {

	private static final String CONTEXT = "/shop";
	private static final String JDBC_URL_PARAMETER = "url";
	private static final String JDBC_USER_PARAMETER = "user";
	private static final String JDBC_PASSWORD_PARAMETER = "password";
	private static final String SERVER_PORT_PARAMETER = "port";

	public static void main(String[] args) {

		var server = new Server(Integer.parseInt(System.getProperty(SERVER_PORT_PARAMETER)));

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

		context.setContextPath(CONTEXT);
		context.setInitParameter(AbstractServlet.JDBC_URL_PARAMETER, System.getProperty(JDBC_URL_PARAMETER));
		context.setInitParameter(AbstractServlet.JDBC_USER_PARAMETER, Objects.requireNonNullElse(System.getProperty(JDBC_USER_PARAMETER), ""));
		context.setInitParameter(AbstractServlet.JDBC_PASSWORD_PARAMETER, Objects.requireNonNullElse(System.getProperty(JDBC_PASSWORD_PARAMETER), ""));

		context.addServlet(ListAllProductsServlet.class, "/products");
		context.addServlet(DeleteProductServlet.class, "/products/delete");
		context.addServlet(CreateProductServlet.class, "/products/add");
		context.addServlet(ModifyProductServlet.class, "/products/edit");
		context.addServlet(SaveProductServlet.class, "/saveproduct");

		server.setHandler(context);

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

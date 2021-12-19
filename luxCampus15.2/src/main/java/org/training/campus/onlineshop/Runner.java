package org.training.campus.onlineshop;

import java.io.FileReader;
import java.util.Objects;
import java.util.Properties;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.training.campus.onlineshop.controller.CreateProductServlet;
import org.training.campus.onlineshop.controller.DeleteProductServlet;
import org.training.campus.onlineshop.controller.ListAllProductsServlet;
import org.training.campus.onlineshop.controller.ModifyProductServlet;
import org.training.campus.onlineshop.controller.SaveProductServlet;

public class Runner {

	public static void main(String[] args) {

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

		try (FileReader reader = new FileReader("properties")) {

			var props = new Properties();
			props.load(reader);

			final String servletContext = Objects.requireNonNullElse(props.getProperty("context"), "/shop");

			context.setContextPath(servletContext);

			context.setInitParameter("user", props.getProperty("user"));
			context.setInitParameter("password", props.getProperty("password"));
			context.setInitParameter("url", props.getProperty("url"));

			context.addServlet(ListAllProductsServlet.class, "/products");
			context.addServlet(DeleteProductServlet.class, "/products/delete");
			context.addServlet(CreateProductServlet.class, "/products/add");
			context.addServlet(ModifyProductServlet.class, "/products/edit");
			context.addServlet(SaveProductServlet.class, "/saveproduct");

			var server = new Server(9090);
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

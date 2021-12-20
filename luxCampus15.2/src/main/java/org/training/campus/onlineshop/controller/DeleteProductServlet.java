package org.training.campus.onlineshop.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.training.campus.onlineshop.entity.Product;

public class DeleteProductServlet extends AbstractServlet {

	protected static final String REDIRECTION_RESOURCE = "/products";

	public DeleteProductServlet() {
		super(false);
	}

	@Override
	public void doWork(HttpServletRequest req) throws ServletException {
		deleteProduct(req);
		fetchProducts();
	}

	@Override
	public String getRedirectionResource() {
		return REDIRECTION_RESOURCE;
	}

	private void deleteProduct(HttpServletRequest req) throws ServletException {
		Product toDelete = getProductFromList(req);
		getProductDao().remove(toDelete);
	}

}

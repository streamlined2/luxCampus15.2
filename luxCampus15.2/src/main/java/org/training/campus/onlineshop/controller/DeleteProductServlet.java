package org.training.campus.onlineshop.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.training.campus.onlineshop.entity.Product;

public class DeleteProductServlet extends AbstractServlet {

	public DeleteProductServlet() {
		super(false);
	}

	@Override
	public void doWork(HttpServletRequest req) throws ServletException {
		deleteProduct(req);
		fetchProducts();
	}

	private void deleteProduct(HttpServletRequest req) throws ServletException {
		Product toDelete = getProductFromList(req);
		getProductDao().remove(toDelete);
	}

	@Override
	public String getRedirectionResource() {
		return "/products";
	}

}

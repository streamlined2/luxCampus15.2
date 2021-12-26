package org.training.campus.onlineshop.controller;

import javax.servlet.http.HttpServletRequest;

public class ListAllProductsServlet extends AbstractServlet {

	@Override
	public void doWork(HttpServletRequest req) {
		fetchProducts();
	}

	@Override
	public String getRedirectionResource() {
		return "product-list.html";
	}

}

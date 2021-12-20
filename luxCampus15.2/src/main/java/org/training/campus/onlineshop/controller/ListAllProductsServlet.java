package org.training.campus.onlineshop.controller;

import javax.servlet.http.HttpServletRequest;

public class ListAllProductsServlet extends AbstractServlet {

	protected static final String REDIRECTION_RESOURCE = "product-list.html";

	@Override
	public void doWork(HttpServletRequest req) {
		fetchProducts();
	}

	@Override
	public String getRedirectionResource() {
		return REDIRECTION_RESOURCE;
	}

}

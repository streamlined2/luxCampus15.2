package org.training.campus.onlineshop.controller;

public class ListAllProductsServlet extends AbstractServlet {

	protected static final String REDIRECTION_RESOURCE = "product-list.ftl";

	@Override
	public void doWork() {
		fetchProducts();
	}

	@Override
	public String getRedirectionResource() {
		return REDIRECTION_RESOURCE;
	}

}

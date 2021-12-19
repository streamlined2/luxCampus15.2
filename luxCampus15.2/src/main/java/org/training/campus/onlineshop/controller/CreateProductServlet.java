package org.training.campus.onlineshop.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class CreateProductServlet extends AbstractServlet {

	protected static final String REDIRECTION_RESOURCE = "new-product.ftl";

	@Override
	public void doWork(HttpServletRequest req) throws ServletException {
		setTemplateParameter(CREATE_PRODUCT_ATTRIBUTE, Boolean.TRUE);
	}

	@Override
	public String getRedirectionResource() {
		return REDIRECTION_RESOURCE;
	}

}

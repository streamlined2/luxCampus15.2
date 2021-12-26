package org.training.campus.onlineshop.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class ModifyProductServlet extends AbstractServlet {

	@Override
	public void doWork(HttpServletRequest req) throws ServletException {
		setTemplateParameter(CREATE_PRODUCT_ATTRIBUTE, Boolean.FALSE);
		setTemplateParameter(PRODUCT_ATTRIBUTE, getProductFromList(req));
	}

	@Override
	public String getRedirectionResource() {
		return "modify-product.html";
	}

}

package org.training.campus.onlineshop.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.training.campus.onlineshop.PageGenerator;
import org.training.campus.onlineshop.dao.JdbcProductDao;
import org.training.campus.onlineshop.dao.ProductDao;
import org.training.campus.onlineshop.entity.Product;

public abstract class AbstractServlet extends HttpServlet {

	protected static final String PRODUCT_DAO_ATTRIBUTE = "productDao";
	protected static final String TEMPLATE_PARAMETERS_ATTRIBUTE = "parameters";
	protected static final String PRODUCTS_ATTRIBUTE = "products";
	protected static final String CREATE_PRODUCT_ATTRIBUTE = "createProduct";
	protected static final String PRODUCT_ATTRIBUTE = "product";
	protected static final String ITEM_PARAMETER = "item";
	protected static final String PRODUCT_NAME_PARAMETER = "name";
	protected static final String PRODUCT_PRICE_PARAMETER = "price";
	protected static final String PRODUCT_CREATION_DATE_PARAMETER = "creationDate";

	@Override
	public void init() throws ServletException {
		super.init();
		initProductDao();
	}

	public abstract void doWork(HttpServletRequest req) throws ServletException;

	public abstract String getRedirectionResource();

	protected Map<String, Object> getTemplateParameters() {
		Map<String, Object> attributes = (Map<String, Object>) getServletContext()
				.getAttribute(TEMPLATE_PARAMETERS_ATTRIBUTE);
		if (attributes == null) {
			attributes = new HashMap<>();
			getServletContext().setAttribute(TEMPLATE_PARAMETERS_ATTRIBUTE, attributes);
		}
		return attributes;
	}

	protected Object getTemplateParameter(String name) {
		return getTemplateParameters().get(name);
	}

	protected void setTemplateParameter(String name, Object value) {
		getTemplateParameters().put(name, value);
	}

	private void initProductDao() {
		if (getProductDao() == null) {
			getServletContext().setAttribute(PRODUCT_DAO_ATTRIBUTE, createProductDao());
		}
	}

	protected ProductDao getProductDao() {
		return (ProductDao) getServletContext().getAttribute(PRODUCT_DAO_ATTRIBUTE);
	}

	private ProductDao createProductDao() {
		var props = new Properties();
		props.put("user", getServletContext().getInitParameter("user"));
		props.put("password", getServletContext().getInitParameter("password"));
		props.put("url", getServletContext().getInitParameter("url"));
		return new JdbcProductDao(props);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setStatus(HttpServletResponse.SC_OK);
		doWork(req);
		resp.getWriter().append(PageGenerator.instance().getPage(getRedirectionResource(), getTemplateParameters()));
	}

	protected void fetchProducts() {
		setTemplateParameter(PRODUCTS_ATTRIBUTE, getProductDao().getAll());
	}

	protected Product getProductFromList(HttpServletRequest req) throws ServletException {
		String itemParameter = defineParameter(req, ITEM_PARAMETER, "missing item parameter of product to operate on");
		List<Product> products = (List<Product>) defineTemplateParameter(PRODUCTS_ATTRIBUTE,
				"missing product list attribute");

		int index = Integer.parseInt(itemParameter);
		if (index <= 0 || index > products.size())
			throw new ServletException(
					String.format("wrong index %d, should be within [1,%d]", index, products.size()));

		return products.get(index - 1);
	}

	protected String defineParameter(HttpServletRequest req, String paramName, String exceptionMessage)
			throws ServletException {
		String parameter = req.getParameter(paramName);
		if (parameter == null) {
			throw new ServletException(exceptionMessage);
		}
		return parameter;
	}

	protected Object defineTemplateParameter(String attrName, String exceptionMessage) throws ServletException {
		Object parameter = getTemplateParameter(attrName);
		if (parameter == null) {
			throw new ServletException(exceptionMessage);
		}
		return parameter;
	}

}

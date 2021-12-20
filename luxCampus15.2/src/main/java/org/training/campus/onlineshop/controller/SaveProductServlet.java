package org.training.campus.onlineshop.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.training.campus.onlineshop.entity.Product;

public class SaveProductServlet extends AbstractServlet {

	protected static final String REDIRECTION_RESOURCE = "/products";

	public SaveProductServlet() {
		super(false);
	}

	@Override
	public void doWork(HttpServletRequest req) throws ServletException {
		updateSaveProduct(req);
		fetchProducts();
	}

	@Override
	public String getRedirectionResource() {
		return REDIRECTION_RESOURCE;
	}

	private void updateSaveProduct(HttpServletRequest req) throws ServletException {
		Boolean createFlag = (Boolean) defineTemplateParameter(CREATE_PRODUCT_ATTRIBUTE,
				"no create product attribute found");
		Product product;
		if (createFlag.booleanValue()) {
			product = new Product();
		} else {
			product = (Product) defineTemplateParameter(PRODUCT_ATTRIBUTE, "no product attribute found");
		}

		updateProductWithParameters(req, product);

		if (createFlag.booleanValue()) {
			getProductDao().persist(product);
		} else {
			getProductDao().merge(product);
		}

	}

	private void updateProductWithParameters(HttpServletRequest req, Product product) throws ServletException {
		String name = defineParameter(req, PRODUCT_NAME_PARAMETER, "missing product name parameter");
		String price = defineParameter(req, PRODUCT_PRICE_PARAMETER, "missing product price parameter");
		String creationDate = defineParameter(req, PRODUCT_CREATION_DATE_PARAMETER,
				"missing product creation date parameter");

		product.setName(name);
		product.setPrice(new BigDecimal(price));
		product.setCreationDate(LocalDate.parse(creationDate, DateTimeFormatter.ISO_LOCAL_DATE));
	}

}

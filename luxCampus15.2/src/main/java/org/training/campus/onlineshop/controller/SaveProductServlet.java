package org.training.campus.onlineshop.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.training.campus.onlineshop.entity.Product;

public class SaveProductServlet extends AbstractServlet {

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
		return "/products";
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
		product.setName(defineParameter(req, PRODUCT_NAME_PARAMETER, "missing product name parameter"));
		product.setPrice(
				new BigDecimal(defineParameter(req, PRODUCT_PRICE_PARAMETER, "missing product price parameter")));
		product.setCreationDate(LocalDate.parse(
				defineParameter(req, PRODUCT_CREATION_DATE_PARAMETER, "missing product creation date parameter"),
				DateTimeFormatter.ISO_LOCAL_DATE));
	}

}

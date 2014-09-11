package org.javamoney.examples.jaxrs.infrastructure.store;

import java.io.Serializable;

import org.javamoney.examples.jaxrs.model.Product;
import org.javamoney.moneta.Money;

class ProductJson implements Serializable {

	private static final long serialVersionUID = 2419998646680039026L;

	private String name;
	
	private String price;

	public String getName() {
		return name;
	}

	public ProductJson(Product product) {
		name = product.getName();
		price = product.getPrice().toString();
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	public Product to() {
		Product product = new Product();
		product.setName(name);
		product.setPrice(Money.parse(price));
		return product;
	}
	
}

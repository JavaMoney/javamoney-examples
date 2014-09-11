package org.javamoney.examples.jaxrs.resources.store;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.money.MonetaryAmount;
import javax.ws.rs.Path;

import org.javamoney.examples.jaxrs.currency.Brazil;
import org.javamoney.examples.jaxrs.ecommerce.Ecommerce;
import org.javamoney.examples.jaxrs.model.Product;

@Path("/brazil")
@RequestScoped
public class BrazilStoreCommerce implements Store {
	

	@Inject
	@Brazil
	private Ecommerce ecommerce;
	
	
	@Override
	public List<Product> products() {
		return ecommerce.products();
	}

	@Override
	public MonetaryAmount buy(List<Product> products) {
		return ecommerce.buy(products);
	}

	@Override
	public MonetaryAmount average(List<Product> products) {
		return ecommerce.average(products);
	}

	@Override
	public MonetaryAmount cheaper(List<Product> products) {
		return ecommerce.cheaper(products);
	}

	@Override
	public MonetaryAmount expensive(List<Product> products) {
		 return ecommerce.expensive(products);
	}

	@Override
	public String summary(List<Product> products) {
		return ecommerce.summary(products);	
	}

}

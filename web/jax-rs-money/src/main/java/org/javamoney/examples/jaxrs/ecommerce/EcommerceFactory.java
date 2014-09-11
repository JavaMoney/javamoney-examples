package org.javamoney.examples.jaxrs.ecommerce;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.money.CurrencyUnit;

import org.javamoney.examples.jaxrs.currency.America;
import org.javamoney.examples.jaxrs.currency.Argentina;
import org.javamoney.examples.jaxrs.currency.Brazil;
import org.javamoney.examples.jaxrs.currency.Europe;

@ApplicationScoped
public class EcommerceFactory {
	
	@Inject
	@Brazil
	private CurrencyUnit real;
	
	@Inject
	@America
	private CurrencyUnit dollar;
	
	@Inject
	@Europe
	private CurrencyUnit euro;
	
	@Inject
	@Argentina
	private CurrencyUnit peso;
	
	
	@Brazil
	@Produces
	public Ecommerce getBrazilEcommerce() {
		return new ECommerceImpl(real);
	}
	
	@America
	@Produces
	public Ecommerce getAmericaEcommerce() {
		return new ECommerceImpl(dollar);
	}
	
	@Argentina
	@Produces
	public Ecommerce getArgentinaEcommerce() {
		return new ECommerceImpl(peso);
	}
	
	@Europe
	@Produces
	public Ecommerce getEuropeEcommerce() {
		return new ECommerceImpl(euro);
	}
}

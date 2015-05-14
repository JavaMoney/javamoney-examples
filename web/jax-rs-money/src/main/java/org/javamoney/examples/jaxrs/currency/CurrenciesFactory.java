package org.javamoney.examples.jaxrs.currency;

import java.util.Locale;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.money.CurrencyUnit;
import javax.money.Monetary;

@ApplicationScoped
public class CurrenciesFactory {

	@Produces
	@Brazil
	public CurrencyUnit getBrazil() {
		return Monetary.getCurrency("BRL");
	}
	
	@Produces
	@America
	public CurrencyUnit getAmerica() {
		return Monetary.getCurrency(Locale.US);
	}
	
	@Produces
	@Argentina
	public CurrencyUnit getArgentina() {
		return Monetary.getCurrency("ARS");
	}
	
	@Produces
	@Europe
	public CurrencyUnit getEurope() {
		return Monetary.getCurrency("EUR");
	}
}

package org.javamoney.examples.jaxrs.currency;

import java.util.Locale;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.money.CurrencyUnit;
import javax.money.MonetaryCurrencies;

@ApplicationScoped
public class CurrenciesFactory {

	@Produces
	@Brazil
	public CurrencyUnit getBrazil() {
		return MonetaryCurrencies.getCurrency("BRL");
	}
	
	@Produces
	@America
	public CurrencyUnit getAmerica() {
		return MonetaryCurrencies.getCurrency(Locale.US);
	}
	
	@Produces
	@Argentina
	public CurrencyUnit getArgentina() {
		return MonetaryCurrencies.getCurrency("ARS");
	}
	
	@Produces
	@Europe
	public CurrencyUnit getEurope() {
		return MonetaryCurrencies.getCurrency("EUR");
	}
}

package javamoneyexamples.jsf.money.exchange;

import javax.enterprise.inject.Produces;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

public class ExchangeRateProducer {

	@Produces
	public ExchangeRateProvider get() {
		return MonetaryConversions.getExchangeRateProvider("ECB");
	}
}

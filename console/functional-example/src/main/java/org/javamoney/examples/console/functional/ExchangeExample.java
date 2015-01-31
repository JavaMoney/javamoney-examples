package org.javamoney.examples.console.functional;

import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryCurrencies;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

import org.javamoney.moneta.Money;

public class ExchangeExample {

	public static void main(String[] args) {
		ExchangeRateProvider imfRateProvider = MonetaryConversions
				.getExchangeRateProvider("IMF");
		ExchangeRateProvider ecbRateProvider = MonetaryConversions
				.getExchangeRateProvider("ECB");

		CurrencyUnit real = MonetaryCurrencies.getCurrency("BRL");
		CurrencyUnit dollar = MonetaryCurrencies.getCurrency(Locale.US);

		CurrencyConversion ecbDollarConvertion = ecbRateProvider
				.getCurrencyConversion(dollar);

		CurrencyConversion imfDollarConvertion = imfRateProvider
				.getCurrencyConversion(dollar);

		MonetaryAmount money = Money.of(10, real);
		System.out.println(money.with(ecbDollarConvertion));
		System.out.println(money.with(imfDollarConvertion));
	}

}

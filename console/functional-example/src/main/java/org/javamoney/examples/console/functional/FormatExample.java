package org.javamoney.examples.console.functional;

import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.Monetary;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.CurrencyStyle;

public class FormatExample {

	public static void main(String[] args) {
		CurrencyUnit dollar = Monetary.getCurrency(Locale.US);
		
		MonetaryAmount monetaryAmount = Money.of(1202.12D, dollar);
		MonetaryAmountFormat germanFormat = MonetaryFormats.getAmountFormat(
				Locale.GERMANY);
		MonetaryAmountFormat usFormat = MonetaryFormats.getAmountFormat(
				Locale.US);
		 MonetaryAmountFormat customFormat = MonetaryFormats.getAmountFormat(
	                AmountFormatQueryBuilder.of(Locale.US).set(CurrencyStyle.SYMBOL).build());
		 
		System.out.println(germanFormat.format(monetaryAmount));//1.202,12 USD
		System.out.println(usFormat.format(monetaryAmount));//USD1,202.12
		System.out.println(customFormat.format(monetaryAmount));//$1,202.12
	}
}

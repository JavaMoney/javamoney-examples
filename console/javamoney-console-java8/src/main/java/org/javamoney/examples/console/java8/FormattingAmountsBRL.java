package org.javamoney.examples.console.java8;

import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.CurrencyStyle;

public class FormattingAmountsBRL {

	public static void main(String[] args) {

		CurrencyUnit real = Monetary.getCurrency("BRL");

		MonetaryAmount ma1 = Money.of(100000, real);
		MonetaryAmount ma2 = Money.of(200, real);

		MonetaryAmount sum = ma1.add(ma2);
		System.out.println(sum);

		MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(new Locale("pt", "BR"));

		System.out.println(format.format(ma1));
		
		MonetaryAmountFormat format2 = MonetaryFormats.getAmountFormat(
			      AmountFormatQueryBuilder.of(new Locale ("pt", "BR"))
			        .set(CurrencyStyle.NAME) // options(CODE,NAME,NUMERIC_CODE,SYMBOL)
			        .build()
			    );

		System.out.println(format2.format(sum));
	}
}
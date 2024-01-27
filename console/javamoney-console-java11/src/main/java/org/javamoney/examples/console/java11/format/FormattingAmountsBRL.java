package org.javamoney.examples.console.java11.format;

import java.util.Locale;

import javax.money.Monetary;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryFormats;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.CurrencyStyle;

public class FormattingAmountsBRL {

	public static void main(String[] args) {

		var real = Monetary.getCurrency("BRL");

		var ma1 = Money.of(100000, real);
		var ma2 = Money.of(200, real);

		var sum = ma1.add(ma2);
		System.out.println(sum);

		var format = MonetaryFormats.getAmountFormat(new Locale("pt", "BR"));

		System.out.println(format.format(ma1));
		
		var format2 = MonetaryFormats.getAmountFormat(
			      AmountFormatQueryBuilder.of(new Locale ("pt", "BR"))
			        .set(CurrencyStyle.NAME) // options(CODE,NAME,NUMERIC_CODE,SYMBOL)
			        .build()
			    );

		System.out.println(format2.format(sum));
	}
}
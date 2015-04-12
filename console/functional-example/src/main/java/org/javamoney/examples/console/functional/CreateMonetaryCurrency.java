package org.javamoney.examples.console.functional;

import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.Monetary;

import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;

public class CreateMonetaryCurrency {

	public static void main(String[] args) {
		CurrencyUnit real = Monetary.getCurrency("BRL");
		CurrencyUnit dollar = Monetary.getCurrency(Locale.US);
        MonetaryAmount money = Money.of(120, real);
        MonetaryAmount fastMoney = FastMoney.of(80, dollar);
        System.out.println(money);
        System.out.println(fastMoney);
	}
}

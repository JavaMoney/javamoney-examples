package org.javamoney.examples.console.functional;

import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryCurrencies;

import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;

public class CreateMonetaryCurrency {

	public static void main(String[] args) {
		CurrencyUnit real = MonetaryCurrencies.getCurrency("BRL");
		CurrencyUnit dollar = MonetaryCurrencies.getCurrency(Locale.US);
        MonetaryAmount money = Money.of(120, real);
        MonetaryAmount fastMoney = FastMoney.of(80, dollar);
        System.out.println(money);
        System.out.println(fastMoney);
	}
}

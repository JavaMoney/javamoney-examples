package org.javamoney.examples.console.functional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.Monetary;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryFunctions;

public class MonetaryFilterOperations {
	private static CurrencyUnit DOLLAR = Monetary.getCurrency(Locale.US);
	private static CurrencyUnit EURO = Monetary.getCurrency("EUR");

	public static void main(String[] args) {
	
		MonetaryAmount money = Money.of(BigDecimal.valueOf(100D), DOLLAR);
		MonetaryAmount min = Money.of(BigDecimal.valueOf(6D), DOLLAR);
		MonetaryAmount max = Money.of(BigDecimal.valueOf(100D), DOLLAR);
		
		List<MonetaryAmount> moneys = getMoneys();
		
		List<MonetaryAmount> justDollar = moneys.stream()
				.filter((MonetaryFunctions.isCurrency(DOLLAR)))
				.collect(Collectors.toList());
		List<MonetaryAmount> notEuro = moneys.stream().filter((MonetaryFunctions.isCurrency(EURO))).collect(Collectors.toList());
//		List<MonetaryAmount> euroOrDollar = moneys.stream().filter((MonetaryFunctions.containsCurrencies(EURO, DOLLAR))).collect(Collectors.toList());
		List<MonetaryAmount> dollarGreaterOneHundred = moneys.stream().filter((MonetaryFunctions.isCurrency(DOLLAR).and(MonetaryFunctions.isGreaterThan(money)))).collect(Collectors.toList());
		List<MonetaryAmount> dollarGreaterOneHundredDistinct = moneys.stream().distinct().filter((MonetaryFunctions.isCurrency(DOLLAR).and(MonetaryFunctions.isGreaterThan(money)))).collect(Collectors.toList());
		List<MonetaryAmount> between = moneys.stream().filter((MonetaryFunctions.isCurrency(DOLLAR).and(MonetaryFunctions.isBetween(min, max)))).collect(Collectors.toList());
		
		System.out.println(justDollar);
		System.out.println(notEuro);
//		System.out.println(euroOrDollar);
		System.out.println(dollarGreaterOneHundred);
		System.out.println(dollarGreaterOneHundredDistinct);
		System.out.println(between);
		
	}
	
	
	public static List<MonetaryAmount> getMoneys() {
		List<MonetaryAmount> moneys = new ArrayList<>();
		moneys.add(Money.of(120, DOLLAR));
		moneys.add(Money.of(50, DOLLAR));
		moneys.add(Money.of(80, DOLLAR));
		moneys.add(Money.of(90, DOLLAR));
		moneys.add(Money.of(120, DOLLAR));
		
		
		moneys.add(Money.of(120, EURO));
		moneys.add(Money.of(50, EURO));
		moneys.add(Money.of(80, EURO));
		moneys.add(Money.of(90, EURO));
		moneys.add(Money.of(120, EURO));
		return moneys;
	}
}

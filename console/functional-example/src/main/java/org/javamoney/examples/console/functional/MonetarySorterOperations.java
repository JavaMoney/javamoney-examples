package org.javamoney.examples.console.functional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryCurrencies;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryFunctions;

public class MonetarySorterOperations {

	
	public static void main(String[] args) {
		List<MonetaryAmount> orderCurrency = getDollars().stream().sorted(MonetaryFunctions.sortCurrencyUnit()).collect(Collectors.toList());
		List<MonetaryAmount> orderSort = getDollars()
				.stream()
				.sorted(MonetaryFunctions.sortCurrencyUnit().thenComparing(
						MonetaryFunctions.sortNumber()))
				.collect(Collectors.toList());
		List<MonetaryAmount> orderCurrencyNumber = getDollars()
				.stream()
				.sorted(MonetaryFunctions.sortCurrencyUnit().thenComparing(
						MonetaryFunctions.sortCurrencyUnitDesc()))
				.collect(Collectors.toList());
		
		System.out.println(orderCurrency);
		System.out.println(orderSort);
		System.out.println(orderCurrencyNumber);
		
	}
	
	public static List<MonetaryAmount> getDollars() {
		CurrencyUnit dollar = MonetaryCurrencies.getCurrency(Locale.US);
		List<MonetaryAmount> moneys = new ArrayList<>();
		moneys.add(Money.of(120, dollar));
		moneys.add(Money.of(50, dollar));
		moneys.add(Money.of(80, dollar));
		moneys.add(Money.of(90, dollar));
		moneys.add(Money.of(120, dollar));
		return moneys;
	}
}

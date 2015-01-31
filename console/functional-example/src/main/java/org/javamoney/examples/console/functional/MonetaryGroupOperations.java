package org.javamoney.examples.console.functional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryCurrencies;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.GroupMonetarySummaryStatistics;
import org.javamoney.moneta.function.MonetaryFunctions;
import org.javamoney.moneta.function.MonetarySummaryStatistics;

public class MonetaryGroupOperations {

	private static CurrencyUnit DOLLAR = MonetaryCurrencies.getCurrency(Locale.US);
	private static CurrencyUnit EURO = MonetaryCurrencies.getCurrency("EUR");


	public static void main(String[] args) {
		Map<CurrencyUnit, List<MonetaryAmount>> groupBy = getCurrencies()
				.stream().collect(MonetaryFunctions.groupByCurrencyUnit());
		MonetarySummaryStatistics summary = getCurrencies().stream()
				.filter(MonetaryFunctions.isCurrency(DOLLAR))
				.collect(MonetaryFunctions.summarizingMonetary(DOLLAR));
		GroupMonetarySummaryStatistics groupSummary = getCurrencies().stream()
				.filter(MonetaryFunctions.isCurrency(DOLLAR))
				.collect(MonetaryFunctions.groupBySummarizingMonetary());

		System.out.println(groupBy);
		System.out.println(summary.getMin());
		System.out.println(summary.getMax());
		System.out.println(summary.getAverage());
		System.out.println(summary.getCount());
		System.out.println(groupSummary);
	}

	public static List<MonetaryAmount> getCurrencies() {


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

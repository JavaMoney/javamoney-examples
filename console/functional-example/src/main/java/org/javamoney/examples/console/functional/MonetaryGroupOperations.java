/*
 * JavaMoney Examples
 * Copyright 2015-2019, Werner Keil, Anatole Tresch
 * and individual contributors by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.javamoney.examples.console.functional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.Monetary;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.GroupMonetarySummaryStatistics;
import org.javamoney.moneta.function.MonetaryFunctions;
import org.javamoney.moneta.function.MonetarySummaryStatistics;

public class MonetaryGroupOperations {

	private static CurrencyUnit DOLLAR = Monetary.getCurrency(Locale.US);
	private static CurrencyUnit EURO = Monetary.getCurrency("EUR");

	public static void main(String[] args) {
		Map<CurrencyUnit, List<MonetaryAmount>> groupBy = getMoneys()
				.stream().collect(MonetaryFunctions.groupByCurrencyUnit());
		MonetarySummaryStatistics summary = getMoneys().stream()
				.filter(MonetaryFunctions.isCurrency(DOLLAR))
				.collect(MonetaryFunctions.summarizingMonetary(DOLLAR));
		GroupMonetarySummaryStatistics groupSummary = getMoneys().stream()
				.filter(MonetaryFunctions.isCurrency(DOLLAR))
				.collect(MonetaryFunctions.groupBySummarizingMonetary());

		System.out.println(groupBy);
		System.out.println(summary.getMin());
		System.out.println(summary.getMax());
		System.out.println(summary.getAverage());
		System.out.println(summary.getCount());
		System.out.println(groupSummary);
	}

	private static List<MonetaryAmount> getMoneys() {
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

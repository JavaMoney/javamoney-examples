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

import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.Monetary;
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

		CurrencyUnit euro = Monetary.getCurrency("EUR");
		CurrencyUnit dollar = Monetary.getCurrency(Locale.US);

		CurrencyConversion ecbDollarConvertion = ecbRateProvider
				.getCurrencyConversion(dollar);

		CurrencyConversion imfDollarConvertion = imfRateProvider
				.getCurrencyConversion(dollar);

		MonetaryAmount money = Money.of(10, euro);
		System.out.println(money.with(ecbDollarConvertion));
		System.out.println(money.with(imfDollarConvertion));
	}
}

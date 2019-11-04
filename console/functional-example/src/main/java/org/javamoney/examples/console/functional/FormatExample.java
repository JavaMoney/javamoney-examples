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

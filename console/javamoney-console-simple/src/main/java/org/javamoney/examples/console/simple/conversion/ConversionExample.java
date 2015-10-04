/**
 * Copyright (c) 2012, 2015, Anatole Tresch, Werner Keil and others by the @author tag.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.javamoney.examples.console.simple.conversion;

import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import javax.money.convert.ConversionQueryBuilder;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;

import java.text.MessageFormat;
import java.time.LocalDate;

/**
 * Showing accessing exchange rates and doing conversions.
 */
public class ConversionExample {
	private static final String DEFAULT_TERM_CURRENCY_CODE = "CHF";

	public static void main(String... args) {
		String termCurrencyCode = DEFAULT_TERM_CURRENCY_CODE;
		if (args.length > 0) {
			termCurrencyCode = args[0];
		}
		final MonetaryAmount amt = Money.of(2000, "EUR");
		CurrencyConversion conv= MonetaryConversions.getConversion(termCurrencyCode, "ECB");
		System.out.println(MessageFormat.format("2000 EUR (ECB)-> {0} = {1}",
				termCurrencyCode, amt.with(conv)));
		conv= MonetaryConversions.getConversion(termCurrencyCode, "IMF");
		System.out.println(MessageFormat.format("2000 EUR (IMF)-> {0} = {1}",
				termCurrencyCode, amt.with(conv)));

		System.out.println(MessageFormat.format(
				"2000 EUR (ECB, at 5th Jan 2015)-> {0} = {1}",
				termCurrencyCode, amt.with(MonetaryConversions
						.getConversion(ConversionQueryBuilder.of()
								.setTermCurrency(termCurrencyCode)
								.set(LocalDate.of(2015, 01, 05)).build()))));
	}

}

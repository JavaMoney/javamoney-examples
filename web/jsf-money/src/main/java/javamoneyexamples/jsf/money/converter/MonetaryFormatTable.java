/*
 *  Copyright (c) 2012, 2015, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Contributors:
 *    Otavio Santana - initial implementation
 *    Werner Keil - extensions and adaptions.
 */
package javamoneyexamples.jsf.money.converter;

import java.io.IOException;
import java.text.NumberFormat;

import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatContext;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryParseException;

import org.javamoney.moneta.Money;

public class MonetaryFormatTable implements MonetaryAmountFormat {

	@Override
	public String queryFrom(MonetaryAmount amount) {
		return amount.toString();
	}

	@Override
	public AmountFormatContext getContext() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void print(Appendable appendable, MonetaryAmount amount) throws IOException {
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(2);
		format.setMinimumFractionDigits(2);
		appendable.append(amount.getCurrency().toString());
		appendable.append(" ").append(format.format(amount.getNumber()));
	}

	@Override
	public MonetaryAmount parse(CharSequence text) throws MonetaryParseException {
		return Money.parse(text);
	}

}

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

import org.javamoney.moneta.Money;

public class BasicOperations {

	public static void main(String[] args) {
		CurrencyUnit dollar = Monetary.getCurrency(Locale.US);
        MonetaryAmount money = Money.of(120, dollar);
        MonetaryAmount money2 = Money.of(50, dollar);
        System.out.println(money.add(money2));
        System.out.println(money.subtract(money2));
        System.out.println(money.multiply(2));
        System.out.println(money.divide(2));
        System.out.println(money.isEqualTo(money2));
        System.out.println(money.isGreaterThan(money2));
        System.out.println(money.isGreaterThanOrEqualTo(money2));
        System.out.println(money.isGreaterThanOrEqualTo(money2));
        System.out.println(money.isLessThan(money2));
        System.out.println(money.isLessThanOrEqualTo(money2));
        System.out.println(money.isNegative());
        System.out.println(money.isNegativeOrZero());
	}
}

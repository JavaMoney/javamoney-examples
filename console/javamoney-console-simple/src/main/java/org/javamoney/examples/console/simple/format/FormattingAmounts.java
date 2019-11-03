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
package org.javamoney.examples.console.simple.format;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.AmountFormatParams;
import org.javamoney.moneta.format.CurrencyStyle;

import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

/**
 * Created by Anatole on 14.05.2015.
 */
public class FormattingAmounts {

    public static void main(String... args) {
        MonetaryAmount amt = Money.of(1234.5678, "EUR");
        System.out.println(amt.query(MonetaryFormats.getAmountFormat(Locale.GERMANY)));
        System.out.println(MonetaryFormats.getAmountFormat(Locale.GERMANY).format(amt));
        amt = Money.of(123412341234.5678, "INR");
        System.out.println(MonetaryFormats.getAmountFormat(new Locale("", "INR")).format(amt));

        // now with adaptive groupings
        System.out.println(MonetaryFormats.getAmountFormat(
                AmountFormatQueryBuilder.of(new Locale("", "INR"))
                        .set(AmountFormatParams.GROUPING_SIZES, new int[]{3, 2})
                        .set(AmountFormatParams.GROUPING_GROUPING_SEPARATORS, new char[]{',', '`'})
                        .build())
                .format(amt));
        
        amt = Money.of(5, "USD");
        System.out.println(MonetaryFormats.getAmountFormat(AmountFormatQueryBuilder.of(Locale.US).set(CurrencyStyle.SYMBOL).set(AmountFormatParams.PATTERN, "¤##.##").build()).format(amt));
    }
}

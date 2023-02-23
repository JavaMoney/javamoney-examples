/*	
 * JavaMoney Examples	
 * Copyright 2012-2020, Werner Keil 	
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
package org.javamoney.examples.console.java10.format;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.AmountFormatParams;
import org.javamoney.moneta.format.CurrencyStyle;

import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

/**
 * Created by Werner on 11.10.2019.
 */
public class FormattingAmounts {

    public static void main(String... args) {
        final var amt = Money.of(1234.5678, "EUR");
        System.out.println(amt);
        System.out.println(amt.query(MonetaryFormats.getAmountFormat(Locale.GERMANY)));
        System.out.println(MonetaryFormats.getAmountFormat(Locale.GERMANY).format(amt));
        var newAmt = Money.of(123412341234.5678, "INR");
        System.out.println(MonetaryFormats.getAmountFormat(new Locale("", "INR")).format(newAmt));
        var str = amt.toString();
        var parsedAmt = Money.parse(str);
        System.out.println(parsedAmt);
        System.out.println(amt.equals(parsedAmt));

        // no with adaptive groupings
        System.out.println(MonetaryFormats.getAmountFormat(
                AmountFormatQueryBuilder.of(new Locale("", "INR"))
                        .set(AmountFormatParams.GROUPING_SIZES, new int[]{2, 3})
                        .set(AmountFormatParams.GROUPING_GROUPING_SEPARATORS, new char[]{',', '`'})
                        .build())
                .format(amt));
        
        newAmt = Money.of(5, "USD");
        System.out.println(MonetaryFormats.getAmountFormat(AmountFormatQueryBuilder.of(Locale.US).set(CurrencyStyle.SYMBOL).set(AmountFormatParams.PATTERN, "¤##.##").build()).format(newAmt));
        
        final var MONEY_FORMATTER = MonetaryFormats.getAmountFormat(Locale.ENGLISH);

        final var startBidMoney = Money.of(25000d, "CAD");
        final String money = MONEY_FORMATTER.format(startBidMoney);
        System.out.println(money);
        var parsedMoney = Money.parse(money, MONEY_FORMATTER);
        System.out.println(parsedMoney);
    }
}

/*	
 * JavaMoney Examples	
 * Copyright 2012-2023, Werner Keil
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
package org.javamoney.examples.console.java11.format;

import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import javax.money.MonetaryContext;
import javax.money.MonetaryContextBuilder;

/**
 * Created by Werner on 13.02.2023.
 */
public class ParsingAmounts {

    public static void main(String... args) {
        MonetaryAmount source = Money.of(1.23456, "EUR");
        String srcTxt = source.toString();
        MonetaryAmount dest = Money.parse(srcTxt);
        System.out.println(dest);
        System.out.println(source.equals(dest));

        MonetaryContext preciseCtx = MonetaryContextBuilder.of().set("AmountFlavor", "PRECISION").setPrecision(6).build();

        source = Money.of(1.23456, "EUR", preciseCtx);
        System.out.println(source.toString());
    }
}

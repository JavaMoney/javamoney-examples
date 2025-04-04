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
package org.javamoney.examples.console.eleven.core;

import org.javamoney.examples.console.eleven.util.ConsoleUtils;
import org.javamoney.moneta.FastMoney;	
import org.javamoney.moneta.Money;	

import javax.money.Monetary;	
import javax.money.MonetaryAmountFactoryQueryBuilder;	


/**	
 * @author Werner Keil	
 * @version 1.0	
 */	
public class AmountsDoCalculations {	

    /**	
     * @param args	
     */	
    public static void main(String[] args) {	
        var amount = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(234).create();	
        ConsoleUtils.printDetails(amount);	

        amount = Monetary.getAmountFactory(FastMoney.class).setCurrency("EUR").setNumber(234).create();	
        ConsoleUtils.printDetails(amount);	

        amount = Monetary.getAmountFactory(	
                   MonetaryAmountFactoryQueryBuilder.of().setMaxScale(50).setPrecision(30).build())	
                     .setCurrency("EUR").setNumber(234).create();	
        ConsoleUtils.printDetails(amount);	

		var amt1 = Money.of(10.1234556123456789, "USD");	
		var amt2 = FastMoney.of(123456789, "USD");	

		var total = amt1.add(amt2).multiply(0.5)	
                .remainder(1);	
        ConsoleUtils.printDetails(total);	
    }	
}
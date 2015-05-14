/*
 * JavaMoney Examples
 * Copyright 2012-2014, Werner Keil 
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
package org.javamoney.examples.console.simple.core;

import org.javamoney.moneta.CurrencyUnitBuilder;

import javax.money.CurrencyQueryBuilder;
import javax.money.Monetary;


public class CurrenciesAccess {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ConsoleUtils.printDetails(Monetary.getCurrency("CHF"));

        Monetary.getCurrencies(CurrencyQueryBuilder.of().setProviderNames("ConfigurableCurrencyUnitProvider").build()).forEach(
                ConsoleUtils::printDetails
        );

        CurrencyUnitBuilder.of("GeeCon", "GeeCon-Conference").build(true);
        Monetary.getCurrencies(CurrencyQueryBuilder.of().setProviderNames("ConfigurableCurrencyUnitProvider").build()).forEach(
                ConsoleUtils::printDetails
        );
    }

    private static void queryAllCurrenciesFromProvider(){

//        printDetails(Monetary.getCurrency("CHF"));
    }


}

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
package org.javamoney.examples.console.simple.core;

import org.javamoney.examples.console.simple.util.ConsoleUtils;
import org.javamoney.moneta.spi.ConfigurableCurrencyUnitProvider;

import javax.money.CurrencyContext;
import javax.money.CurrencyContextBuilder;
import javax.money.CurrencyUnit;
import javax.money.Monetary;

/**
 * Programmatically registers a CurrencyUnit on the fly into the current registry.
 */
public class CurrenciesProgrammaticallyRegister {

    public static void main(String[] args) {
        CurrencyUnit onTheFlyUnit = new CurrencyUnit() {

            private CurrencyContext context = CurrencyContextBuilder.of("Devoxx-ToolsInAction").build();

            @Override
            public int compareTo(CurrencyUnit o) {
                return this.getCurrencyCode().compareTo(o.getCurrencyCode());
            }

            @Override
            public String getCurrencyCode() {
                return "DevoxxFranc";
            }

            @Override
            public int getNumericCode() {
                return 800;
            }

            @Override
            public int getDefaultFractionDigits() {
                return 0;
            }

            @Override
            public CurrencyContext getContext() {
                return context;
            }
        };
        ConfigurableCurrencyUnitProvider.registerCurrencyUnit(onTheFlyUnit);
        ConsoleUtils.printDetails(Monetary.getCurrency("DevoxxFranc"));
    }
}

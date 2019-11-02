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
package org.javamoney.examples.console.simple.util;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

/**
 * Created by Anatole on 14.05.2015.
 */
public final class ConsoleUtils {

    private ConsoleUtils(){}

    public static void printDetails(CurrencyUnit cu) {
        if (cu == null) {
            System.out.println("N/A");
        } else {
            System.out.println("CurrencyUnit '" + cu.toString() + "':\n" +
                    "  Class                 : " + cu.getClass().getName() + "\n" +
                    "  Currency Code         : " + cu.getCurrencyCode() + "\n" +
                    "  Num.Code              : " + cu.getNumericCode() + "\n" +
                    "  DefaultFraction Digits: " + cu.getDefaultFractionDigits() + "\n" +
                    "  Context               : " + cu.getContext());
        }
    }

    public static void printDetails(String title, CurrencyUnit cu) {
        if (cu == null) {
            System.out.println(title + " -> N/A");
        } else {
            System.out.println(title + " -> CurrencyUnit '" + cu.toString() + "':\n" +
                    "  Class                 : " + cu.getClass().getName() + "\n" +
                    "  Currency Code         : " + cu.getCurrencyCode() + "\n" +
                    "  Num.Code              : " + cu.getNumericCode() + "\n" +
                    "  DefaultFraction Digits: " + cu.getDefaultFractionDigits() + "\n" +
                    "  Context               : " + cu.getContext());
        }
    }

    public static void printDetails(MonetaryAmount am) {
        if (am == null) {
            System.out.println("N/A");
        } else {
            System.out.println("Amount '" + am.toString() + "':\n" +
                    "  Class                 : " + am.getClass().getName() + "\n" +
                    "  CurrencyUnit          : " + am.getCurrency().getCurrencyCode() + "\n" +
                    "  Number                : " + am.getNumber() + "\n" +
                    "  Context               : " + am.getContext());
        }
    }

    public static void printDetails(String title, MonetaryAmount am) {
        if (am == null) {
            System.out.println(title + " -> N/A");
        } else {
            System.out.println(title + " -> Amount '" + am.toString() + "':\n" +
                    "  Class                 : " + am.getClass().getName() + "\n" +
                    "  CurrencyUnit          : " + am.getCurrency().getCurrencyCode() + "\n" +
                    "  Number                : " + am.getNumber() + "\n" +
                    "  Context               : " + am.getContext());
        }
    }
}

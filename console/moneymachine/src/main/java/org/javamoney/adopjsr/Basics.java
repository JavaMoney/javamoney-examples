/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch). Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License. Contributors: Anatole Tresch - initial version.
 */
package org.javamoney.adopjsr;

import javax.money.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collection;
import java.util.Locale;

/**
 * This class has to be implemented and helps us giving feedback on the JSR 354's API. This part of the
 * project deals with basic aspects such as getting currencies and creating amounts of different types and with
 * different
 * capabilities.
 * <p>
 * Created by Anatole on 07.03.14.
 */
public class Basics{

    /**
     * Get a {@link javax.money.CurrencyUnit} using a currency code.
     *
     * @param currencyCode the currency code
     * @return the corresponding CurrencyUnit instance.
     * @see javax.money.Monetary
     */
    public CurrencyUnit getProvidedCurrency(String currencyCode){
        // throw new UnsupportedOperationException();
        return Monetary.getCurrency(currencyCode);
    }

    /**
     * Get a {@link javax.money.CurrencyUnit} using a Locale, modeling a country.
     *
     * @param locale The country locale.
     * @return the corresponding CurrencyUnit instance.
     * @see javax.money.Monetary
     * @see java.util.Locale
     */
    public CurrencyUnit getProvidedCurrency(Locale locale){
        throw new UnsupportedOperationException();
    }

    /**
     * Create a custom {@link javax.money.CurrencyUnit}.
     *
     * @param currencyCode         the currency code
     * @param numericCode          the numeric code.
     * @param defaultFractionUnits the default fraction units.
     * @return the new currency unit instance created.
     * @see org.javamoney.moneta.BuildableCurrencyUnit
     */
    public CurrencyUnit buildCustomCurrency(String currencyCode, int numericCode, int defaultFractionUnits){
        throw new UnsupportedOperationException();
    }

    /**
     * Build and register a {@link javax.money.CurrencyUnit}.
     *
     * @param currencyCode         the currency code (non null).
     * @param numericCode          the numeric code.
     * @param defaultFractionUnits the default fraction units.
     * @return the CurrencyUnit. Additionally the unit should be registered,
     * so it accessible from {@link }MonetaryCurrencies}.
     * @see org.javamoney.moneta.BuildableCurrencyUnit
     * @see javax.money.Monetary
     */
    public CurrencyUnit buildAndRegisterCustomCurrency(String currencyCode, int numericCode, int defaultFractionUnits){
        throw new UnsupportedOperationException();
    }

    /**
     * Evaluate the current registered implementation {@link javax.money.MonetaryAmount} types for amounts.
     *
     * @return the current amount types.
     * @see javax.money.Monetary
     */
    public Collection<Class<? extends MonetaryAmount>> getMonetaryAmountTypes(){
        throw new UnsupportedOperationException();
    }

    /**
     * Evaluate the current registered default implementation {@link javax.money.MonetaryAmount} type for amounts.
     *
     * @return the current default amount type.
     * @see javax.money.Monetary
     */
    public Class<? extends MonetaryAmount> getDefaultMonetaryAmountType(){
        throw new UnsupportedOperationException();
    }

    /**
     * Lookup the a corresponding factory for creating {@link javax.money.MonetaryAmount} instances of a certain type.
     *
     * @param moneyType the monetary amount's implementation class.
     * @return the corresponding factory, not null.
     * @see javax.money.Monetary
     */
    public <T extends MonetaryAmount> MonetaryAmountFactory<T> getMoneyFactory(Class<T> moneyType){
        throw new UnsupportedOperationException();
    }

    /**
     * Get an amount with the given amount and currency. THe advanced might try to of an amount that has a
     * {@link javax.money.CurrencyUnit}, which is not registered in {@link javax.money.Monetary}.
     *
     * @param number       the amount
     * @param currencyCode the currency code
     * @return a corresponding amount instance.
     * @see javax.money.Monetary
     * @see javax.money.MonetaryAmountFactory
     */
    public MonetaryAmount getMoney(Number number, String currencyCode){
        throw new UnsupportedOperationException();
    }

    /**
     * Converts the given amount to a new amount with the given BigDecimal value and currencyCode.
     *
     * @param amount       the amount
     * @param bd           the BD number
     * @param currencyCode the currency code
     * @return a corresponding amount instance.
     * @see javax.money.Monetary
     * @see javax.money.MonetaryAmountFactory
     */
    public MonetaryAmount convertAmount(MonetaryAmount amount, BigDecimal bd, String currencyCode){
        throw new UnsupportedOperationException();
    }

    /**
     * Change the numeric capabilities of the given amount to a new capabilities given.
     *
     * @param amount    the amount
     * @param scale     the maximal scale
     * @param precision the target precision
     * @param context   the MathContext
     * @return a corresponding amount instance.
     * @see javax.money.Monetary
     * @see javax.money.MonetaryAmountFactory
     * @see javax.money.MonetaryContext
     */
    public MonetaryAmount convertAmount(MonetaryAmount amount, int scale, int precision, MathContext context){
        throw new UnsupportedOperationException();
    }

    /**
     * Create a {@link javax.money.MonetaryAmount} that covers the capabilities as defined in the {@link javax.money
     * .MonetaryContext}.
     *
     * @param number       the amount
     * @param currencyCode the currency code
     * @param context      the Monetary context
     * @return an according money instance
     * @see javax.money.Monetary
     * @see javax.money.MonetaryAmountFactory
     * @see javax.money.MonetaryContext
     */
    public MonetaryAmount getMoneyWithContext(Number number, String currencyCode, MonetaryContext context){
        throw new UnsupportedOperationException();
    }

    /**
     * Create a {@link javax.money.MonetaryAmount} that covers the following capabilities:
     * <ul>
     * <li>It must support a precision of 256.</li>
     * <li>It must support a scale of 128.</li>
     * <li>It must use {@link java.math.RoundingMode#FLOOR}</li>
     * </ul>
     * Within this test you should query a corresponding implementation type and then of the corresponding amount.
     *
     * @param number       the amount
     * @param currencyCode the currency code
     * @return an according money instance
     * @see javax.money.MonetaryAmounts
     * @see javax.money.MonetaryAmountFactory
     * @see javax.money.MonetaryContext
     */
    public MonetaryAmount getMoneyWithSpecificCapabilities(Number number, String currencyCode){
        throw new UnsupportedOperationException();
    }
}

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

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.convert.ConversionContext;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ProviderContext;
import java.time.LocalDate;
import java.util.List;

/**
 * API test class that allows to test out the API for currency conversion. Hint the available providers can be
 * evaluated by calling {@link javax.money.convert.MonetaryConversions#getProviderNames()}, by default the moneta
 * reference implementation comes with the following providers:
 * <ul>
 * <li>IMF - International Monetary Fonds (current day rates).</li>
 * <li>ECB - European Central Bank (current day rates)</li>
 * <li>ECB-HIST90 - European Central Bank (historic rates, back to 1990).</li>
 * </ul>
 * Created by Anatole on 21.03.14.
 *
 * @see javax.money.convert.MonetaryConversions
 */
public class Conversions {

    /**
     * Get the corresponding exchange rate provided by the International Monetary Fonds (IMF).
     *
     * @param src the source/base {@link javax.money.CurrencyUnit}, not null
     * @param tgt the target {@link javax.money.CurrencyUnit}, not null
     * @return the rate found.
     * @see javax.money.convert.MonetaryConversions
     * @see javax.money.convert.ExchangeRateProvider
     */
    public ExchangeRate getExchangeRateFromIMF(CurrencyUnit src, CurrencyUnit tgt) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get the an exchange rate provided by the International Monetary Fonds (IMF) for a given UTC timestamp.
     *
     * @param src       the source/base {@link javax.money.CurrencyUnit}, not null
     * @param tgt       the target {@link javax.money.CurrencyUnit}, not null
     * @param timestamp The target UTC timestamp
     * @return the rate found.
     * @see javax.money.convert.MonetaryConversions
     * @see javax.money.convert.ExchangeRateProvider
     */
    public ExchangeRate getExchangeRateWithTime(CurrencyUnit src, CurrencyUnit tgt, LocalDate timestamp) {
        throw new UnsupportedOperationException();
    }

    /**
     * Converts an amount to the given target currency, using the current default conversion.
     *
     * @param tgt    The target currency
     * @param amount the amount to be converted
     * @return the converted amount, not null.
     * @see javax.money.convert.MonetaryConversions
     * @see javax.money.convert.CurrencyConversion
     * @see javax.money.MonetaryOperator
     */
    public MonetaryAmount convertAmountDefault(CurrencyUnit tgt, MonetaryAmount amount) {
        throw new UnsupportedOperationException();
    }

    /**
     * Evaluate the current default Conversion Context.
     *
     * @param src the source/base {@link javax.money.CurrencyUnit}, not null
     * @param tgt the target {@link javax.money.CurrencyUnit}, not null
     * @return the converted amount, not null.
     * @see javax.money.convert.MonetaryConversions
     * @see javax.money.convert.CurrencyConversion
     * @see javax.money.MonetaryOperator
     */
    public ConversionContext getDefaultConversionContext(CurrencyUnit src, CurrencyUnit tgt) {
        throw new UnsupportedOperationException();
    }

    /**
     * Evaluate the Provider Context of the IMF provider. Optionally print the details to the console.
     *
     * @return the ConversionContext, not null.
     * @see javax.money.convert.MonetaryConversions
     * @see javax.money.convert.ExchangeRateProvider
     */
    public ProviderContext getIMFProviderContext() {
        throw new UnsupportedOperationException();
    }

    /**
     * Evaluate the Provider Context of the ECB provider. Optionally print the details to the console.
     *
     * @return the ConversionContext, not null.
     * @see javax.money.convert.MonetaryConversions
     * @see javax.money.convert.ExchangeRateProvider
     */
    public ProviderContext getECBProviderContext() {
        throw new UnsupportedOperationException();
    }

    /**
     * Evaluate the Provider Context of the ECB provider. Optionally print the details to the console.
     *
     * @return the ConversionContext, not null.
     * @see javax.money.convert.MonetaryConversions
     * @see javax.money.convert.ExchangeRateProvider
     */
    public ProviderContext getDefaultProviderContext() {
        throw new UnsupportedOperationException();
    }

    /**
     * Converts an amount to the given target currency, based on the given timestamp.
     *
     * @param tgt       The target currency
     * @param amount    the amount to be converted
     * @param timestamp the target timestamp
     * @return the converted amount, not null.
     * @see javax.money.convert.MonetaryConversions
     * @see javax.money.convert.CurrencyConversion
     * @see javax.money.MonetaryOperator
     */
    public MonetaryAmount convertAmount(CurrencyUnit tgt, MonetaryAmount amount, LocalDate timestamp) {
        throw new UnsupportedOperationException();
    }


    /**
     * Get the default provider chain. <p/>
     * <b>Hint: </b>This is not an API specific target. It is based on the current RI. Nevertheless we might
     * discuss, if standardizing this mechanism would make sense. Or if we even would add an additional method
     * on {@link javax.money.convert.ExchangeRateProvider} for evaluating this.
     *
     * @return the provider ID, the test will check, if the provider is correctly registered.
     * @see javax.money.convert.MonetaryConversions
     * @see javax.money.convert.ExchangeRateProvider
     */
    public List<String> getDefaultProviderChain() {
        throw new UnsupportedOperationException();
    }

    /**
     * Register your own custom {@link }ExchangeRateProvider} implementation and return the according provider ID.
     * The test will check, if the provider is visible and print out the according {@link javax.money.convert
     * .ProviderContext}.
     *
     * @return the provider ID, the test will check, if the provider is correctly registered.
     * @see javax.money.convert.MonetaryConversions
     * @see javax.money.convert.ExchangeRateProvider
     */
    public String getNewProviderName() {
        throw new UnsupportedOperationException();
    }
}

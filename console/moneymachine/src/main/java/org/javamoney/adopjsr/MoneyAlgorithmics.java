/*
 * Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch). Licensed under the Apache
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
import javax.money.MonetaryOperator;
import javax.money.MonetaryQuery;
import java.math.BigDecimal;
import java.util.List;

/**
 * Class to perform algorithmic calculations and some of the provided external functions on amounts.
 * Created by Anatole on 07.03.14.
 */
public class MoneyAlgorithmics {

    /**
     * Add up all {@link MonetaryAmount} instances.
     *
     * @param amounts the amounts (only in one single currency).
     * @return the sum of all amounts
     */
    public MonetaryAmount addAll(MonetaryAmount... amounts) {
        throw new UnsupportedOperationException();
    }

    /**
     * Multiply the {@link MonetaryAmount} with the given factor.
     *
     * @param amount the amount
     * @param factor the the factor
     * @return the multiplied amount
     */
    public MonetaryAmount multiply(MonetaryAmount amount, Number factor) {
        throw new UnsupportedOperationException();
    }

    /**
     * subtract two {@link MonetaryAmount}s.
     *
     * @param amount the amount
     * @param amt2   the amoun to be
     * @return
     */
    public MonetaryAmount subtract(MonetaryAmount amount, MonetaryAmount amt2) {
        throw new UnsupportedOperationException();
    }

    /**
     * Divide an {@link MonetaryAmount}.
     *
     * @param amount the amount
     * @param factor the factor
     * @return
     */
    public MonetaryAmount divide(MonetaryAmount amount, BigDecimal factor) {
        throw new UnsupportedOperationException();
    }

    /**
     * Scale an {@link MonetaryAmount}.
     *
     * @param amount the amount
     * @param scale  the scale factor
     * @return
     */
    public MonetaryAmount scaleByTen(MonetaryAmount amount, int scale) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sort the given {@link MonetaryAmount}s, by currency and number.
     *
     * @param amounts the amounts
     * @return
     */
    public List<MonetaryAmount> sortAmounts(MonetaryAmount... amounts) {
        throw new UnsupportedOperationException();
    }

    /**
     * Calculate the sum of only the given amounts, that are of the given currency.
     *
     * @param targetCurrency the target currency
     * @param amounts        the amounts to filter and add
     * @return the amount's total, in the given target currency.
     */
    public MonetaryAmount querySumOf(CurrencyUnit targetCurrency, MonetaryAmount... amounts) {
        throw new UnsupportedOperationException();
    }

    /**
     * Calculate the reciprocal of the given {@link MonetaryAmount} (1/amount).
     *
     * @param amount the amount, with the same currency.
     * @return the amount's reciprocal value.
     */
    public MonetaryAmount calculateReciprocal(MonetaryAmount amount) {
        throw new UnsupportedOperationException();
    }

    /**
     * Calulculate the given percentage of an {@link MonetaryAmount}.
     *
     * @param amt     the amount
     * @param percent the percentage, with the same currency.
     * @return the amount's percentage value.
     * @see org.javamoney.moneta.function.MonetaryFunctions
     */
    public MonetaryAmount calculatePercent(MonetaryAmount amt, double percent) {
        throw new UnsupportedOperationException();
    }

    /**
     * Calulculate the given permil of an {@link MonetaryAmount}.
     *
     * @param amt    the amount
     * @param permil the percentage, with the same currency.
     * @return the amount's permil value.
     * @see org.javamoney.moneta.function.MonetaryFunctions
     */
    public MonetaryAmount calculatePermil(MonetaryAmount amt, double permil) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get the major part only of the given {@link MonetaryAmount}.
     *
     * @param amt the amount
     * @return the major part of it, with the same currency.
     * @see org.javamoney.moneta.function.MonetaryFunctions
     * @see org.javamoney.moneta.function.MonetaryUtil
     */
    public MonetaryAmount getMajorPart(MonetaryAmount amt) {
        throw new UnsupportedOperationException();
    }

    /**
     * Calculate a compound interest, defined as {@code }base * (1+interest)^n}
     *
     * @param base the base amount
     * @param rate the interest rate
     * @param n    the periods
     * @return the compound interest.
     */
    public MonetaryAmount getCompoundInterest(MonetaryAmount base, double rate, int n) {
        throw new UnsupportedOperationException();
    }


    /**
     * Multiply amount with the given factor (advanced). <p/>
     * <b>Hint: </b>The factor may exceed the numeric capabilities of the
     * amount implementation given!
     *
     * @param amount the amount
     * @param factor the factor
     * @return the correct result
     * @see javax.money.MonetaryAmount#getContext()
     * @see javax.money.MonetaryContext
     * @see javax.money.MonetaryAmounts#getAmountFactory(Class) (javax.money.AmountFactoryQuery)
     */
    public MonetaryAmount multiplyAdvanced(MonetaryAmount amount, BigDecimal factor) {
        throw new UnsupportedOperationException();
    }

    /**
     * Subtract two amounts. <p/>
     * <b>Hint: </b>The valuation may exceed the numeric capabilities of the
     * amount implementation given!  You should handle this situation, e.g. by using a more appropriate amount
     * implementation.
     *
     * @param amount the base amount
     * @param amt2   the amount to be subtracted
     * @return the subtraction result
     * @see javax.money.MonetaryAmount#getContext()
     * @see javax.money.MonetaryContext
     * @see javax.money.MonetaryAmounts#getAmountFactory(Class) (javax.money.AmountFactoryQuery)
     */
    public MonetaryAmount subtractAdvanced(MonetaryAmount amount, MonetaryAmount amt2) {
        throw new UnsupportedOperationException();
    }

    /**
     * Divide an amount.<p/>
     * <b>Hint: </b>The valuation may exceed the numeric capabilities of the
     * amount implementation given! You should handle this situation, e.g. by using a more appropriate amount
     * implementation.
     *
     * @param amount the amount
     * @param factor the factor (divisor)
     * @return the division result
     * @see javax.money.MonetaryAmount#getContext()
     * @see javax.money.MonetaryContext
     * @see javax.money.MonetaryAmounts#getAmountFactory(Class) (javax.money.AmountFactoryQuery)
     */
    public MonetaryAmount divideAdvanced(MonetaryAmount amount, BigDecimal factor) {
        throw new UnsupportedOperationException();
    }


    /**
     * Implement a {@link javax.money.MonetaryOperator} that simply duplicates the amount given.
     *
     * @return the duplicating operator.
     */
    public MonetaryOperator getDuplicateOperator() {
        throw new UnsupportedOperationException();
    }

    /**
     * Implement a {@link javax.money.MonetaryOperator} that calculates the total of all amounts operated on.
     */
    public MonetaryOperator getTotalOperator() {
        throw new UnsupportedOperationException();
    }

    /**
     * Implement a {@link javax.money.MonetaryQuery} that return {@code true} for each amount, that has an ISO
     * currency (as available on {@link java.util.Currency}.
     */
    public MonetaryQuery<Boolean> getCountingQuery() {
        throw new UnsupportedOperationException();
    }

}

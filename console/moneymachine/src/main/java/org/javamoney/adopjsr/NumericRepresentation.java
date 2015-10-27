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
import java.util.Collection;
import java.util.Locale;

/**
 * This class has to be implemented and helps us giving feedback on the JSR 354's API. This part of the
 * project deals with extraction and modelling of the numeric part of a value.
 *
 * Created by Anatole on 07.03.14.
 */
public class NumericRepresentation{

    /**
     * Get a {@link java.math.BigDecimal} from the given amount.
     *
     * @param amount the {@link javax.money.MonetaryAmount}
     * @return the corresponding BigDecimal value
     * @see javax.money.MonetaryCurrencies
     */
    public BigDecimal getBigDecimal(MonetaryAmount amount){
        throw new UnsupportedOperationException();
    }

    /**
     * Get a long (truncated) from the given amount.
     *
     * @param amount the {@link javax.money.MonetaryAmount}
     * @return the corresponding BigDecimal value
     * @see javax.money.MonetaryCurrencies
     */
    public long getLongTruncated(MonetaryAmount amount){
        throw new UnsupportedOperationException();
    }

    /**
     * Get the current precision from the given amount.
     *
     * @param amount the {@link javax.money.MonetaryAmount}
     * @return the corresponding precision value
     */
    public int getPrecision(MonetaryAmount amount){
        throw new UnsupportedOperationException();
    }

    /**
     * Get the current scale from the given amount.
     *
     * @param amount the {@link javax.money.MonetaryAmount}
     * @return the corresponding scale value
     */
    public int getScale(MonetaryAmount amount){
        throw new UnsupportedOperationException();
    }

    /**
     * Get the current fraction denominator from the given amount.
     *
     * @param amount the {@link javax.money.MonetaryAmount}
     * @return the corresponding fraction denominator value
     */
    public long getFractionDenominator(MonetaryAmount amount){
        throw new UnsupportedOperationException();
    }

    /**
     * Get the current fraction denominator from the given amount.
     *
     * @param amount the {@link javax.money.MonetaryAmount}
     * @return the corresponding fraction denominator value
     */
    public long getFractionNumerator(MonetaryAmount amount){
        throw new UnsupportedOperationException();
    }

    /**
     * Get the current number representation type from the given amount.
     *
     * @param amount the {@link javax.money.MonetaryAmount}
     * @return the corresponding number representation type
     */
    public Class<?> getNumberType(MonetaryAmount amount){
        throw new UnsupportedOperationException();
    }

    /**
     * Get the current number from the given amount.
     *
     * @param amount the {@link javax.money.MonetaryAmount}
     * @return the corresponding (JDK) number value
     */
    public Number getNumber(MonetaryAmount amount){
        throw new UnsupportedOperationException();
    }


}

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

import javax.money.MonetaryAmount;
import javax.money.Monetary;
import java.util.Collection;

/**
 * Access roundings based on currency, as well as using customized rounding.
 *
 * @see javax.money.Monetary
 * Created by Anatole on 08.03.14.
 */
public class Rounding{

    /**
     * Round Amounts with the default rounding.
     *
     * @return the rounded amount
     * @see javax.money.Monetary
     */
    public MonetaryAmount roundWithDefaultRounding(MonetaryAmount amount){
        throw new UnsupportedOperationException();
    }


    /**
     * Round Amounts with a cash rounding.<p/>
     * <b>Hint: </b>Set the 'cashRounding' property to {@code true},
     * when accessing the rounding.
     *
     * @return the rounded amount
     * @see javax.money.Monetary
     * @see javax.money.RoundingContext
     */
    public MonetaryAmount roundForCash(MonetaryAmount amount){
        throw new UnsupportedOperationException();
    }

    /**
     * Round Amounts with a mathematical rounding:
     * <ul>
     * <li>Use {@link java.math.RoundingMode#HALF_UP}</li>
     * <li>Use a scale of 3</li>
     * </ul><p/>
     * <b>Hint: </b>Pass the according MathContext as a parameter.
     *
     * @return the rounded amount
     * @see javax.money.MonetaryContext
     */
    public MonetaryAmount roundMathematical(MonetaryAmount amount){
        throw new UnsupportedOperationException();
    }

    /**
     * Evaluate which roundings are currently available in the system.
     * @return the array of rounding ids currently known.
     */
    public Collection<String> getKnownRoundings(){
        throw new UnsupportedOperationException();
    }

    /**
     * Register your own custom rounding. The test will check, if your rounding is available and perform a test
     * rounding.
     *
     * @return the rounding name of your custom rounding.
     */
    public String getCustomRoundingName(){
        throw new UnsupportedOperationException();
    }
}

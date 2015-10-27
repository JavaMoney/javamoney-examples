/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
 */

package org.javamoney.adopjsr;

import org.javamoney.moneta.CurrencyUnitBuilder;
import org.javamoney.moneta.Money;
import org.junit.Test;

import javax.money.*;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Anatole on 21.03.14.
 */
public class RoundingTest{

    private static final CurrencyContext CURRENCY_CONTEXT = CurrencyContextBuilder.of("RoundingTest").build();

    private Rounding rnd = new Rounding();
    private Money[] moneys =
            new Money[]{org.javamoney.moneta.Money.of(200.12345678, "CHF"), Money.of(100.1234567, "JPY"),
                    Money.of(100.1234567, CurrencyUnitBuilder.of("API-TEST", CURRENCY_CONTEXT).setNumericCode(1234)
                                     .setDefaultFractionDigits(5).build()
                    )};

    @Test
    public void testRoundWithDefaultRounding() throws Exception{
        for(Money m : moneys){
            assertEquals(m.with(Monetary.getDefaultRounding()), rnd.roundWithDefaultRounding(m));
        }
    }


    @Test
    public void testRoundForCash() throws Exception{
        for(Money m : moneys){
            assertEquals(m.with(Monetary.getRounding(
                                 RoundingQueryBuilder.of().setCurrency(m.getCurrency()).set("cashRounding", true)
                                         .build())),
                         rnd.roundForCash(m));
        }
    }

    @Test
    public void testRoundMathematical() throws Exception{
        RoundingQuery ctx = RoundingQueryBuilder.of().set(RoundingMode.HALF_UP).set("maxScale", 3).build();
        for(Money m : moneys){
            assertEquals(rnd.roundMathematical(m), m.with(Monetary.getRounding(ctx)));
        }
    }

    @Test
    public void testKnownRoundings() throws Exception{
        Collection<String> roundings = rnd.getKnownRoundings();
        assertNotNull(roundings);
        Set<String> curSet = Monetary.getRoundingNames();
        for(String roundingId : curSet){
            assertTrue(roundings.contains(roundingId));
        }
        for(String roundingId : roundings){
            assertTrue(curSet.contains(roundingId));
        }
    }

    /**
     * Test your own custom rounding. The test will check, if your rounding is available and perform a test
     * rounding.
     */
    @Test
    public void testCustomRoundingName(){
        String rndId = rnd.getCustomRoundingName();
        MonetaryOperator rounding = Monetary.getRounding(rndId);
        assertNotNull(rounding);
        for(Money m : moneys){
            assertNotNull(m.with(rounding));
            assertEquals(m.getClass().getName(), m.with(rounding).getClass().getName());
        }
    }
}

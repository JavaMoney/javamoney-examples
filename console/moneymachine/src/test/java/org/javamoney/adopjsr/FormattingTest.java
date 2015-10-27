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

import org.javamoney.moneta.Money;
import org.junit.Test;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;
import javax.money.format.*;

import org.javamoney.moneta.format.CurrencyStyle;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test cases for the formatting API.
 * Created by Anatole on 21.03.14.
 */
public class FormattingTest {

    private Formatting formatting = new Formatting();

    @Test
    public void testGetAmountFormat() throws Exception {
        for (Locale loc : DecimalFormat.getAvailableLocales()) {
            MonetaryAmountFormat fmt = formatting.getAmountFormat(loc);
            assertEquals(fmt.getContext().getLocale(), loc);
        }
    }

    @Test
    public void testGetAmountFormat1() throws Exception {
        for (Locale loc : DecimalFormat.getAvailableLocales()) {
            MonetaryAmountFormat fmt = formatting.getAmountFormat(loc);
            assertEquals(fmt, MonetaryFormats.getAmountFormat(loc));
        }
    }

    @Test
    public void testCreateCustomFormat() throws Exception {
        MonetaryOperator outOp = (value) -> value.divide(1000000);
        MonetaryOperator inOp = new MonetaryOperator() {
            @Override
            public MonetaryAmount apply(MonetaryAmount value) {
                return value.multiply(1000000);
            }
        };
        MonetaryAmountFormat fmt = MonetaryFormats.getAmountFormat(
                AmountFormatQueryBuilder.of(Locale.ENGLISH).set(CurrencyStyle.SYMBOL)
                        .set("groupingSizes", new int[]{3, 2}).set("pattern", " ##0.00  ¤ Mio;[##0.00] ¤ Mio")
                        .set("displayConversion", outOp).set("parseConversion", inOp).build());
        Money m = Money.of(2323233223232424.23, "CHF");
        MonetaryAmountFormat toTest = formatting.createCustomFormat();
        assertEquals(fmt.format(m), toTest.format(m));
        m = Money.of(-2323233223232424.23, "CHF");
        assertEquals(fmt.format(m), toTest.format(m));
    }

    @Test
    public void testRegisterCustomFormat() {
        String formatId = formatting.getRegisteredCustomFormat();
        assertNotNull(formatId);
        AmountFormatQuery ctx = AmountFormatQueryBuilder.of(formatId).build();
        MonetaryAmountFormat f = MonetaryFormats.getAmountFormat(ctx);
        assertNotNull(f);
    }

    @Test
    public void testAvailableLocales() {
        Collection<Locale> locales = formatting.getAvailableLocales();
        assertEquals(MonetaryFormats.getAvailableLocales(), locales);
    }

}

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
import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryUtil;
import org.junit.Test;

import javax.money.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Anatole on 08.03.14.
 */
public class MoneyAlgorithmicsTest{

    private MoneyAlgorithmics alg = new MoneyAlgorithmics();
    private static final CurrencyContext CURENCY_CONTEXT = CurrencyContextBuilder.of("MoneyAlgorithmicsTest").build();

    @Test
    public void testAddAll() throws Exception{
        assertEquals(Money.of(20, "CHF"), alg.addAll(Money.of(10, "CHF"), Money.of(10, "CHF")));
        assertEquals(Money.of(20.8, "CHF"), alg.addAll(Money.of(10.5, "CHF"), Money.of(10.3, "CHF")));
        assertEquals(Money.of(-90, "CHF"), alg.addAll(Money.of(-100.3, "CHF"), Money.of(10.3, "CHF")));
        assertEquals(Money.of(0, "CHF"), alg.addAll(Money.of(0, "CHF"), Money.of(0, "CHF")));
        assertEquals(Money.of(283976.34, "CHF"), alg.addAll(Money.of(283976.34, "CHF"), Money.of(0, "CHF")));
    }

    @Test
    public void testMultiply() throws Exception{
        assertEquals(Money.of(100, "CHF"), alg.multiply(Money.of(10, "CHF"), 10));
        assertEquals(Money.of(101, "CHF"), alg.multiply(Money.of(10.1, "CHF"), 10));
        assertEquals(Money.of(21323120, "CHF"), alg.multiply(Money.of(2132312, "CHF"), 10));
        assertEquals(Money.of(100, "CHF"), alg.multiply(Money.of(-100, "CHF"), -1));
    }

    @Test
    public void testSubtract() throws Exception{
        assertEquals(Money.of(0, "CHF"), alg.subtract(Money.of(10, "CHF"), Money.of(10, "CHF")));
        assertEquals(Money.of(-110, "CHF"), alg.subtract(Money.of(10, "CHF"), Money.of(120, "CHF")));
        assertEquals(Money.of(-2.5, "CHF"), alg.subtract(Money.of(10, "CHF"), Money.of(12.5, "CHF")));
        assertEquals(Money.of(0.06, "CHF"), alg.subtract(Money.of(10.56, "CHF"), Money.of(10.5, "CHF")));
    }

    @Test
    public void testDivide() throws Exception{
        assertEquals(Money.of(1, "CHF"), alg.divide(Money.of(10, "CHF"), new BigDecimal(10)));
        assertEquals(Money.of(1, "CHF"), alg.divide(Money.of(10.345, "CHF"), new BigDecimal("10.345")));
        assertEquals(Money.of(10.345, "CHF"), alg.divide(Money.of(10.345, "CHF"), BigDecimal.ONE));
        assertEquals(Money.of(0.0837991089509923, "CHF"),
                     alg.divide(Money.of(10.345, "CHF"), new BigDecimal("123.45")));
    }

    @Test
    public void testScaleByTen() throws Exception{
        assertEquals(Money.of(100, "CHF"), alg.scaleByTen(Money.of(10, "CHF"), 1));
        assertEquals(Money.of(123234405.6, "CHF"), alg.scaleByTen(Money.of(123234.4056, "CHF"), 3));
    }

    @Test
    public void testSortAmounts() throws Exception{
        List<MonetaryAmount> amounts =
                alg.sortAmounts(Money.of(0, "CHF"), FastMoney.of(1, "CHF"), Money.of(-200, "CHF"),
                                FastMoney.of(210, "USD"));
        List<MonetaryAmount> sortedAmounts = new ArrayList<>();
        sortedAmounts.addAll(amounts);
        Collections.sort(sortedAmounts);
    }

    @Test
    public void testQuerySumOf() throws Exception{
        MonetaryAmount amt =
                alg.querySumOf(Monetary.getCurrency("CHF"), FastMoney.of(10, "CHF"), Money.of(0, "CHF"),
                               FastMoney.of(1, "USD"), Money.of(200.45, "CHF"), FastMoney.of(210, "USD"));
        assertTrue(Money.of(210.45, "CHF").isEqualTo(amt));
    }

    @Test
    public void testCalculateReciprocal() throws Exception{
        MonetaryAmount amt = alg.calculateReciprocal(Money.of(10, "CHF"));
        assertTrue(Money.of(0.1, "CHF").isEqualTo(amt));
    }

    @Test
    public void testCalculatePercent() throws Exception{
        MonetaryAmount amt = alg.calculatePercent(FastMoney.of(100, "EUR"), 20.5);
        assertTrue(Money.of(20.5, "EUR").isEqualTo(amt));
        amt = alg.calculatePercent(Money.of(10.45, "EUR"), 1.345);
        assertTrue(Money.of(0.1405525, "EUR").isEqualTo(amt));
    }

    @Test
    public void testCalculatePermil() throws Exception{
        MonetaryAmount amt = alg.calculatePermil(FastMoney.of(100, "EUR"), 20.5);
        assertTrue(Money.of(2.05, "EUR").isEqualTo(amt));
        amt = alg.calculatePermil(Money.of(10.45, "EUR"), 1.345);
        assertTrue(Money.of(0.01405525, "EUR").isEqualTo(amt));
    }

    @Test
    public void testGetMajorPart() throws Exception{
        MonetaryAmount amt1 = alg.getMajorPart(FastMoney.of(100.1223, "EUR"));
        assertEquals(amt1, FastMoney.of(100.1223, "EUR").with(MonetaryUtil.majorPart()));
    }

    @Test
    public void testGetCompoundInterest() throws Exception{
        MonetaryAmount amt1 = alg.getCompoundInterest(FastMoney.of(100, "EUR"), 10.5, 1);
        assertTrue(Money.of(110.5, "EUR").isEqualTo(amt1));
        MonetaryAmount amt2 = alg.getCompoundInterest(FastMoney.of(100, "EUR"), 10.5, 2);
        assertTrue(amt1.multiply(1.105).isEqualTo(amt2));
    }

    @Test
    public void testMultiplyAdvanced() throws Exception{
        MonetaryAmount amt1 = alg.multiplyAdvanced(FastMoney.of(100, "EUR"), BigDecimal.valueOf(Long.MAX_VALUE));
        assertTrue(Monetary.getDefaultAmountFactory().setNumber(100).setCurrency("EUR")
                           .setContext(MonetaryContextBuilder.of().set(MathContext.UNLIMITED).build()).create()
                           .multiply(BigDecimal.valueOf(Long.MAX_VALUE)).isEqualTo(amt1));
    }

    @Test
    public void testSubtractAdvanced() throws Exception{
        MonetaryAmount amt1 = alg.subtractAdvanced(FastMoney.of(100, "EUR"),
                                                   Money.of(new BigDecimal("0.0000000000000000000001"), "EUR"));
        assertTrue(Money.of(100, "EUR", MonetaryContextBuilder.of().set(MathContext.UNLIMITED).build())
                           .subtract(Money.of(new BigDecimal("0.0000000000000000000001"), "EUR")).isEqualTo(amt1));
    }

    @Test
    public void testDivideAdvanced() throws Exception{
        MonetaryAmount amt1 = alg.divideAdvanced(FastMoney.of(100, "EUR"), new BigDecimal("0.0000000000000000000001"));
        assertTrue(Money.of(100, "EUR", MonetaryContextBuilder.of().set(MathContext.UNLIMITED).build())
                           .divide(new BigDecimal("0.0000000000000000000001")).isEqualTo(amt1));
    }


    /**
     * Implement a {@link javax.money.MonetaryOperator} that simply duplicates the amount given.
     */
    @Test
    public void testDuplicateOperator(){
        MonetaryOperator op = alg.getDuplicateOperator();
        assertEquals(Money.of(20, "CHF"), Money.of(10, "CHF").with(op));
        assertEquals(Money.of(20.86, "CHF"), Money.of(10.43, "CHF").with(op));
        assertEquals(Money.of(-4, "CHF"), Money.of(-2, "CHF").with(op));
        assertEquals(Money.of(0, "CHF"), Money.of(0, "CHF").with(op));
    }

    /**
     * Implement a {@link javax.money.MonetaryOperator} that calculates the total of all amounts operated on.
     */
    @Test
    public void testTotalOperator(){
        MonetaryOperator op = alg.getTotalOperator();
        op.apply(Money.of(2, "CHF"));
        op.apply(FastMoney.of(4.5, "CHF"));
        op.apply(Money.of(10, "CHF"));
        op.apply(FastMoney.of(-1.5, "CHF"));
        assertEquals(Money.of(35.1234, "CHF"), op.apply(Money.of(20.1234, "CHF")));
    }

    /**
     * Implement a {@link javax.money.MonetaryQuery} that return {@code true} for each amount, that has an ISO
     * currency (as available on {@link java.util.Currency}.
     */
    @Test
    public void testCountingQuery(){
        MonetaryQuery<Boolean> query = alg.getCountingQuery();
        assertTrue(Money.of(2, "CHF").query(query));
        assertFalse(FastMoney.of(4.5, CurrencyUnitBuilder.of("GEEC", CURENCY_CONTEXT).build()).query(query));
        assertTrue(Money.of(10, "INR").query(query));
        assertFalse(FastMoney.of(-1.5, CurrencyUnitBuilder.of("2xx2", CURENCY_CONTEXT).build()).query(query));
    }
}

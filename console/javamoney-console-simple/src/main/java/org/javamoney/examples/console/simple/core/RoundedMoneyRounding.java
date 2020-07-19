package org.javamoney.examples.console.simple.core;

import java.math.BigDecimal;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import org.javamoney.moneta.RoundedMoney;

public final class RoundedMoneyRounding
{
    private RoundedMoneyRounding()
    {
    }


    public static void main(final String... args)
    {
        final CurrencyUnit usd = Monetary.getCurrency("USD");
        final RoundedMoney halfcent = RoundedMoney.of(new BigDecimal("0.005"), usd);
        final RoundedMoney zero = RoundedMoney.of(BigDecimal.ZERO, usd);

        System.out.append("A1. 0.005 + 0 = ").println(//
                                                      halfcent.add(zero) //
                                                                      .getNumber().numberValue(BigDecimal.class).toPlainString());

        System.out.append("A2. 0 + 0.005 = ").println(//
                                                      zero.add(halfcent) //
                                                                      .getNumber().numberValue(BigDecimal.class).toPlainString());

        System.out.println("----");

        System.out.append("B1: -0.005 = ").println(//
                                                   halfcent.negate() //
                                                                   .getNumber().numberValue(BigDecimal.class).toPlainString());

        System.out.append("B2: 0.005 * -1 = ").println(//
                                                       halfcent.multiply(new BigDecimal("-1")) //
                                                                       .getNumber().numberValue(BigDecimal.class).toPlainString());

        System.out.println("----");

        System.out.append("C1: 0.005 * 1 = ").println(//
                                                      halfcent.multiply(BigDecimal.ONE) //
                                                                      .getNumber().numberValue(BigDecimal.class).toPlainString());

        System.out.append("C2: 0.005 * 1.1 = ").println(//
                                                        halfcent.multiply(new BigDecimal("1.1")) //
                                                                        .getNumber().numberValue(BigDecimal.class).toPlainString());

        System.out.println("----");

        System.out.append("D1: 0.005 * 2 = ").println(//
                                                      halfcent.multiply(new BigDecimal("2")) //
                                                                      .getNumber().numberValue(BigDecimal.class).toPlainString());

        System.out.append("D2: (0.005 * 2) / 2 = ").println(//
                                                            halfcent.multiply(new BigDecimal("2")).divide(new BigDecimal("2")) //
                                                                            .getNumber().numberValue(BigDecimal.class).toPlainString());
    }
}
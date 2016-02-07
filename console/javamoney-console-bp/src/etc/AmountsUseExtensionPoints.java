package org.javamoney.examples.console.simple.core;

import org.javamoney.examples.console.simple.util.ConsoleUtils;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryFunctions;
import org.javamoney.moneta.function.MonetaryOperators;
import org.javamoney.moneta.function.MonetaryQueries;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.RoundingQueryBuilder;

import java.math.RoundingMode;

/**
 * Showcase the usage of extension point mechanism.
 */
public class AmountsUseExtensionPoints {

    public static void main(String... args){

        MonetaryAmount amt = Money.of(1234.56234, "CHF");
        ConsoleUtils.printDetails("Base", amt);
        ConsoleUtils.printDetails("10.5 %", amt.with(MonetaryOperators.percent(10.5)));
        ConsoleUtils.printDetails("10.5 o/oo", amt.with(MonetaryOperators.permil(10.5)));
        ConsoleUtils.printDetails("Major Part", amt.with(MonetaryOperators.majorPart()));
        ConsoleUtils.printDetails("Minor Part", amt.with(MonetaryOperators.minorPart()));
        ConsoleUtils.printDetails("1/Base (Reciprocal)", amt.with(MonetaryOperators.reciprocal()));

        System.out.println("Minor Part as long -> " + amt.query(MonetaryQueries.extractMinorPart()));
        System.out.println("Major Part as long -> " + amt.query(MonetaryQueries.extractMajorPart()));

        ConsoleUtils.printDetails("Rounded (default)", amt.with(Monetary.getDefaultRounding()));
        ConsoleUtils.printDetails("Rounded (DOWN, 1 fraction digit)", amt.with(Monetary.getRounding(
                RoundingQueryBuilder.of().set(RoundingMode.DOWN).setScale(1).build()
        )));
    }

}

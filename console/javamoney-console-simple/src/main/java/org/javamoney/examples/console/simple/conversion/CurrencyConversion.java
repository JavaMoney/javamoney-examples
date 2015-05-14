package org.javamoney.examples.console.simple.conversion;

import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import javax.money.convert.ConversionQueryBuilder;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import java.time.LocalDate;

/**
 * Showing accessing exchange rates and doing conversions.
 */
public class CurrencyConversion {

    public static void main(String... args){
        MonetaryAmount amt = Money.of(2000, "EUR");

        System.out.println("2000 EUR -(ECB)-> CHF = " + amt.with(MonetaryConversions.getConversion("CHF", "ECB")));
        System.out.println("2000 EUR -(IMF)-> CHF = " + amt.with(MonetaryConversions.getConversion("CHF", "IMF")));

        System.out.println("2000 EUR -(ECB, at 5th Jan 2008)-> CHF = " + amt.with(MonetaryConversions.getConversion(ConversionQueryBuilder.of().setTermCurrency("CHF")
                .set(LocalDate.of(2008, 01, 05)).build())));

        System.out.println("2000 EUR -(?)-> BRL = " + amt.with(MonetaryConversions.getConversion("BRL")));
    }

}

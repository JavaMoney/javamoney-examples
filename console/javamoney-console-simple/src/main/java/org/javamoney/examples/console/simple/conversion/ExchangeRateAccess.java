package org.javamoney.examples.console.simple.conversion;

import javax.money.convert.ConversionQueryBuilder;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import java.time.LocalDate;

/**
 * Showing accessing exchange rates and doing conversions.
 */
public class ExchangeRateAccess {

    public static void main(String... args){
        System.out.println("Default conversion chain -> " + MonetaryConversions.getDefaultConversionProviderChain());

        ExchangeRateProvider provider = MonetaryConversions.getExchangeRateProvider("IDENT");
        System.out.println("IDENT provider -> " + provider);
        provider = MonetaryConversions.getExchangeRateProvider("IDENT", "ECB");
        System.out.println("IDENT, ECB provider -> " + provider);

        System.out.println(provider.getExchangeRate("CHF", "EUR"));
        System.out.println(provider.getExchangeRate("CHF", "CHF"));

        provider = MonetaryConversions.getExchangeRateProvider("IDENT", "IMF");
        System.out.println(provider.getExchangeRate("TND", "BRL"));

        provider = MonetaryConversions.getExchangeRateProvider("IDENT", "ECB", "ECB-HIST");
        System.out.println("CHF -> EUR (today) -> " + provider.getExchangeRate(ConversionQueryBuilder.of()
                .setBaseCurrency("EUR").setTermCurrency("CHF")
                .set(LocalDate.of(2008, 1, 8)).build()));
        System.out.println("CHF -> EUR (1.8.2008) -> " + provider.getExchangeRate(ConversionQueryBuilder.of()
                .setBaseCurrency("EUR").setTermCurrency("CHF")
                .set(LocalDate.of(2008, 1, 8)).build()));
    }

}

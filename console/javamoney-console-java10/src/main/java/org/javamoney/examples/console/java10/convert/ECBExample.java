package org.javamoney.examples.console.java10.convert;

import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.ConversionQueryBuilder;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;
import java.math.BigDecimal;
import java.time.LocalDate;

import static javax.money.convert.MonetaryConversions.getExchangeRateProvider;

public class ECBExample {

    private static final CurrencyUnit EURO = Monetary
            .getCurrency("EUR");

    private static final CurrencyUnit DOLLAR = Monetary
            .getCurrency("USD");

    public static void main(String... args) {
        final var providerName = ((args.length > 0) ? args[0] : "ECB");

        final var provider = getExchangeRateProvider(providerName);
        final var conv1 = provider
                .getCurrencyConversion(EURO);
        System.out.println(conv1);
        final var money = Money.of(BigDecimal.TEN, DOLLAR);
        System.out.println(money);
        var result = conv1.apply(money);
        System.out.println(result);

        if (!"ECB".equals(providerName)) {
            final var conv2 = provider.getCurrencyConversion(ConversionQueryBuilder.of()
                    .setTermCurrency("EUR")
                    .set(LocalDate.of(2022, 12, 1))
                    .build());
            result = conv2.apply(money);
            System.out.println(result);
        }
    }
}

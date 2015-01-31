package org.javamoney.examples.console.functional;

import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MonetaryCurrencies;

public class CreateCurrencyUnit 
{
    public static void main( String[] args )
    {
        CurrencyUnit real = MonetaryCurrencies.getCurrency("BRL");
        CurrencyUnit dollar = MonetaryCurrencies.getCurrency(Locale.US);
        System.out.println(real);
        System.out.println(dollar);
    }
}

package org.javamoney.examples.console.functional;

import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

public class CreateCurrencyUnit 
{
    public static void main( String[] args )
    {
        CurrencyUnit real = Monetary.getCurrency("BRL");
        CurrencyUnit dollar = Monetary.getCurrency(Locale.US);
        System.out.println(real);
        System.out.println(dollar);
    }
}

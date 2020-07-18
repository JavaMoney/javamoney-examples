package org.javamoney.examples.console.java10.util;	

import javax.money.CurrencyUnit;	
import javax.money.MonetaryAmount;	

/**	
 * Created by Anatole on 14.05.2015.	
 */	
public final class ConsoleUtils {	

    private ConsoleUtils(){}	

    public static void printDetails(CurrencyUnit cu) {	
        if (cu == null) {	
            System.out.println("N/A");	
        } else {	
            System.out.println("CurrencyUnit '" + cu.toString() + "':\n" +	
                    "  Class                 : " + cu.getClass().getName() + "\n" +	
                    "  Currency Code         : " + cu.getCurrencyCode() + "\n" +	
                    "  Num.Code              : " + cu.getNumericCode() + "\n" +	
                    "  DefaultFraction Digits: " + cu.getDefaultFractionDigits() + "\n" +	
                    "  Context               : " + cu.getContext());	
        }	
    }	

    public static void printDetails(String title, CurrencyUnit cu) {	
        if (cu == null) {	
            System.out.println(title + " -> N/A");	
        } else {	
            System.out.println(title + " -> CurrencyUnit '" + cu.toString() + "':\n" +	
                    "  Class                 : " + cu.getClass().getName() + "\n" +	
                    "  Currency Code         : " + cu.getCurrencyCode() + "\n" +	
                    "  Num.Code              : " + cu.getNumericCode() + "\n" +	
                    "  DefaultFraction Digits: " + cu.getDefaultFractionDigits() + "\n" +	
                    "  Context               : " + cu.getContext());	
        }	
    }	

    public static void printDetails(MonetaryAmount am) {	
        if (am == null) {	
            System.out.println("N/A");	
        } else {	
            System.out.println("Amount '" + am.toString() + "':\n" +	
                    "  Class                 : " + am.getClass().getName() + "\n" +	
                    "  CurrencyUnit          : " + am.getCurrency().getCurrencyCode() + "\n" +	
                    "  Number                : " + am.getNumber() + "\n" +	
                    "  Context               : " + am.getContext());	
        }	
    }	

    public static void printDetails(String title, MonetaryAmount am) {	
        if (am == null) {	
            System.out.println(title + " -> N/A");	
        } else {	
            System.out.println(title + " -> Amount '" + am.toString() + "':\n" +	
                    "  Class                 : " + am.getClass().getName() + "\n" +	
                    "  CurrencyUnit          : " + am.getCurrency().getCurrencyCode() + "\n" +	
                    "  Number                : " + am.getNumber() + "\n" +	
                    "  Context               : " + am.getContext());	
        }	
    }	
}
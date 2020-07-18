package org.javamoney.examples.console.java10;	

import java.util.Locale;	

import javax.money.MonetaryAmount;	
import javax.money.format.MonetaryAmountFormat;	
import javax.money.format.MonetaryFormats;	

import org.javamoney.moneta.Money;	

/**	
 * Created by Werner on 11.10.2019.	
 */	
public class FormattingLocaleDemo {	

	public static void main(String[] args) {	
		final String COMPARISON = "EUR 123,456.78";	

		MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.ROOT);	

		MonetaryAmount source = Money.of(123456.78, "EUR");	
		String formatted = format.format(source);	

		System.out.println(formatted); // "EUR 123,456.78", space is a 32 on JDK 8, a 160 on JDK 9 and 10	
		System.out.println((int) formatted.toCharArray()[3]); // JDK 8: 32, JDK 9: 160, JDK 10: 160	

		MonetaryAmount result = format.parse(COMPARISON); // Space is char of 32 (standard space)	
		formatted = format.format(result);	
		System.out.println(formatted);	
		System.out.println(COMPARISON.equals(formatted));	
		System.out.println(result.toString());	
	}	

}
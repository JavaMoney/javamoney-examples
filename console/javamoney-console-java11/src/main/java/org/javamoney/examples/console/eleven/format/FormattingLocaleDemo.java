/*	
 * JavaMoney Examples	
 * Copyright 2012-2020, Werner Keil 	
 * and individual contributors by the @author tags.	
 *	
 * Licensed under the Apache License, Version 2.0 (the "License");	
 * you may not use this file except in compliance with the License.	
 * You may obtain a copy of the License at	
 * http://www.apache.org/licenses/LICENSE-2.0	
 * Unless required by applicable law or agreed to in writing, software	
 * distributed under the License is distributed on an "AS IS" BASIS,  	
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.	
 * See the License for the specific language governing permissions and	
 * limitations under the License.	
 */	
package org.javamoney.examples.console.eleven.format;

import java.util.Locale;	

import javax.money.format.MonetaryFormats;	

import org.javamoney.moneta.Money;	

/**	
 * Created by Werner on 11.10.2019.	
 */	
public class FormattingLocaleDemo {	

	public static void main(String[] args) {	
		final String COMPARISON = "EUR 123,456.78";	

		var format = MonetaryFormats.getAmountFormat(Locale.ROOT);	

		var source = Money.of(123456.78, "EUR");	
		var formatted = format.format(source);	

		System.out.println(formatted); // "EUR 123,456.78", space is a 32 on JDK 8, a 160 on JDK 9 and 10	
		System.out.println((int) formatted.toCharArray()[3]); // JDK 8: 32, JDK 9: 160, JDK 10: 160	

		var result = format.parse(COMPARISON); // Space is char of 32 (standard space)	
		formatted = format.format(result);	
		System.out.println(formatted);	
		System.out.println(COMPARISON.equals(formatted));	
		System.out.println(result.toString());	
	}	
}
/**
 * 
 */
package org.javamoney.examples.console;

import javax.money.Money;

import net.java.javamoney.ri.IntegralMoney;

/**
 * @author Werner Keil
 *
 */
public class IntegralDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Money amt1 = Money.of("USD", 10.1234556123456789);
		IntegralMoney amt2 = IntegralMoney.of("USD", 123456789);
		Money total = amt1.add(amt2);
		System.out.println(total);
	}

}

/*
 * JSR 354 JavaFX Binding Example
 */
package net.java.javamoney.examples.javafx;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Werner Keil
 *
 */
public class BillTest {

    Bill bill1 = new Bill();
    Bill bill2 = new Bill();
    Bill bill3 = new Bill();
	
	@Test
	public void testGetAmountDue() {
		assertEquals(0d, bill1.getAmountDue(), 0);
	}

	@Test
	public void testGetNewAmountDue() {
		assertEquals(10d, bill1.getNewAmountDue().getNumber().doubleValue(), 0);
	}

}

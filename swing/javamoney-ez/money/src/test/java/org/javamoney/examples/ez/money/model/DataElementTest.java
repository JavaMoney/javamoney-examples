package org.javamoney.examples.ez.money.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class DataElementTest {

	@Test
	public void testGetIdentifier() {
		DataElement temp = new DataElement("Id1");
		assertEquals("Id1", temp.getIdentifier());
	}

	@Test
	public void testToString() {
		DataElement temp = new DataElement("Id1");
		assertEquals("Id1", temp.toString());
	}

}

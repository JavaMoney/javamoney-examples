package org.javamoney.examples.ez.common.gui;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinkTest {

	@Test
	public void testSetLinkText() {
		Link link = new Link("A link");
		assertEquals("A link", link.getLinkText());
		assertEquals("<html><u>A link</u></html>", link.getText());
	}
}

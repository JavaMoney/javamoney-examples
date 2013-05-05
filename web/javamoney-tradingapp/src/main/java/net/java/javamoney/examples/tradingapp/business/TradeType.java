/*
 * JSR 354 Stock-Trading Example
 * Copyright 2005-2013, Werner Keil and individual contributors by the @author tag. 
 * See the copyright.txt in the distribution for a full listing of individual contributors.
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
package net.java.javamoney.examples.tradingapp.business;

/**
 * The type of trade, either BUY or SELL
 * @author Werner Keil
 * 
 */
public enum TradeType {

	BUY("B"), SELL("S");

	private final String value;

	private TradeType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static TradeType of(String s) {
		for (TradeType t : values()) {
			if (t.getValue().equals(s)) return t;
		}
		return null;
	}
}

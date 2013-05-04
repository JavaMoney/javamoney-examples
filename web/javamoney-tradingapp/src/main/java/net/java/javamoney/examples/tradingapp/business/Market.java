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

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jscience.economics.money.Currency;

import com.surveycom.sdj.Nameable;// TODO change to SDJ3

/**
 * This object represents a simplified stock market place.
 * 
 * @author Werner Keil
 * @version 0.9.1, 2013-05-04
 * 
 */
public class Market implements Nameable {
	private String name;
	private String symbol;
	private Currency currency;
	private Map<String, String> knownMarkets;
	private Map<String, Object> currencyExchangeRates; // Rates based on Default Currency!
	
	private final Log logger = LogFactory.getLog(getClass());
	
	public Market(String symbol, String name, Currency curr,
	  Map<String, String> markets, Map<String, Object> exchangeRates) {
		
		if (Currency.getReferenceCurrency() == null) {
			logger.info("Setting Reference Currency to " +
					String.valueOf(curr) + "...");
			curr.setExchangeRate(1);
			Currency.setReferenceCurrency(curr);
		} else {
			if (curr.equals(Currency.getReferenceCurrency())) {
				logger.info(String.valueOf(curr) + " is the Reference Currency.");
				curr.setExchangeRate(1);
			}
		}
		
		this.name = name;
		this.symbol = symbol;
		this.currency = curr;
		this.knownMarkets = markets;		
		this.currencyExchangeRates = exchangeRates;
	}
	
	/**
	 * @return the currency
	 */
	public Currency getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}
	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getSymbol();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((currency == null) ? 0 : currency.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		result = PRIME * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Market other = (Market) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}

	// these are helper methods dealing with Collections (therefore NOT part of  the Market identity)
	// TODO some actually belong to a Monetary System like defined in <type>MonetaryUnits</type>
	
	/**
	 * @return the knownMarkets
	 */
	public Map<String, String> getKnownMarkets() {
		return knownMarkets;
	}

	/**
	 * @return the currencyExchangeRates
	 */
	public Map<String, Object> getCurrencyExchangeRates() {
		return currencyExchangeRates;
	}

	public boolean isNamed() {
		return (name != null);
	}

}

/*
 * JSR 354 Trading App Example
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

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jscience.economics.money.Currency;
import org.jscience.economics.money.Money;
import org.jscience.physics.amount.Amount;

import com.surveycom.sdj.Characterizable;

public class Portfolio implements Characterizable {

	private Amount<Money> amount;
    //private float cash;
	private Currency currency;
    //maps symbol string to shares
    private Map<String, String> sharesPerSymbol;

    public Portfolio(double cashVal, LinkedHashMap<String, String> sharesPerSymbol,
    		 Currency curr) {
        this.sharesPerSymbol =  sharesPerSymbol;
        this.currency = curr;
        this.setCash(cashVal);
    }

    public double getCash(Currency inCurr) {
        return amount.doubleValue(inCurr);
    }
    
    public double getCash() {
        return getCash(this.currency);
    }

    public boolean contains(String symbol) {
        return sharesPerSymbol.containsKey(symbol);
    }

    public int getNumberOfShares(String symbol) {
        Object shares = sharesPerSymbol.get(symbol);

        if (shares instanceof String) {
            return Integer.parseInt((String) shares);
        } else if (shares instanceof Integer) {

            return ((Integer) shares).intValue();
        } else {
            throw new RuntimeException("Application error");
        }
    }

    public Iterator getSymbolIterator() {
        return sharesPerSymbol.keySet().iterator();
    }

    public void buyStock(String symbol, int sharesBought, Amount<Money> purchasePrice) {
        //cash -= sharesBought * purchasePrice;
    	amount = amount.minus(purchasePrice.times(sharesBought));
        if (sharesPerSymbol.containsKey(symbol)) {
            int currentShares = getNumberOfShares(symbol);
            sharesPerSymbol.put(symbol, new Integer(currentShares
                    + sharesBought).toString());
        } else {
            sharesPerSymbol.put(symbol, String.valueOf(sharesBought));
        }
    }

    public void sellStock(String symbol, int sharesSold, Amount<Money> sellPrice) {
        //cash += sharesSold * sellPrice;
    	amount = amount.plus(sellPrice.times(sharesSold));
        int currentShares = getNumberOfShares(symbol);
        int sharesLeft = currentShares - sharesSold;
        if (sharesLeft == 0) {
            sharesPerSymbol.remove(symbol);
        } else {
            sharesPerSymbol.put(symbol, String.valueOf((sharesLeft)));
        }
    }

    public boolean canBuy(int shares, Amount<Money> purchasePrice) {
    	if (purchasePrice != null) {
	        if ((purchasePrice.times(shares)).compareTo(amount) <= 0 ){
	            return true;
	        } else {
	            return false;
	        }
    	} else {
    		if (amount != null) {
    			return (amount.doubleValue(currency) > 0);
    		} else {
    			return false;
    		}
    	}
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
	 * @param cash the cash to set
	 */
	public void setCash(double cashVal) {
		setAmount(Amount.valueOf(cashVal, currency));
	}

	/**
	 * @return the amount
	 */
	public Amount<Money> getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Amount<Money> amount) {
		this.amount = amount;
	}
	
	public String getMoney() {
		return String.valueOf(amount);
	}
}
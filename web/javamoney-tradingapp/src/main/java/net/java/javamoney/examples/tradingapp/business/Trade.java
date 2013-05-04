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

import org.jscience.economics.money.Money;
import org.jscience.physics.amount.Amount;

import com.surveycom.sdj.Characterizable;

/**
 * @author Werner Keil
 *
 */
public class Trade implements Characterizable {

    //public static final boolean BUY = true;

    //public static final boolean SELL = false;

    //private boolean buySell;

    private TradeType type;
    
    private String symbol;

    private int shares;

    private Amount<Money> price;

    public boolean isBuySell() {
        return TradeType.BUY.equals(type);
    }

//    public void setBuySell(boolean buySell) {
//        this.buySell = buySell;
//    }

    public Amount<Money> getPrice() {
        return price;
    }

    public void setPrice(Amount<Money> price) {
        this.price = price;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int quantity) {
        this.shares = quantity;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setType(TradeType tradeType) {
    	this.type = tradeType;
    }
    
	public TradeType getType() {
		return type;
	}
}
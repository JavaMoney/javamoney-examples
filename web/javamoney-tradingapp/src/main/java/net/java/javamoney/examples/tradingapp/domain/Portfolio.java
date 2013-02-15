package net.java.javamoney.examples.tradingapp.domain;

import java.util.Iterator;
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
    private Map<String, Number> sharesPerSymbol;

    public Portfolio(double cashVal, Map<String, Number> sharesPerSymbol,
    		 Currency curr) {
        this.sharesPerSymbol = sharesPerSymbol;
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

    public Iterator<String> getSymbolIterator() {
        return sharesPerSymbol.keySet().iterator();
    }

    public void buyStock(String symbol, int sharesBought, Amount<Money> purchasePrice) {
        //cash -= sharesBought * purchasePrice;
    	amount = amount.minus(purchasePrice.times(sharesBought));
        if (sharesPerSymbol.containsKey(symbol)) {
            int currentShares = getNumberOfShares(symbol);
            sharesPerSymbol.put(symbol, new Integer(currentShares
                    + sharesBought));
        } else {
            sharesPerSymbol.put(symbol, new Integer(sharesBought));
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
            sharesPerSymbol.put(symbol, new Integer(sharesLeft));
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
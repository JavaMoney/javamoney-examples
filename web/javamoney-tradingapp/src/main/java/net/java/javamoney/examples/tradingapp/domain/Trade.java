package net.java.javamoney.examples.tradingapp.domain;

import org.jscience.economics.money.Money;
import org.jscience.physics.amount.Amount;

import com.surveycom.sdj.Characterizable;

public class Trade implements Characterizable {

    public static final boolean BUY = true;

    public static final boolean SELL = false;

    private boolean buySell;

    private String symbol;

    private int shares;

    private Amount<Money> price;

    public boolean isBuySell() {
        return buySell;
    }

    public void setBuySell(boolean buySell) {
        this.buySell = buySell;
    }

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
}
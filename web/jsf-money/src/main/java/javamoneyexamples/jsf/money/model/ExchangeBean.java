package javamoneyexamples.jsf.money.model;

import java.io.Serializable;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryCurrencies;

import org.javamoney.moneta.Money;

public class ExchangeBean implements Serializable {

	private static final long serialVersionUID = -4277622484280053995L;

	private Double value;
	
	private String coinFrom;
	
	private String coinTo;
	
	private MonetaryAmount result;
	

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getCoinFrom() {
		return coinFrom;
	}

	public void setCoinFrom(String coin) {
		this.coinFrom = coin;
	}

	public MonetaryAmount getResult() {
		return result;
	}

	public void setResult(MonetaryAmount result) {
		this.result = result;
	}
	
	

	public String getCoinTo() {
		return coinTo;
	}

	public void setCoinTo(String coinTo) {
		this.coinTo = coinTo;
	}

	public CurrencyUnit getCurrencyFrom() {
		return MonetaryCurrencies.getCurrency(coinFrom);
	}
	
	public CurrencyUnit getCurrencyTo() {
		return MonetaryCurrencies.getCurrency(coinTo);
	}

	public MonetaryAmount getMounetaryFrom() {
		return Money.of(value, getCurrencyFrom());
	}
	
}

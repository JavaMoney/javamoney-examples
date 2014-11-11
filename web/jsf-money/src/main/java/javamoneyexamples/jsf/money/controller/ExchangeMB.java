package javamoneyexamples.jsf.money.controller;

import java.util.List;

import javamoneyexamples.jsf.money.exchange.ExchangeType;
import javamoneyexamples.jsf.money.model.ExchangeBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;

@ViewScoped
@ManagedBean
public class ExchangeMB {

	@Inject
	private ExchangeBean bean;
	
	@Inject
	private ExchangeRateProvider provider;
	
	public List<String> getCoins() {
		return ExchangeType.getCoins();
	}
	
	public void exchange() {
		CurrencyConversion currencyConversion = provider.getCurrencyConversion(bean.getCurrencyTo());
		MonetaryAmount result = currencyConversion.apply(bean.getMounetaryFrom());
		bean.setResult(result);
	}

	public ExchangeBean getBean() {
		return bean;
	}

	public void setBean(ExchangeBean bean) {
		this.bean = bean;
	}
	
	
	
	
}

/*
 * JavaMoney Examples
 * Copyright 2012 Red Hat, Inc. and/or its affiliates,
 * and individual contributors by the @author tags. See the copyright.txt in the
 * distribution for a full listing of individual contributors
 *
 * Copyright 2012-2019, Werner Keil
 * and individual contributors by the @author tags.
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
package org.javamoney.examples.cdi.payment.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.Monetary;


//import org.javamoney.annotation.Amount;
import org.javamoney.examples.cdi.payment.events.PaymentEvent;
import org.javamoney.examples.cdi.payment.events.PaymentType;
import org.javamoney.examples.cdi.payment.qualifiers.Amount;
import org.javamoney.examples.cdi.payment.qualifiers.Credit;
import org.javamoney.examples.cdi.payment.qualifiers.Debit;
import org.javamoney.moneta.Money;

@Named
@SessionScoped
public class PaymentBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6634420857401223541L;

	@Inject
	private Logger log;

	// Events producers
	@Inject
	@Credit
	Event<PaymentEvent> creditEventProducer;

	@Inject
	@Debit
	Event<PaymentEvent> debitEventProducer;

	// @Inject
	// private MonetaryAmountFactory amountFactory;

	private static final CurrencyUnit CURRENCY = Monetary
			.getCurrency("EUR");

	@Amount
	private BigDecimal amount = new BigDecimal(10.0);

	@Amount
	private MonetaryAmount money = Money.of(amount, CURRENCY);

	public MonetaryAmount getMoney() {
		return money;
	}

	public void setMoney(MonetaryAmount money) {
		this.money = money;
	}

	private String paymentOption = PaymentType.DEBIT.toString();

	// Pay Action
	public String pay() {

		PaymentEvent currentEvtPayload = new PaymentEvent();
		currentEvtPayload.setType(PaymentType.of(paymentOption));
		// currentEvtPayload.setAmount(amount);
		currentEvtPayload.setMoney(money);
		currentEvtPayload.setDatetime(new Date());

		switch (currentEvtPayload.getType()) {
		case DEBIT:
			debitEventProducer.fire(currentEvtPayload);
			break;

		case CREDIT:
			creditEventProducer.fire(currentEvtPayload);
			break;

		default:
			log.severe("invalid payment option");
			break;
		}

		// paymentAction
		return "index";
	}

	// Reset Action
	public void reset() {
		amount = null;
		paymentOption = "";
	}

	public Event<PaymentEvent> getCreditEventLauncher() {
		return creditEventProducer;
	}

	public void setCreditEventLauncher(Event<PaymentEvent> creditEventLauncher) {
		this.creditEventProducer = creditEventLauncher;
	}

	public Event<PaymentEvent> getDebitEventLauncher() {
		return debitEventProducer;
	}

	public void setDebitEventLauncher(Event<PaymentEvent> debitEventLauncher) {
		this.debitEventProducer = debitEventLauncher;
	}

	public String getPaymentOption() {
		return paymentOption;
	}

	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
		this.money = Money.of(amount, CURRENCY);
	}

}

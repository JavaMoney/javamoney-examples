package org.javamoney.examples.jaxrs.bank;

import java.time.LocalDateTime;

import javax.money.MonetaryAmount;

public class HistoryBankAccount {

	private MonetaryAmount value;
	
	private LocalDateTime when;

	public MonetaryAmount getValue() {
		return value;
	}

	public void setValue(MonetaryAmount value) {
		this.value = value;
	}

	public LocalDateTime getWhen() {
		return when;
	}

	public void setWhen(LocalDateTime when) {
		this.when = when;
	}
	
}

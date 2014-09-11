package org.javamoney.examples.jaxrs.infrastructure.bank;

import java.time.LocalDateTime;

import org.javamoney.examples.jaxrs.bank.HistoryBankAccount;
import org.javamoney.moneta.Money;


class HistoryBankAccountJS {
	
	private String value;
	
	private String when;
	
	
	HistoryBankAccountJS(HistoryBankAccount account) {
		this.value = account.getValue().toString();
		this.when = account.getWhen().toString();
	}
	
	HistoryBankAccount to() {
		HistoryBankAccount account = new HistoryBankAccount();
		account.setValue(Money.parse(value));
		account.setWhen(LocalDateTime.parse(value));
		return account;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getWhen() {
		return when;
	}

	public void setWhen(String when) {
		this.when = when;
	}
	
}

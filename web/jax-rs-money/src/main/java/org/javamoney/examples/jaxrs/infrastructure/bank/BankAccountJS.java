package org.javamoney.examples.jaxrs.infrastructure.bank;

import java.time.LocalDateTime;

import org.javamoney.examples.jaxrs.model.BankAccount;
import org.javamoney.moneta.Money;

class BankAccountJS {
	
	private String user;

	private String value;

	private String begin;

	private String end;
	
	BankAccountJS(BankAccount account){
		this.user = account.getUser();
		value = account.getValue().toString();
		begin = account.getBegin().toString();
		end = account.getEnd().toString();
	}

	public BankAccount to() {
		BankAccount account = new BankAccount();
		account.setBegin(LocalDateTime.parse(begin));
		account.setEnd(LocalDateTime.parse(end));
		account.setUser(user);
		account.setValue(Money.parse(value));
		return account;
	}
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

}

package org.javamoney.examples.jaxrs.model;

import java.time.LocalDateTime;

import javax.money.MonetaryAmount;

public class BankAccount {

	private String user;
	
	private MonetaryAmount value;

	private LocalDateTime begin;
	
	private LocalDateTime end;
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public MonetaryAmount getValue() {
		return value;
	}

	public void setValue(MonetaryAmount value) {
		this.value = value;
	}

	public LocalDateTime getBegin() {
		return begin;
	}

	public void setBegin(LocalDateTime begin) {
		this.begin = begin;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}
	
}

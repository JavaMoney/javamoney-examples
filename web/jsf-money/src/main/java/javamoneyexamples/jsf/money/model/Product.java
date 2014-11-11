package javamoneyexamples.jsf.money.model;

import java.io.Serializable;

import javax.money.MonetaryAmount;

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private MonetaryAmount money;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MonetaryAmount getMoney() {
		return money;
	}

	public void setMoney(MonetaryAmount money) {
		this.money = money;
	}
	
	
	@Override
	public String toString() {
		return "name: " + name + " money: " + money;
	}

	public void clear() {
		name = "";
		money = null;
		
	}
	
}

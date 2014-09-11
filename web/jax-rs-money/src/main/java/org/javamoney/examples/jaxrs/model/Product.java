package org.javamoney.examples.jaxrs.model;

import java.io.Serializable;

import javax.money.MonetaryAmount;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Product implements Serializable {

	private static final long serialVersionUID = -6431548302068028659L;

	private String name;
	
	private MonetaryAmount price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public MonetaryAmount getPrice() {
		return price;
	}

	public void setPrice(MonetaryAmount preco) {
		this.price = preco;
	}

	public Product() {
		
	}

	public Product(String nome, MonetaryAmount preco) {
		this.name = nome;
		this.price = preco;
	}
	
	
}

package javamoneyexamples.jsf.money.controller;

import java.io.Serializable;

import javamoneyexamples.jsf.money.model.Product;
import javamoneyexamples.jsf.money.model.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;


@SessionScoped
@Named
public class ProductMB implements Serializable {

	private static final long serialVersionUID = -8303484031079724876L;

	@Inject
	private Product product;
	
	@Inject
	private User user;

	public Product getProduct() {
		return product;
	}

	public User getUser() {
		return user;
	}
	
	
	public void add(){
		user.addProduct(product);
		System.out.println("Ok");
	}
	
}

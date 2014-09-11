package org.javamoney.examples.jaxrs.resources.store;

import java.util.ArrayList;
import java.util.List;

import javax.money.CurrencyUnit;

import org.javamoney.examples.jaxrs.model.Product;
import org.javamoney.moneta.FastMoney;

public class Mock {
	
	public static  List<Product> hello(CurrencyUnit unit) {
		List<Product> products = new ArrayList<>();
		products.add(new Product("Banana", FastMoney.of(20, unit)));
		products.add(new Product("DVD", FastMoney.of(50, unit)));
		products.add(new Product("CD", FastMoney.of(5, unit)));
		products.add(new Product("Computer", FastMoney.of(999.9, unit)));
		products.add(new Product("Notebook", FastMoney.of(2500.69, unit)));
		products.add(new Product("Phone", FastMoney.of(800, unit)));
		products.add(new Product("Memory", FastMoney.of(189, unit)));
		products.add(new Product("Table", FastMoney.of(240, unit)));
		products.add(new Product("Blu-ray", FastMoney.of(60, unit)));
		products.add(new Product("Camera", FastMoney.of(560, unit)));
		return products;
	}
}

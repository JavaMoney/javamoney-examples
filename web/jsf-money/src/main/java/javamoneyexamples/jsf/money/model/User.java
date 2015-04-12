package javamoneyexamples.jsf.money.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.Monetary;

import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.function.MonetaryFunctions;

public class User implements Serializable {

	private static final CurrencyUnit CURRENCY = Monetary.getCurrency("USD");

	private static final long serialVersionUID = -4619662846488953817L;
	
	private List<Product> products = new ArrayList<>();

	public List<Product> getProducts() {
		return products;
	}

	public void addProduct(Product product) {
		Product copy = new Product();
		copy.setMoney(product.getMoney());
		copy.setName(product.getName());
		products.add(copy);
		product.clear();
	}
	
	public MonetaryAmount getSum() {
		return products.stream().map(Product::getMoney)
				.reduce(MonetaryFunctions.sum())
				.orElse(FastMoney.of(0, CURRENCY));
	}
	
	public MonetaryAmount getMin() {
		return products.stream().map(Product::getMoney)
				.reduce(MonetaryFunctions.min())
				.orElse(FastMoney.of(0, CURRENCY));
	}
	
	
	public MonetaryAmount getMax() {
		return products.stream().map(Product::getMoney)
				.reduce(MonetaryFunctions.max())
				.orElse(FastMoney.of(0, CURRENCY));
	}
	
	public int getCount() {
		return products.size();
	}
	
	public MonetaryAmount getAverage() {
		return products.stream().map(Product::getMoney)
				.collect(MonetaryFunctions.summarizingMonetary(CURRENCY))
				.getAverage();
	}

	@Override
	public String toString() {
		return products.toString();
	}
	
}

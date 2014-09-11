package org.javamoney.examples.jaxrs.ecommerce;

import java.util.List;

import javax.money.MonetaryAmount;

import org.javamoney.examples.jaxrs.model.Product;

/**
 * Base of actions in a commerce online.
 * @author otaviojava
 */
public interface Ecommerce {
	
	/**
	 * Show examples of products with the currency of the place.
	 * @return examples with currency
	 */
	List<Product> products();
	
	/**
	 * Do a buy, and returns the sum of the products. All products should be in the same currency of the store and cannot be null.
	 * Use the {@link Ecommerce#products()} to discover.
	 * @param products the products to buy
	 * @return the sum of the purchase 
	 */
	MonetaryAmount buy(List<Product> products);
	
	/**
	 * average of all products, All products should be in same currency and cannot be null.
	 * @param products 
	 * @return the average of all products
	 */
	MonetaryAmount average(List<Product> products);
	
	/**
	 * returns the cheaper price in a list of products
	 * @param products
	 * @return the cheapest one
	 */
	MonetaryAmount cheaper(List<Product> products);
	
	/**
	 * returns the expensive price in a list of products
	 * @param products
	 * @return the more expensive
	 */
	MonetaryAmount expensive(List<Product> products);
	
	/**
	 * details of purchase, sum, cheaper price, expensive price, average, number of products in the list.
	 * @param products
	 * @return the summary
	 */
	String summary(List<Product> products);
}

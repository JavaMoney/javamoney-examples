package org.javamoney.examples.jaxrs.ecommerce;

import java.util.List;

import javax.enterprise.inject.Vetoed;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

import org.javamoney.examples.jaxrs.model.Product;
import org.javamoney.examples.jaxrs.resources.store.Mock;
import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.function.MonetaryFunctions;
import org.javamoney.moneta.function.MonetarySummaryStatistics;

@Vetoed
class ECommerceImpl implements Ecommerce {
	
	private CurrencyUnit currency;
	
	private MonetaryAmount zero;
	
	public ECommerceImpl(CurrencyUnit currency) {
		this.currency = currency;
		zero = FastMoney.of(0, currency);
	}
	
	@Override
	public List<Product> products() {
		return Mock.hello(currency);
	}

	@Override
	public MonetaryAmount buy(List<Product> products) {
		return products.stream().map(Product::getPrice)
				.reduce(MonetaryFunctions.sum()).orElse(zero);
	}

	@Override
	public MonetaryAmount average(List<Product> products) {
		MonetarySummaryStatistics summary = products.stream().map(Product::getPrice)
		.collect(MonetaryFunctions.summarizingMonetary(currency));
		return summary.getAvarage();
	}

	@Override
	public MonetaryAmount cheaper(List<Product> products) {
		return products.stream().map(Product::getPrice)
				.reduce(MonetaryFunctions.min()).orElse(zero);
	}

	@Override
	public MonetaryAmount expensive(List<Product> products) {
		 return products.stream().map(Product::getPrice)
		.reduce(MonetaryFunctions.max()).orElse(zero);
	}

	@Override
	public String summary(List<Product> products) {
		MonetarySummaryStatistics summary = products.stream().map(Product::getPrice)
				.collect(MonetaryFunctions.summarizingMonetary(currency));
		
		StringBuilder summaryText = new StringBuilder();
		summaryText.append("Cheaper price: ").append(summary.getMin());
		summaryText.append("\nExpensive price: ").append(summary.getMax());
		summaryText.append("\nAvarage price: ").append(summary.getAvarage());
		summaryText.append("\nSum price: ").append(summary.getSum());
		summaryText.append("\nCount: ").append(summary.getCount());
		return summaryText.toString();	
	}
}

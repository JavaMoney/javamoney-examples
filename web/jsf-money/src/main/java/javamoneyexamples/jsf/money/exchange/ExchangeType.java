package javamoneyexamples.jsf.money.exchange;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ExchangeType {

	DOLLAR("USD"), BRAZILIAN_REAL("BRL"), EURO("EUR"), YEN("JPY");
	
	private String type;

	ExchangeType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public static List<String> getCoins() {
		return 	Stream.of(ExchangeType.values()).map(ExchangeType::getType).collect(Collectors.toList());
	}
	
}

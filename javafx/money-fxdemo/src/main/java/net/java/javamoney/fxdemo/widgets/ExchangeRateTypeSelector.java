package net.java.javamoney.fxdemo.widgets;

import java.util.Enumeration;

import javafx.scene.control.ChoiceBox;

import javax.money.convert.ExchangeRateType;
import javax.money.provider.Monetary;

public class ExchangeRateTypeSelector extends ChoiceBox<ExchangeRateType> {

	public ExchangeRateTypeSelector() {
		Enumeration<ExchangeRateType> en = Monetary.getConversionProvider()
				.getSupportedExchangeRateTypes();
		while (en.hasMoreElements()) {
			ExchangeRateType type = en.nextElement();
			getItems().add(type);
		}

	}

}

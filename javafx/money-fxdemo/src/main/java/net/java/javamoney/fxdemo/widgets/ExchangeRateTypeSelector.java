package net.java.javamoney.fxdemo.widgets;

import java.util.Collection;

import javafx.scene.control.ChoiceBox;

import javax.money.convert.ExchangeRateType;
import javax.money.convert.MonetaryConversion;

public class ExchangeRateTypeSelector extends ChoiceBox<ExchangeRateType> {

	public ExchangeRateTypeSelector() {
		Collection<ExchangeRateType> en = MonetaryConversion
				.getSupportedExchangeRateTypes();
		for (ExchangeRateType type : en) {
			getItems().add(type);
		}

	}

}

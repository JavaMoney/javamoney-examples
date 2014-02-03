package org.javamoney.examples.fxdemo.widgets;

import java.util.Collection;

import javax.money.convert.ExchangeRateType;
import javax.money.convert.MonetaryConversions;

import javafx.scene.control.ChoiceBox;

public class ExchangeRateTypeSelector extends ChoiceBox<ExchangeRateType> {

	public ExchangeRateTypeSelector() {
		final Collection<ExchangeRateType> types = MonetaryConversions
				.getSupportedExchangeRateTypes();
		for (ExchangeRateType type : types) {
			getItems().add(type);
		}

	}

}

package org.javamoney.examples.fxdemo.widgets;

import java.util.Collection;

import javafx.scene.control.ChoiceBox;

import org.javamoney.convert.ExchangeRateType;
import org.javamoney.convert.MonetaryConversions;

public class ExchangeRateTypeSelector extends ChoiceBox<ExchangeRateType> {

	public ExchangeRateTypeSelector() {
		final Collection<ExchangeRateType> types = MonetaryConversions
				.getSupportedExchangeRateTypes();
		for (ExchangeRateType type : types) {
			getItems().add(type);
		}

	}

}

package net.java.javamoney.fxdemo.widgets;

import java.util.Collection;

import javafx.scene.control.ChoiceBox;

import javax.money.convert.ExchangeRateType;
import javax.money.convert.MonetaryConversions;

public class ExchangeRateTypeSelector extends ChoiceBox<ExchangeRateType> {

	public ExchangeRateTypeSelector() {
		final Collection<ExchangeRateType> types = MonetaryConversions
				.getSupportedExchangeRateTypes();
		for (ExchangeRateType type : types) {
			getItems().add(type);
		}

	}

}

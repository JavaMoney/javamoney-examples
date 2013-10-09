package org.javamoney.examples.fxdemo.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javax.money.CurrencyUnit;

import org.javamoney.currencies.MonetaryCurrencies;
import org.javamoney.examples.fxdemo.AbstractFXMLComponent;
import org.javamoney.moneta.MoneyCurrency;

/**
 * @author Anatole Tresch
 * 
 */
public class CurrencySelector extends AbstractFXMLComponent {

	@FXML
	private ComboBox<String> codeBox;

	@FXML
	private Label currencyTitle;

	public CurrencySelector(String title) {
		super("/org/javamoney/examples/fxdemo/widgets/CurrencySelector.fxml");
		this.currencyTitle.setText(title);
	}

	public MoneyCurrency getCurrency() {
		String code = (String) codeBox.getSelectionModel().getSelectedItem();
		if (code != null) {
			return MoneyCurrency.of(code);
		}
		return null;
	}

	public void setCurrency(CurrencyUnit unit) {
		if (unit != null) {
			codeBox.getSelectionModel().select(unit.getCurrencyCode());
		} else {
			codeBox.getSelectionModel().clearSelection();
		}
	}

}

package org.javamoney.examples.fxdemo.widgets;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javax.money.CurrencySupplier;
import javax.money.CurrencyUnit;
import javax.money.MonetaryCurrencies;

import org.javamoney.examples.fxdemo.AbstractFXMLComponent;

import java.util.Collections;


/**
 * @author Anatole Tresch
 * @author Werner Keil
 */
public class CurrencySelector extends AbstractFXMLComponent implements CurrencySupplier {

	@FXML
	private ComboBox<String> codeBox;

	@FXML
	private Label currencyTitle;

	public CurrencySelector(String title) {
		super("/org/javamoney/examples/fxdemo/widgets/CurrencySelector.fxml");
		this.currencyTitle.setText(title);
        for(CurrencyUnit cu: MonetaryCurrencies.getCurrencies()){
            codeBox.getItems().add(cu.getCurrencyCode());
        }
        Collections.sort(codeBox.getItems());
        codeBox.getSelectionModel().select("CHF");
    }

	public CurrencyUnit getCurrency() {
		String code = codeBox.getSelectionModel().getSelectedItem();
		if (code != null) {
			return MonetaryCurrencies.getCurrency(code);
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

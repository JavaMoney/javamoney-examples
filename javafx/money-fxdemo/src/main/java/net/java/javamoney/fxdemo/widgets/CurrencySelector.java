package net.java.javamoney.fxdemo.widgets;

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
import javax.money.MoneyCurrency;
import javax.money.ext.MonetaryCurrencies;

import net.java.javamoney.fxdemo.AbstractFXMLComponent;
import net.java.javamoney.ri.convert.provider.BTCCurrency;

/**
 * @author Anatole Tresch
 * @author Werner Keil
 *
 */
public class CurrencySelector extends AbstractFXMLComponent {

	@FXML
	private ChoiceBox<String> namespaceBox;

	@FXML
	private ComboBox<String> codeBox;

	@FXML
	private Label currencyTitle;

	@SuppressWarnings("unchecked")
	public CurrencySelector(String title) {
		super("/net/java/javamoney/fxdemo/widgets/CurrencySelector.fxml");
		this.currencyTitle.setText(title);
		namespaceBox.getItems().addAll(MonetaryCurrencies.getNamespaces());
		namespaceBox.getItems().add(BTCCurrency.BTC_NAMESPACE);
		namespaceBox.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener() {

					public void changed(ObservableValue value, Object oldValue,
							Object newValue) {
						if (newValue != null) {
							final List<String> currencyCodes = new ArrayList<String>();
							if (MoneyCurrency.ISO_NAMESPACE.equals(newValue)) {
								for (CurrencyUnit unit : MonetaryCurrencies
										.getAll((String) newValue)) {
									String code = unit.getCurrencyCode();
									if (code != null && !code.trim().isEmpty()) {
										if (!currencyCodes.contains(code)) {
											currencyCodes.add(unit
													.getCurrencyCode());
										}
									}
								}
							}
							Collections.sort(currencyCodes);
							codeBox.getItems().setAll(currencyCodes);
						}
					}
				});
		namespaceBox.getSelectionModel().select(MoneyCurrency.ISO_NAMESPACE);
	}

	public CurrencyUnit getCurrency() {
		String namespace = (String) namespaceBox.getSelectionModel()
				.getSelectedItem();
		String code = (String) codeBox.getSelectionModel().getSelectedItem();
		if (namespace != null && code != null) {
			if (BTCCurrency.BTC_NAMESPACE.equals(namespace)) {
				return BTCCurrency.of();
			} else {
				return MonetaryCurrencies.get(namespace, code);
			}
		}
		return null;
	}

	public void setCurrency(CurrencyUnit unit) {
		if (unit != null) {
			namespaceBox.getSelectionModel().select(unit.getNamespace());
			if (!codeBox.getItems().contains(unit.getCurrencyCode())) {
				codeBox.getItems().add(unit.getCurrencyCode());
			}
			codeBox.getSelectionModel().select(unit.getCurrencyCode());
		} else {
			namespaceBox.getSelectionModel().clearSelection();
			codeBox.getSelectionModel().clearSelection();
		}
	}

}

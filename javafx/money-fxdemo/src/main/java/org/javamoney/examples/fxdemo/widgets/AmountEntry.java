package org.javamoney.examples.fxdemo.widgets;

import java.math.BigDecimal;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.money.MonetaryAmount;

import org.javamoney.examples.fxdemo.AbstractFXMLComponent;
import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.MoneyCurrency;

/**
 * @author Anatole Tresch
 * @author Werner Keil
 * 
 */
public class AmountEntry extends AbstractFXMLComponent {

	@FXML
	private ComboBox codeBox;

	@FXML
	private ChoiceBox numberType;

	@FXML
	private TextField numberValue;

	@FXML
	private Label amountTitle;

	public AmountEntry(String title) {
		super("AmountEntry.fxml");
		amountTitle.setText(title);
		numberType.getItems().add("BigDecimal");
		numberType.getItems().add("Long");
	}

	public MonetaryAmount getAmount() {
		String code = (String) codeBox.getSelectionModel().getSelectedItem();
		String typeClass = (String) numberType.getSelectionModel()
				.getSelectedItem();
		MoneyCurrency currency = MoneyCurrency.of(code);
		BigDecimal dec = new BigDecimal(numberValue.getText());
		if (typeClass != null) {
			if ("Long".equals(typeClass)) {
				return FastMoney.of(currency, dec);
			}
		}
		return Money.of(currency, dec);
	}

	public void setAmount(MonetaryAmount amount) {
		if (amount != null) {
			codeBox.getSelectionModel().select(
					amount.getCurrency().getCurrencyCode());
			if (FastMoney.class.equals(amount.getClass())) {
				numberType.getSelectionModel().select("Long");
			} else {
				numberType.getSelectionModel().select("BigDecimal");
			}
			numberValue.setText(Money.from(amount).asType(BigDecimal.class)
					.toString());
		} else {
			codeBox.getSelectionModel().clearSelection();
			numberType.getSelectionModel().clearSelection();
			numberValue.setText("0");
		}
	}

	public ComboBox getCodeBox() {
		return codeBox;
	}
}

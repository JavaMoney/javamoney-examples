package org.javamoney.examples.fxdemo.widgets;

import java.math.BigDecimal;
import java.util.Collections;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.Monetary;

import org.javamoney.examples.fxdemo.AbstractFXMLComponent;
import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;

/**
 * @author Anatole Tresch
 * @author Werner Keil
 * 
 */
public class AmountEntry extends AbstractFXMLComponent {

	@FXML
	private ComboBox<String> codeBox;

	@FXML
	private ChoiceBox<String> numberType;

	@FXML
	private TextField numberValue;

	@FXML
	private Label amountTitle;

	public AmountEntry(String title) {
		super("AmountEntry.fxml");
		amountTitle.setText(title);
		numberType.getItems().add("BigDecimal");
		numberType.getItems().add("Long");
        for(CurrencyUnit cu: Monetary.getCurrencies()){
            codeBox.getItems().add(cu.getCurrencyCode());
        }
        Collections.sort(codeBox.getItems());
        codeBox.getSelectionModel().select("CHF");
	}

	public MonetaryAmount getAmount() {
		String code = (String) codeBox.getSelectionModel().getSelectedItem();
		String typeClass = (String) numberType.getSelectionModel()
				.getSelectedItem();
		CurrencyUnit currency = Monetary.getCurrency(code);
		BigDecimal dec = new BigDecimal(numberValue.getText());
		if (typeClass != null) {
			if ("Long".equals(typeClass)) {
				return FastMoney.of(dec,currency);
			}
		}
		return Money.of(dec,currency);
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
			numberValue.setText(Money.from(amount).getNumber()
					.toString());
		} else {
			codeBox.getSelectionModel().clearSelection();
			numberType.getSelectionModel().clearSelection();
			numberValue.setText("0");
		}
	}

	public ComboBox<String> getCodeBox() {
		return codeBox;
	}
}

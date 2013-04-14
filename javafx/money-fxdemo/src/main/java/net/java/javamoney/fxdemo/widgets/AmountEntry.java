package net.java.javamoney.fxdemo.widgets;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.Money;
import javax.money.MoneyCurrency;
import javax.money.provider.Monetary;

import net.java.javamoney.fxdemo.AbstractFXMLComponent;
import net.java.javamoney.ri.core.IntegralMoney;

public class AmountEntry extends AbstractFXMLComponent {

	@FXML
	private ChoiceBox namespaceBox;

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
		namespaceBox.getItems().addAll(
				Monetary.getCurrencyUnitProvider().getNamespaces());
		numberType.getItems().add("BigDecimal");
		numberType.getItems().add("Long");
		namespaceBox.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener() {
					public void changed(ObservableValue value, Object oldValue,
							Object newValue) {
						List<String> currencies = new ArrayList<String>();
						for (CurrencyUnit unit : Monetary
								.getCurrencyUnitProvider().getAll(
										(String) newValue)) {
							currencies.add(unit.getCurrencyCode());
						}
						Collections.sort(currencies);
						codeBox.getItems().setAll(currencies);
					}
				});
		namespaceBox.getSelectionModel().select(MoneyCurrency.ISO_NAMESPACE);
	}

	public MonetaryAmount getAmount() {
		String namespace = (String) namespaceBox.getSelectionModel()
				.getSelectedItem();
		String code = (String) codeBox.getSelectionModel().getSelectedItem();
		String typeClass = (String)numberType.getSelectionModel()
				.getSelectedItem();
		CurrencyUnit currency = Monetary.getCurrencyUnitProvider().get(
				namespace, code);
		BigDecimal dec = new BigDecimal(numberValue.getText());
		if (typeClass != null) {
			if("Long".equals(typeClass)){
				return IntegralMoney.of(currency, dec);
			}
		}
		return Money.of(currency, dec);
	}

	public void setAmount(MonetaryAmount amount){
		if(amount!=null){
			namespaceBox.getSelectionModel().select(amount.getCurrency().getNamespace());
			codeBox.getSelectionModel().select(amount.getCurrency().getCurrencyCode());
			if(IntegralMoney.class.equals(amount.getClass())){
				numberType.getSelectionModel().select("Long");
			}
			else{
				numberType.getSelectionModel().select("BigDecimal");
			}
			numberValue.setText(amount.asType(BigDecimal.class).toString());
		}
		else{
			namespaceBox.getSelectionModel().clearSelection();
			codeBox.getSelectionModel().clearSelection();
			numberType.getSelectionModel().clearSelection();
			numberValue.setText("0");
		}
	}
}

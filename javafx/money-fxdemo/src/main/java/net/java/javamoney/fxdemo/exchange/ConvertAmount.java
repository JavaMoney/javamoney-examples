package net.java.javamoney.fxdemo.exchange;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConverter;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateType;
import javax.money.provider.Monetary;

import net.java.javamoney.fxdemo.widgets.AbstractExamplePane;
import net.java.javamoney.fxdemo.widgets.AbstractSingleSamplePane;
import net.java.javamoney.fxdemo.widgets.AmountEntry;
import net.java.javamoney.fxdemo.widgets.CurrencySelector;
import net.java.javamoney.fxdemo.widgets.ExchangeRateTypeSelector;

public class ConvertAmount extends AbstractExamplePane {

	public ConvertAmount() {
		super(new ExamplePane());
		setExampleTitle("Convert an Amount");
		setExampleDescription("This example shows how to convert a Monetary Amount into another currency.");
		setExampleCode(loadExample("/samples/CurrencyConversion.javatxt"));
	}

	public final static class ExamplePane extends AbstractSingleSamplePane {

		private VBox exPane = new VBox();
		private AmountEntry amountBox = new AmountEntry("Amount");
		private CurrencySelector currencySelector1 = new CurrencySelector("Target Currency");
		private ExchangeRateTypeSelector rateTypeSelector = new ExchangeRateTypeSelector();

		public ExamplePane() {
			exPane.getChildren().addAll(new Label("Rate Type"),
					rateTypeSelector,
					amountBox,
					currencySelector1);
			this.inputPane.getChildren().add(exPane);
			AnchorPane.setLeftAnchor(exPane, 10d);
			AnchorPane.setTopAnchor(exPane, 10d);
			Button actionButton = new Button("Create");
			actionButton
					.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
						public void handle(ActionEvent action) {
							StringWriter sw = new StringWriter();
							PrintWriter pw = new PrintWriter(sw);
							try {
								ExchangeRateType type = rateTypeSelector
										.getSelectionModel().getSelectedItem();
								CurrencyConverter prov = Monetary
										.getConversionProvider()
										.getCurrencyConverter(type);
								MonetaryAmount convertedAmount = prov.convert(amountBox.getAmount(), currencySelector1.getCurrency());
								pw.println("Converted Amount");
								pw.println("----------------");
								pw.println();
								printSummary(convertedAmount, pw);
							} catch (Exception e) {
								e.printStackTrace(pw);
							}
							pw.flush();
							ExamplePane.this.outputArea.setText(sw.toString());
						}

						private void printSummary(MonetaryAmount amount,
								PrintWriter pw) {
							pw.println("Class: " + amount.getClass().getName());
							pw.println("Value (BD): "
									+ amount.asType(BigDecimal.class));
							pw.println("Precision: " + amount.getPrecision());
							pw.println("Scale: " + amount.getScale());
						}
					});
			buttonPane.getChildren().add(actionButton);
		}
	}
}

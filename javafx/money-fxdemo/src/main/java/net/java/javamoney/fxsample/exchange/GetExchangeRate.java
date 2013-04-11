package net.java.javamoney.fxsample.exchange;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.ExchangeRateType;
import javax.money.provider.Monetary;

import net.java.javamoney.fxsample.widgets.AbstractExamplePane;
import net.java.javamoney.fxsample.widgets.AbstractSingleSamplePane;
import net.java.javamoney.fxsample.widgets.CurrencySelector;
import net.java.javamoney.fxsample.widgets.ExchangeRateTypeSelector;

public class GetExchangeRate extends AbstractExamplePane {

	public GetExchangeRate() {
		super(new ExamplePane());
		setExampleTitle("Access Exchange Rates");
		setExampleDescription("This example shows how to access exchange rates for different conversions.");
		setExampleCode(loadExample("/samples/ExchangeRates.javatxt"));
	}

	public final static class ExamplePane extends AbstractSingleSamplePane {

		private VBox exPane = new VBox();
		private CurrencySelector currencySelector1 = new CurrencySelector(
				"Source Currency");
		private CurrencySelector currencySelector2 = new CurrencySelector(
				"Target Currency");
		private ExchangeRateTypeSelector rateTypeSelector = new ExchangeRateTypeSelector();

		public ExamplePane() {
			exPane.getChildren().addAll(new Label("Rate Type"),
					rateTypeSelector, currencySelector1, currencySelector2);
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
								ExchangeRateProvider prov = Monetary
										.getConversionProvider()
										.getExchangeRateProvider(type);
								ExchangeRate rate = prov.getExchangeRate(
										currencySelector1.getCurrency(),
										currencySelector2.getCurrency());
								pw.println("Exchange Rate");
								pw.println("--------------");
								pw.println();
								if (rate == null) {
									pw.println("N/A (null).");
								} else {
									printSummary(rate, pw);
								}

							} catch (Exception e) {
								e.printStackTrace(pw);
							}
							pw.flush();
							ExamplePane.this.outputArea.setText(sw.toString());
						}

						private void printSummary(ExchangeRate rate,
								PrintWriter pw) {
							pw.println("Class: " + rate.getClass().getName());
							pw.println("Source Currency: " + rate.getBase());
							pw.println("Target Currency: " + rate.getTerm());
							pw.println("Factor: " + rate.getFactor());
							pw.println("Provider: " + rate.getProvider());
							pw.println("Derived: " + rate.isDerived());
							if (rate.isDerived()) {
								pw.println("Chain: "
										+ rate.getExchangeRateChain());
							}
						}
					});
			buttonPane.getChildren().add(actionButton);
		}
	}
}

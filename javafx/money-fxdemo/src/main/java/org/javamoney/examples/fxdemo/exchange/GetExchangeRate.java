package org.javamoney.examples.fxdemo.exchange;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javax.money.CurrencyUnit;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.RateType;
import javax.money.convert.MonetaryConversions;

import org.javamoney.convert.provider.EZBCurrentConversionProvider;
import org.javamoney.examples.fxdemo.widgets.AbstractExamplePane;
import org.javamoney.examples.fxdemo.widgets.AbstractSingleSamplePane;
import org.javamoney.examples.fxdemo.widgets.CurrencySelector;
import org.javamoney.examples.fxdemo.widgets.RateTypeSelector;

/**
 * @author Werner Keil
 * @author Anatole Tresch
 *
 */
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
				"Base Currency");
		private CurrencySelector currencySelector2 = new CurrencySelector(
				"Term Currency");
		private RateTypeSelector rateTypeSelector = new RateTypeSelector();

		public ExamplePane() {
			final Button swapButton = new Button("Swap");
			swapButton.setDisable(true);
			exPane.getChildren().addAll(new Label("Rate Type"),
					rateTypeSelector, currencySelector1, currencySelector2);
			this.inputPane.getChildren().add(exPane);
			rateTypeSelector.valueProperty().addListener(
					new ChangeListener<RateType>() {
						public void changed(
								ObservableValue<? extends RateType> observable,
								RateType oldERT, RateType newERT) {
							logger.info((observable !=null ? "Obs: " + observable : "")
									+ (oldERT !=null ? " Old ERT: " + oldERT : "")
									+ (newERT !=null ? " New ERT: " + newERT : ""));
							
							if (newERT != null) {
								if (EZBCurrentConversionProvider. .CONTEXT.equals(newERT)) {
									logger.debug("got ECB");
									currencySelector1.setCurrency(EZBCurrentConversionProvider.BASE_CURRENCY);
									currencySelector1.setDisable(true);
									swapButton.setDisable(true);
								} else {
									currencySelector1.setDisable(false);
									swapButton.setDisable(false);
								}
							}
						}

					});
			
			AnchorPane.setLeftAnchor(exPane, 10d);
			AnchorPane.setTopAnchor(exPane, 10d);
			Button actionButton = new Button("Create");
			actionButton
					.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
						public void handle(ActionEvent action) {
							final StringWriter sw = new StringWriter();
							final PrintWriter pw = new PrintWriter(sw);
							try {
								String type = rateTypeSelector
										.getSelectionModel().getSelectedItem();
								ExchangeRateProvider prov = MonetaryConversions.getExchangeRateProvider(type);
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
							pw.println("Base Currency: " + rate.getBase());
							pw.println("Term Currency: " + rate.getTerm());
							pw.println("Factor: " + rate.getFactor());
							pw.println("Provider: " + rate.getProvider());
							pw.println("Derived: " + rate.isDerived());
							if (rate.isDerived()) {
								pw.println("Chain: "
										+ rate.getExchangeRateChain());
							}
						}
					});
			
			swapButton
					.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
						public void handle(ActionEvent action) {
							logger.debug("Swapping...");
							final CurrencyUnit tmpCurrency = currencySelector1.getCurrency();
							currencySelector1.setCurrency(currencySelector2.getCurrency());
							currencySelector2.setCurrency(tmpCurrency);
						}
					});
			
			buttonPane.getChildren().addAll(actionButton, swapButton);
		}
	}
}

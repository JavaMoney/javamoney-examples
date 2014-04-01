package org.javamoney.examples.fxdemo.exchange;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javax.money.CurrencyUnit;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

import org.javamoney.examples.fxdemo.widgets.AbstractExamplePane;
import org.javamoney.examples.fxdemo.widgets.AbstractSingleSamplePane;
import org.javamoney.examples.fxdemo.widgets.CurrencySelector;

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
        private TextField rateProvider = new TextField();

		public ExamplePane() {
			final Button swapButton = new Button("Swap");
			swapButton.setDisable(true);
            exPane.getChildren().addAll(currencySelector1, currencySelector2, new Label("Rate Provider(s)"), rateProvider);
			this.inputPane.getChildren().add(exPane);
			AnchorPane.setLeftAnchor(exPane, 10d);
			AnchorPane.setTopAnchor(exPane, 10d);
			Button actionButton = new Button("Create");
			actionButton
					.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
						public void handle(ActionEvent action) {
							final StringWriter sw = new StringWriter();
							final PrintWriter pw = new PrintWriter(sw);
							try {
                                ExchangeRateProvider prov = null;
                                String rp = rateProvider.getText();
                                if(rp==null || rp.trim().isEmpty()){
								    prov = MonetaryConversions
										.getExchangeRateProvider();
                                }
                                else{
                                    String[] rps = rp.split(",");
                                    prov = MonetaryConversions
                                            .getExchangeRateProvider(rps);
                                }
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
							pw.println("Context: " + rate.getConversionContext());
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

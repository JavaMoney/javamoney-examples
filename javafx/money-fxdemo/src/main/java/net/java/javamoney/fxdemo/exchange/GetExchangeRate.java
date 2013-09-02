package net.java.javamoney.fxdemo.exchange;

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
import javax.money.convert.ConversionProvider;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateType;
import javax.money.convert.MonetaryConversions;

import net.java.javamoney.fxdemo.widgets.AbstractExamplePane;
import net.java.javamoney.fxdemo.widgets.AbstractSingleSamplePane;
import net.java.javamoney.fxdemo.widgets.CurrencySelector;
import net.java.javamoney.fxdemo.widgets.ExchangeRateTypeSelector;
import net.java.javamoney.ri.convert.provider.BTCCurrency;
import net.java.javamoney.ri.convert.provider.EZBConversionProvider;
import net.java.javamoney.ri.convert.provider.MtGoxV2ConversionProvider;

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
		private ExchangeRateTypeSelector rateTypeSelector = new ExchangeRateTypeSelector();

		public ExamplePane() {
			final Button swapButton = new Button("Swap");
			swapButton.setDisable(true);
			exPane.getChildren().addAll(new Label("Rate Type"),
					rateTypeSelector, currencySelector1, currencySelector2);
			this.inputPane.getChildren().add(exPane);
			rateTypeSelector.valueProperty().addListener(
					new ChangeListener<ExchangeRateType>() {
						public void changed(
								ObservableValue<? extends ExchangeRateType> observable,
								ExchangeRateType oldERT, ExchangeRateType newERT) {
							logger.info((observable !=null ? "Obs: " + observable : "")
									+ (oldERT !=null ? " Old ERT: " + oldERT : "")
									+ (newERT !=null ? " New ERT: " + newERT : ""));
							
							if (newERT != null) {
								if (EZBConversionProvider.RATE_TYPE.equals(newERT)) {
									logger.debug("got ECB");
									currencySelector1.setCurrency(EZBConversionProvider.BASE_CURRENCY);
									currencySelector1.setDisable(true);
									currencySelector2.setDisable(false);
									swapButton.setDisable(true);
								} else if (MtGoxV2ConversionProvider.RATE_TYPE.equals(newERT)) {
									logger.debug("got BTC");
									currencySelector1.setDisable(false);
									
									currencySelector2.setCurrency(BTCCurrency.of());
									currencySelector2.setDisable(true);
									swapButton.setDisable(true);
								} else {
									currencySelector1.setDisable(false);
									currencySelector2.setDisable(false);
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
								ExchangeRateType type = rateTypeSelector
										.getSelectionModel().getSelectedItem();
								ConversionProvider prov;
								if (MtGoxV2ConversionProvider.RATE_TYPE.equals(type)) {
									prov = new MtGoxV2ConversionProvider();
								} else {
									prov = MonetaryConversions
										.getConversionProvider(type);
								}
								final CurrencyUnit curr1 = currencySelector1.getCurrency();
								final CurrencyUnit curr2 = currencySelector2.getCurrency();
								ExchangeRate rate = prov.getExchangeRate(curr1,curr2);
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

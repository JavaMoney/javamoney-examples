package net.java.javamoney.fxdemo.exchange;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConverter;
import javax.money.convert.ExchangeRateType;
import javax.money.convert.MonetaryConversions;

import net.java.javamoney.fxdemo.widgets.AbstractExamplePane;
import net.java.javamoney.fxdemo.widgets.AbstractSingleSamplePane;
import net.java.javamoney.fxdemo.widgets.AmountEntry;
import net.java.javamoney.fxdemo.widgets.CurrencySelector;
import net.java.javamoney.fxdemo.widgets.ExchangeRateTypeSelector;
import net.java.javamoney.ri.convert.provider.EZBCurrentConversionProvider;

/**
 * @author Anatole Tresch
 * @author Werner Keil
 *
 */
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
		private CurrencySelector currencySelector1 = new CurrencySelector(
				"Term Currency");
		private ExchangeRateTypeSelector rateTypeSelector = new ExchangeRateTypeSelector();

		public ExamplePane() {
			exPane.getChildren().addAll(new Label("Rate Type"),
					rateTypeSelector, amountBox, currencySelector1);
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
								if (EZBCurrentConversionProvider.RATE_TYPE.equals(newERT)) {
									logger.debug("got ECB");
									amountBox.getCodeBox().setValue(EZBCurrentConversionProvider.BASE_CURRENCY.getCurrencyCode());
									amountBox.getCodeBox().setDisable(true);
								} else {
									amountBox.getCodeBox().setDisable(false);
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
								CurrencyConverter prov = MonetaryConversions
										.getConversionProvider(type)
										.getConverter();
								MonetaryAmount convertedAmount = prov.convert(
										amountBox.getAmount(),
										currencySelector1.getCurrency());
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
							pw.println("Currency: " + amount.getCurrency());
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

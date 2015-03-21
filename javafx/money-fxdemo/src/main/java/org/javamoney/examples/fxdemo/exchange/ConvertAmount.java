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

import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ProviderContext;
import javax.money.convert.RateType;
import javax.money.convert.MonetaryConversions;

import org.javamoney.examples.fxdemo.widgets.AbstractExamplePane;
import org.javamoney.examples.fxdemo.widgets.AbstractSingleSamplePane;
import org.javamoney.examples.fxdemo.widgets.AmountEntry;
import org.javamoney.examples.fxdemo.widgets.CurrencySelector;
import org.javamoney.examples.fxdemo.widgets.RateTypeSelector;

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
		public ExamplePane() {
            exPane.getChildren().addAll(amountBox, currencySelector1);
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
                                CurrencyConversion conv = MonetaryConversions.getConversion(currencySelector1.getCurrency());
								MonetaryAmount convertedAmount = amountBox.getAmount().with(conv);
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
							pw.println("Vaöue: " + amount.toString());
						}
					});
			buttonPane.getChildren().add(actionButton);
		}
	}
}

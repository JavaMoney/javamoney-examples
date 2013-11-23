package org.javamoney.examples.fxdemo.format;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javax.money.MonetaryAmount;

import org.javamoney.examples.fxdemo.widgets.AbstractExamplePane;
import org.javamoney.examples.fxdemo.widgets.AbstractSingleSamplePane;
import org.javamoney.examples.fxdemo.widgets.AmountEntry;
import org.javamoney.format.ItemFormat;
import org.javamoney.format.LocalizationStyle;
import org.javamoney.format.MonetaryFormats;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.CurrencyStyle;


public class FormatAmount extends AbstractExamplePane {

	public FormatAmount() {
		super(new ExamplePane());
		setExampleTitle("Format Amounts");
		setExampleDescription("This example shows how to formats monmetary amounts. Also additional custom properties are used to configure formatting on the fly.");
		setExampleCode(loadExample("/samples/FormattingAnAmount.javatxt"));
	}

	public final static class ExamplePane extends AbstractSingleSamplePane {

		private VBox exPane = new VBox();
		private AmountEntry amount1 = new AmountEntry("Amount Formatted");
		private TextField groupSizes = new TextField("4");
		private ChoiceBox<CurrencyStyle> currencyPlacement = new ChoiceBox<CurrencyStyle>();

		public ExamplePane() {
			exPane.getChildren().addAll(amount1, new Label("groupSizes"),
					groupSizes, new Label("currencyPlacement"),
					currencyPlacement);
			currencyPlacement.getItems().addAll(CurrencyStyle.values());
			this.inputPane.getChildren().add(exPane);
			AnchorPane.setLeftAnchor(exPane, 10d);
			AnchorPane.setTopAnchor(exPane, 10d);
			Button actionButton = new Button("Create and Format Amount");
			actionButton
					.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
						public void handle(ActionEvent action) {
							StringWriter sw = new StringWriter();
							PrintWriter pw = new PrintWriter(sw);
							try {
								MonetaryAmount amount = amount1.getAmount();
								LocalizationStyle.Builder styleBuilder = new LocalizationStyle.Builder(
										MonetaryAmount.class);
								if (groupSizes.getText() != null) {
									String[] groups = groupSizes.getText()
											.split(",");
									int[] groupsInt = new int[groups.length];
									for (int i = 0; i < groupsInt.length; i++) {
										groupsInt[i] = Integer
												.parseInt(groups[i]);
									}
//									styleBuilder.setAttribute("groups",
//											groupsInt);
								}
								CurrencyStyle placement = currencyPlacement
										.getSelectionModel().getSelectedItem();
								if (placement != null) {
//									styleBuilder.setAttribute(
//											"currencyPlacement", placement);
								}
								ItemFormat<MonetaryAmount> formatter = MonetaryFormats
										.getItemFormat(MonetaryAmount.class,
												styleBuilder.build());
								pw.println("Formatted Amount");
								pw.println("----------------");
								if (formatter != null) {
									pw.println(formatter.format(amount, Locale.getDefault()));
								} else {
									pw.println("N/A: No formatter available.");
								}
							} catch (Exception e) {
								e.printStackTrace(pw);
							}
							pw.flush();
							ExamplePane.this.outputArea.setText(sw.toString());
						}

					});
			Button fillButton = new Button("Fill INR Example");
			fillButton
					.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
						public void handle(ActionEvent action) {
							MonetaryAmount amount = Money.of("INR",
									12345678901234567890.123d);
							amount1.setAmount(amount);
							groupSizes.setText("3,2");
						}
					});
			buttonPane.getChildren().addAll(actionButton, fillButton);
		}
	}
}

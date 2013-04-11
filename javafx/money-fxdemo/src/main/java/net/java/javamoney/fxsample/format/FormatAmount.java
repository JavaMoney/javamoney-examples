package net.java.javamoney.fxsample.format;

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
import javax.money.Money;
import javax.money.format.ItemFormatter;
import javax.money.format.LocalizationStyle;
import javax.money.provider.Monetary;

import net.java.javamoney.fxsample.widgets.AbstractExamplePane;
import net.java.javamoney.fxsample.widgets.AbstractSingleSamplePane;
import net.java.javamoney.fxsample.widgets.AmountEntry;
import net.java.javamoney.ri.format.provider.format.IsoAmountFormatter.CurrencyPlacement;

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
		private ChoiceBox<CurrencyPlacement> currencyPlacement = new ChoiceBox<CurrencyPlacement>();

		public ExamplePane() {
			exPane.getChildren().addAll(amount1, new Label("groupSizes"),
					groupSizes, new Label("currencyPlacement"),
					currencyPlacement);
			currencyPlacement.getItems().addAll(CurrencyPlacement.values());
			this.inputPane.getChildren().add(exPane);
			AnchorPane.setLeftAnchor(exPane, 10d);
			AnchorPane.setTopAnchor(exPane, 10d);
			Button actionButton = new Button("Create and Format Amount");
			actionButton
					.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
						public void handle(ActionEvent action) {
							StringWriter sw = new StringWriter();
							PrintWriter pw = new PrintWriter(sw);
							StringBuilder builder = new StringBuilder();
							try {
								MonetaryAmount amount = amount1.getAmount();
								LocalizationStyle.Builder styleBuilder = new LocalizationStyle.Builder(
										"default", Locale.ENGLISH);
								if (groupSizes.getText() != null) {
									String[] groups = groupSizes.getText()
											.split(",");
									int[] groupsInt = new int[groups.length];
									for (int i = 0; i < groupsInt.length; i++) {
										groupsInt[i] = Integer
												.parseInt(groups[i]);
									}
									styleBuilder.setAttribute("groups", groupsInt);
								}
								CurrencyPlacement placement = currencyPlacement
										.getSelectionModel().getSelectedItem();
								if (placement != null) {
									styleBuilder.setAttribute("currencyPlacement",
											placement);
								}
								ItemFormatter<MonetaryAmount> formatter = Monetary
										.getItemFormatterFactory()
										.getItemFormatter(MonetaryAmount.class,
												styleBuilder.build());
								pw.println("Formatted Amount");
								pw.println("----------------");
								if (formatter != null) {
									pw.println(formatter.format(amount));
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

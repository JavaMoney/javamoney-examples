package net.java.javamoney.fxsample.core;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.money.MonetaryAmount;

import net.java.javamoney.fxsample.widgets.AbstractExamplePane;
import net.java.javamoney.fxsample.widgets.AbstractSingleSamplePane;
import net.java.javamoney.fxsample.widgets.AmountEntry;

public class CalculateAmounts extends AbstractExamplePane {

	public CalculateAmounts() {
		super(new ExamplePane());
		setExampleTitle("Monetary Amounts Arithmethics");
		setExampleDescription("This example shows how to perform arithemtic operations on monmetary amounts.");
		setExampleCode(loadExample("/samples/AmountArithmetics.javatxt"));
	}

	public final static class ExamplePane extends AbstractSingleSamplePane {

		private HBox exPane = new HBox();
		private AmountEntry amount1Pane = new AmountEntry("First Amount");
		private AmountEntry amount2Pane = new AmountEntry("Second Amount");
		private Label operationLabel = new Label("Operation to be performed:");
		private ChoiceBox operationChoice = new ChoiceBox();
		private CheckBox addResultToAmount1 = new CheckBox(
				"Set result to Amount 1.");

		public ExamplePane() {
			exPane.getChildren().add(amount1Pane);
			exPane.getChildren().add(amount2Pane);
			VBox amountBox = new VBox();
			amountBox.getChildren().addAll(amount1Pane,
					amount2Pane);
			VBox opBox = new VBox();
			opBox.getChildren().addAll(new Label("Operation"), operationChoice,
					addResultToAmount1);
			exPane.getChildren().addAll(amountBox, opBox);
			this.inputPane.getChildren().add(exPane);
			AnchorPane.setLeftAnchor(inputPane, 10d);
			AnchorPane.setTopAnchor(inputPane, 10d);
			operationChoice.getItems().addAll("add", "subtract", "multiply",
					"divide", "divideToIntegralValue", "max", "min",
					"remainder");
			Button actionButton = new Button("Perform");
			actionButton
					.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent action) {
							StringWriter sw = new StringWriter();
							PrintWriter pw = new PrintWriter(sw);
							StringBuilder builder = new StringBuilder();
							try {
								MonetaryAmount amount1 = amount1Pane
										.getAmount();
								MonetaryAmount amount2 = amount2Pane
										.getAmount();
								MonetaryAmount amountResult = performOperation(
										amount1, amount2,
										(String) operationChoice
												.getSelectionModel()
												.getSelectedItem());
								if (addResultToAmount1.isSelected()) {
									amount1Pane.setAmount(amountResult);
								}
								pw.println("MonetaryAmount");
								pw.println("--------------");
								pw.println();
								printSummary(amountResult, pw);
							} catch (Exception e) {
								e.printStackTrace(pw);
							}
							pw.flush();
							ExamplePane.this.outputArea.setText(sw.toString());
						}

						private MonetaryAmount performOperation(
								MonetaryAmount amount1, MonetaryAmount amount2,
								String operation) {
							if ("add".equals(operation)) {
								return amount1.add(amount2);
							} else if ("subtract".equals(operation)) {
								return amount1.subtract(amount2);
							} else if ("multiply".equals(operation)) {
								return amount1.multiply(amount2);
							} else if ("divide".equals(operation)) {
								return amount1.divide(amount2);
							} else if ("divideToIntegralValue"
									.equals(operation)) {
								return amount1.divideToIntegralValue(amount2);
							} else if ("max".equals(operation)) {
								return amount1.max(amount2);
							} else if ("min".equals(operation)) {
								return amount1.min(amount2);
							} else if ("remainder".equals(operation)) {
								return amount1.remainder(amount2);
							}
							return null;
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

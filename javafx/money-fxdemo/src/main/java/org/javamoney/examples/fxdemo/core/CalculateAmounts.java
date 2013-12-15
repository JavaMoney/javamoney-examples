package org.javamoney.examples.fxdemo.core;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.money.MonetaryAmount;

import org.javamoney.examples.fxdemo.widgets.AbstractExamplePane;
import org.javamoney.examples.fxdemo.widgets.AbstractSingleSamplePane;
import org.javamoney.examples.fxdemo.widgets.AmountEntry;
import org.javamoney.moneta.Money;

public class CalculateAmounts extends AbstractExamplePane {

	public CalculateAmounts() {
		super(new ExamplePane());
		setExampleTitle("Monetary Amount Arithmethics");
		setExampleDescription("This example shows how to perform arithemtic operations on monetary amounts.");
		setExampleCode(loadExample("/samples/AmountArithmetics.javatxt"));
	}

	public final static class ExamplePane extends AbstractSingleSamplePane {

		private HBox exPane = new HBox();
		private AmountEntry amount1Pane = new AmountEntry("First Amount");
		private AmountEntry amount2Pane = new AmountEntry("Second Amount");
		private ChoiceBox<String> operationChoice = new ChoiceBox<String>();
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
					"divide", "divideToIntegralValue", "remainder");
			Button actionButton = new Button("Perform");
			actionButton
					.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
						public void handle(ActionEvent action) {
							StringWriter sw = new StringWriter();
							PrintWriter pw = new PrintWriter(sw);
							// StringBuilder builder = new StringBuilder();
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
								return Money.from(amount1).add(amount2);
							} else if ("subtract".equals(operation)) {
								return Money.from(amount1).subtract(amount2);
							} else if ("multiply".equals(operation)) {
								return Money.from(amount1).multiply(
										Money.from(amount2).getNumber().doubleValue());
							} else if ("divide".equals(operation)) {
								return Money.from(amount1).divide(
										Money.from(amount2).getNumber().doubleValue());
							} else if ("divideToIntegralValue"
									.equals(operation)) {
								return Money.from(amount1)
										.divideToIntegralValue(
												Money.from(amount2)
											.getNumber().doubleValue());
								// } else if ("max".equals(operation)) {
								// return amount1.max(amount2);
								// } else if ("min".equals(operation)) {
								// return amount1.min(amount2);
							} else if ("remainder".equals(operation)) {
								return Money.from(amount1).remainder(
										Money.from(amount2)
										.getNumber().doubleValue());
							}
							return null;
						}

						private void printSummary(MonetaryAmount amount,
								PrintWriter pw) {
							pw.println("Class: " + amount.getClass().getName());
							pw.println("Currency: " + amount.getCurrency());
							pw.println("Value: "
									+ amount);
						}
					});
			buttonPane.getChildren().add(actionButton);
		}
	}
}

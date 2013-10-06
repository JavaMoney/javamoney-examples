package org.javamoney.examples.fxdemo.core;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javax.money.MonetaryAmount;

import org.javamoney.examples.fxdemo.widgets.AbstractExamplePane;
import org.javamoney.examples.fxdemo.widgets.AbstractSingleSamplePane;
import org.javamoney.examples.fxdemo.widgets.AmountEntry;

public class CreateAmounts extends AbstractExamplePane {

	public CreateAmounts() {
		super(new ExamplePane());
		setExampleTitle("Creating Monetary Amounts");
		setExampleDescription("This example shows how to create monetary amounts with different currencies and with different number representations.");
		setExampleCode(loadExample("/samples/CreateAmounts.javatxt"));
	}

	

	public final static class ExamplePane extends AbstractSingleSamplePane {

		private HBox exPane = new HBox();
		private AmountEntry amount1 = new AmountEntry("Amount");

		public ExamplePane() {
			exPane.getChildren().add(amount1);
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
								MonetaryAmount amount = amount1.getAmount();
								pw.println("MonetaryAmount");
								pw.println("--------------");
								pw.println();
								printSummary(amount, pw);
							} catch (Exception e) {
								e.printStackTrace(pw);
							}
							pw.flush();
							ExamplePane.this.outputArea.setText(sw.toString());
						}

						private void printSummary(MonetaryAmount amount,
								PrintWriter pw) {
							pw.println("Class: " + amount.getClass().getName());
							pw.println("Value (BD): "
									+ amount.asType(BigDecimal.class));
							pw.println("Value (double): "
									+ amount.doubleValue());
							pw.println("Precision: " + amount.getPrecision());
							pw.println("Scale: " + amount.getScale());
						}
					});
			buttonPane.getChildren().add(actionButton);
		}
	}
}

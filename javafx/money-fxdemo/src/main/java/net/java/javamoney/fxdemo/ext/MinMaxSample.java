package net.java.javamoney.fxdemo.ext;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javax.money.MonetaryAmount;
import javax.money.function.Maximum;
import javax.money.function.Minimum;

import net.java.javamoney.fxdemo.widgets.AbstractExamplePane;
import net.java.javamoney.fxdemo.widgets.AbstractSingleSamplePane;
import net.java.javamoney.fxdemo.widgets.AmountEntry;

public class MinMaxSample extends AbstractExamplePane {

	public MinMaxSample() {
		super(new ExamplePane());
		setExampleTitle("Using a monetary extension for min/max.");
		setExampleDescription("This example shows how to use a monetary extension to caluclate in/max for a list of values.");
		setExampleCode(loadExample("/samples/Extensions.javatxt"));
	}

	public final static class ExamplePane extends
			AbstractSingleSamplePane {

		private HBox exPane = new HBox();
		private AmountEntry amount1 = new AmountEntry("Amount 1");
		private AmountEntry amount2 = new AmountEntry("Amount 2");
		private AmountEntry amount3 = new AmountEntry("Amount 3");

		public ExamplePane() {
			exPane.getChildren().addAll(amount1, amount2, amount3);
			this.inputPane.getChildren().add(exPane);
			AnchorPane.setLeftAnchor(exPane, 10d);
			AnchorPane.setTopAnchor(exPane, 10d);
			Button minButton = new Button("Calculate MIN");
			minButton
					.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
						public void handle(ActionEvent action) {
							StringWriter sw = new StringWriter();
							PrintWriter pw = new PrintWriter(sw);
							try {
								MonetaryAmount min = Minimum.from(Arrays.asList(
										amount1.getAmount(), amount2.getAmount(), 
										amount3.getAmount()));
								pw.println("MonetaryAmount (Min)");
								pw.println("--------------------");
								pw.println();
								printSummary(min, pw);
							} catch (Exception e) {
								e.printStackTrace(pw);
							}
							pw.flush();
							ExamplePane.this.outputArea.setText(sw
									.toString());
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
			Button maxButton = new Button("Calculate MAX");
			maxButton
					.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
						public void handle(ActionEvent action) {
							StringWriter sw = new StringWriter();
							PrintWriter pw = new PrintWriter(sw);
							try {
								MonetaryAmount max =  Maximum.from(Arrays.asList(
										amount1.getAmount(), amount2.getAmount(), 
										amount3.getAmount()));
								pw.println("MonetaryAmount (Max)");
								pw.println("--------------------");
								pw.println();
								printSummary(max, pw);
							} catch (Exception e) {
								e.printStackTrace(pw);
							}
							pw.flush();
							ExamplePane.this.outputArea.setText(sw
									.toString());
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
			buttonPane.getChildren().addAll(minButton, new Label(" "), maxButton);
		}
	}
}

package net.java.javamoney.fxdemo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import net.java.javamoney.fxdemo.core.AccessCurrencies;
import net.java.javamoney.fxdemo.core.CalculateAmounts;
import net.java.javamoney.fxdemo.core.CreateAmounts;
import net.java.javamoney.fxdemo.exchange.ConvertAmount;
import net.java.javamoney.fxdemo.exchange.GetExchangeRate;
import net.java.javamoney.fxdemo.ext.MinMaxSample;
import net.java.javamoney.fxdemo.format.FormatAmount;

public class MainScreen extends AbstractFXMLComponent {
	@FXML
	private VBox coreExamplesMenu;

	@FXML
	private VBox conversionExamplesMenu;

	@FXML
	private VBox formattingExamplesMenu;

	@FXML
	private VBox extensionExamplesMenu;

	@FXML
	private AnchorPane sampleContainer;

	public MainScreen() {
		super("MainScreen.fxml");
		initCoreSamples();
	}

	private void initCoreSamples() {
		addButton(coreExamplesMenu, "Create Amounts", CreateAmounts.class);
		addButton(coreExamplesMenu, "Access Currencies", AccessCurrencies.class);
		addButton(coreExamplesMenu, "Monetary Arithmetics",
				CalculateAmounts.class);
		addButton(conversionExamplesMenu, "Access Conversion Rates",
				GetExchangeRate.class);
		addButton(conversionExamplesMenu, "Convert an Amount",
				ConvertAmount.class);
		addButton(formattingExamplesMenu, "Formatting an Amount",
				FormatAmount.class);
		addButton(extensionExamplesMenu, "Using an Extension",
				MinMaxSample.class);
	}

	private void addButton(VBox box, String title,
			final Class<? extends Node> type) {
		Hyperlink button = new Hyperlink(title);
		button.setPrefWidth(200d);
		button.setAlignment(Pos.CENTER_LEFT);
		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				openSample(type);
			}
		});
		box.getChildren().add(button);
	}

	public void openSample(Class<? extends Node> sampleClass) {
		try {
			Node sample = sampleClass.newInstance();
			this.sampleContainer.getChildren().clear();
			this.sampleContainer.getChildren().add(sample);
			AnchorPane.setBottomAnchor(sample, 5d);
			AnchorPane.setTopAnchor(sample, 5d);
			AnchorPane.setRightAnchor(sample, 5d);
			AnchorPane.setLeftAnchor(sample, 5d);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

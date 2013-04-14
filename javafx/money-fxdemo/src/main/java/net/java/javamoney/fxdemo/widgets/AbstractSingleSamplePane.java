package net.java.javamoney.fxdemo.widgets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import net.java.javamoney.fxdemo.AbstractFXMLComponent;

public class AbstractSingleSamplePane extends AbstractFXMLComponent {

	@FXML
	protected HBox buttonPane;

	@FXML
	protected AnchorPane inputPane;

	@FXML
	protected TextArea outputArea;

	public AbstractSingleSamplePane() {
		super("/net/java/javamoney/fxdemo/widgets/SingleSamplePane.fxml");
	}

}
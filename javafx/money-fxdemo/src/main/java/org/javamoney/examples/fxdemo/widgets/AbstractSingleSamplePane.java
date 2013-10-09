package org.javamoney.examples.fxdemo.widgets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.javamoney.examples.fxdemo.AbstractFXMLComponent;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class AbstractSingleSamplePane extends AbstractFXMLComponent {

	@FXML
	protected HBox buttonPane;

	@FXML
	protected AnchorPane inputPane;

	@FXML
	protected TextArea outputArea;

	public AbstractSingleSamplePane() {
		super("/org/javamoney/examples/fxdemo/widgets/SingleSamplePane.fxml");
	}

}
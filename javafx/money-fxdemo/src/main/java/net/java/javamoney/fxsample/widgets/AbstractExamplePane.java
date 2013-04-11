package net.java.javamoney.fxsample.widgets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.java.javamoney.fxsample.AbstractFXMLComponent;

public abstract class AbstractExamplePane extends AbstractFXMLComponent {

	@FXML
	private Label exampleTitle;

	@FXML
	private ImageView exampleImage;

	@FXML
	private Label exampleDescription;
	
	@FXML
	private AnchorPane codePane;

	@FXML
	private AnchorPane contentPane;

	public AbstractExamplePane(Node content) {
		super("/net/java/javamoney/fxsample/widgets/ExamplePane.fxml");
		this.contentPane.getChildren().add(content);
		AnchorPane.setBottomAnchor(content, 0d);
		AnchorPane.setTopAnchor(content, 0d);
		AnchorPane.setRightAnchor(content, 0d);
		AnchorPane.setLeftAnchor(content, 0d);
	}

	/**
	 * @return the exampleTitle
	 */
	public final String getExampleTitle() {
		return exampleTitle.getText();
	}

	public final void setExampleTitle(String title) {
		exampleTitle.setText(title);
	}

	public final void setExampleCode(String code) {
		SamplePage samplePage = new SamplePage(code);
		codePane.getChildren().setAll(samplePage);
		AnchorPane.setBottomAnchor(samplePage, 5d);
		AnchorPane.setTopAnchor(samplePage, 5d);
		AnchorPane.setLeftAnchor(samplePage, 5d);
		AnchorPane.setRightAnchor(samplePage, 5d);
	}

	public final void setExampleDescription(String desc) {
		exampleDescription.setText(desc);
	}
	/**
	 * @return the exampleImage
	 */
	public final ImageView getExampleImage() {
		return exampleImage;
	}

	/**
	 * @return the contentPane
	 */
	public final AnchorPane getContentPane() {
		return contentPane;
	}

	protected String loadExample(String resource) {
		InputStream is = null;
		try {
			is = getClass().getResource(resource).openStream();
			InputStreamReader reader = new InputStreamReader(is);
			StringBuilder builder = new StringBuilder(1024);
			char[] buff = new char[1024];
			int read = reader.read(buff);
			while (read > 0) {
				builder.append(buff, 0, read);
				read = reader.read(buff);
			}
			return builder.toString();
		} catch (Exception e) {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return "Failed to load resource: " + resource;
	}

}

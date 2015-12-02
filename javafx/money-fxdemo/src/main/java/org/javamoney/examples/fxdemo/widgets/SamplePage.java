package org.javamoney.examples.fxdemo.widgets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * SamplePage
 */
public class SamplePage extends BorderPane {
	private static WebEngine engine = null;
	private static WebView webView = null;
	private String rawCode;
	private String htmlCode;

	public SamplePage(String source) throws IllegalArgumentException {
		this.rawCode = source;
		initView();
	}

	public void initView() {
		try {
			// check if 3d sample and on supported platform
			loadCode();
			// create code view
			WebView webView = getWebView();
			webView.setPrefWidth(300);
			engine.loadContent(htmlCode);
			ToolBar codeToolBar = new ToolBar();
			codeToolBar.setId("code-tool-bar");
			Button copyCodeButton = new Button("Copy Source");
			copyCodeButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent actionEvent) {
					Map<DataFormat, Object> clipboardContent = new HashMap<DataFormat, Object>();
					clipboardContent.put(DataFormat.PLAIN_TEXT, rawCode);
					clipboardContent.put(DataFormat.HTML, htmlCode);
					Clipboard.getSystemClipboard().setContent(clipboardContent);
				}
			});
			codeToolBar.getItems().addAll(copyCodeButton);
			setTop(codeToolBar);
			setCenter(webView);
		} catch (Exception e) {
			e.printStackTrace();
			setCenter(new Text("Failed to create sample because of ["
					+ e.getMessage() + "]"));
		}
	}

	protected WebView getWebView() {
		if (engine == null) {
			webView = new WebView();
			webView.setContextMenuEnabled(false);
			engine = webView.getEngine();
		}
		return webView;
	}

	private String shCoreJs;
	private String shBrushJScript;
	private String shCoreDefaultCss;

	private void loadCode() {
		// load syntax highlighter
		if (shCoreJs == null)
			shCoreJs = loadFile(getClass().getResource(
					"syntaxhighlighter/shCore.js"))
					+ ";";
		if (shBrushJScript == null)
			shBrushJScript = loadFile(getClass().getResource(
					"syntaxhighlighter/shBrushJava.js"));
		if (shCoreDefaultCss == null)
			shCoreDefaultCss = loadFile(
					getClass().getResource(
							"syntaxhighlighter/shCoreDefault.css")).replaceAll(
					"!important", "");
		// load and convert source
		String source = rawCode;
		// store raw code
		rawCode = source;
		// escape < & >
		source = source.replaceAll("&", "&amp;");
		source = source.replaceAll("<", "&lt;");
		source = source.replaceAll(">", "&gt;");
		source = source.replaceAll("\"", "&quot;");
		source = source.replaceAll("\'", "&apos;");
		// create content
		StringBuilder html = new StringBuilder();
		html.append("<html>\n");
		html.append("    <head>\n");
		html.append("    <script type=\"text/javascript\">\n");
		html.append(shCoreJs);
		html.append('\n');
		html.append(shBrushJScript);
		html.append("    </script>\n");
		html.append("    <style>\n");
		html.append(shCoreDefaultCss);
		html.append('\n');
		html.append("        .syntaxhighlighter {\n");
		html.append("			overflow: visible;\n");
		if (com.sun.javafx.util.Utils.isMac()) {
			html.append("			font: 12px Ayuthaya !important; line-height: 150% !important; \n");
			html.append("		}\n");
			html.append("		code { font: 12px Ayuthaya !important; line-height: 150% !important; } \n");
		} else {
			html.append("			font: 12px monospace !important; line-height: 150% !important; \n");
			html.append("		}\n");
			html.append("		code { font: 12px monospace !important; line-height: 150% !important; } \n");
		}
		html.append("		.syntaxhighlighter .preprocessor { color: #060 !important; }\n");
		html.append("		.syntaxhighlighter .comments, .syntaxhighlighter .comments a  { color: #009300 !important; }\n");
		html.append("		.syntaxhighlighter .string  { color: #555 !important; }\n");
		html.append("		.syntaxhighlighter .value  { color: blue !important; }\n");
		html.append("		.syntaxhighlighter .keyword  { color: #000080 !important; }\n");
		html.append("    </style>\n");
		html.append("    </head>\n");
		html.append("<body>\n");
		html.append("    <pre class=\"brush: java;gutter: false;toolbar: false;\">\n");
		html.append(source);
		html.append('\n');
		html.append("    </pre>\n"
				+ "    <script type=\"text/javascript\"> SyntaxHighlighter.all(); </script>\n"
				+ "</body>\n" + "</html>\n");
		htmlCode = html.toString();
	}

	private String loadFile(URL resource) {
		InputStream is = null;
		try {
			is = resource.openStream();
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

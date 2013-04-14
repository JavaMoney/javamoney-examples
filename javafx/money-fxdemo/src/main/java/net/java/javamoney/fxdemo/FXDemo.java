package net.java.javamoney.fxdemo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.apache.log4j.Logger;

public class FXDemo extends Application {

	private static final Logger LOGGER = Logger.getLogger(FXDemo.class);

	public void start(final Stage primaryStage) {
		try {
			Scene scene = new Scene(new MainScreen());
			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.setTitle("JSR 354 JavaMoney - Examples");
			primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.show();
		} catch (Exception e) {
			LOGGER.fatal("Failed to start application.", e);
			System.exit(-1);
		}
	}

	public static void main(String[] args) {
		Application.launch(FXDemo.class, args);
	}

}

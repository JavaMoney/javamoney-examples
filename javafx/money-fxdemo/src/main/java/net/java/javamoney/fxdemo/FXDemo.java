package net.java.javamoney.fxdemo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FXDemo extends Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(FXDemo.class);

	public void start(final Stage primaryStage) {
		try {
			Scene scene = new Scene(new MainScreen());
			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.setTitle("JSR 354 JavaMoney - Demo");
			// set icon
			primaryStage.getIcons().add(new Image("/images/javamoney_s.png"));
			primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.show();
		} catch (Exception e) {
			LOGGER.error("Failed to start application.", e);
			System.exit(-1);
		}
	}

	public static void main(String[] args) {
		Application.launch(FXDemo.class, args);
	}

}

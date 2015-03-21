/*
 *  Copyright (c) 2012, 2015, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Contributors:
 *    Anatole Tresch - initial implementation
 *    Werner Keil - extensions and adaptions.
 */
package org.javamoney.examples.fxdemo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anatole Tresch
 * @author Werner Keil
 *
 */
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

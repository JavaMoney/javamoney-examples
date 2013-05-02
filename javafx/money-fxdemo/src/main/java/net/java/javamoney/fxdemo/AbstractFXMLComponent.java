/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
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
package net.java.javamoney.fxdemo;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anatole Tresch
 * @author Werner Keil
 *
 */
public abstract class AbstractFXMLComponent extends AnchorPane {

	private static final String DEFAULT_BUNDLE = "i18n/translation";

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private String fxmlResource;
	private String resourceBundle;
	private Node ui;

	public AbstractFXMLComponent() {
		this(null, null);
	}

	public AbstractFXMLComponent(String fxmlResource) {
		this(fxmlResource, null);
	}

	public AbstractFXMLComponent(String fxmlResource, String resourceBundle) {
		super();
		setId(getClass().getSimpleName());
		initComponent(fxmlResource, resourceBundle);
		initFields();
	}

	public String getFXMLResource() {
		return fxmlResource;
	}

	public String getResourceBundle() {
		return this.resourceBundle;
	}

	private void initComponent(String fxmlResource, String resourceBundle) {
		// init rest
		if (fxmlResource == null || fxmlResource.isEmpty()) {
			this.fxmlResource = getClass().getName() + ".fxml";
		} else {
			this.fxmlResource = fxmlResource;
		}
		this.resourceBundle = resourceBundle;
		if (this.resourceBundle == null) {
			this.resourceBundle = DEFAULT_BUNDLE;
		}
		// initUI
		Locale userLocale = Locale.ENGLISH; // TODO i18n
		try {
			ui = FXMLLoader.load(getClass().getResource(this.fxmlResource),
					ResourceBundle.getBundle(this.resourceBundle, userLocale));
			this.getChildren().add(ui);
			AnchorPane.setBottomAnchor(ui, 0d);
			AnchorPane.setTopAnchor(ui, 0d);
			AnchorPane.setLeftAnchor(ui, 0d);
			AnchorPane.setRightAnchor(ui, 0d);
		} catch (IOException e) {
			throw new IllegalArgumentException("Failed to load component: "
					+ this, e);
		}
	}

	private void initFields() {
		Class<?> clazz = getClass();
		while (clazz != null) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field f : fields) {
				if (f.getAnnotation(FXML.class) != null) {
					Node value = ComponentUtil.lookup(ui, f.getName());
					if (value == null) {
						throw new IllegalArgumentException("Lookup failed of "
								+ f.getName() + " in " + this.fxmlResource);
					}
					if (!f.isAccessible()) {
						f.setAccessible(true);
					}
					try {
						f.set(this, value);
						if (logger.isDebugEnabled()) {
							logger.debug("Initialized field: " + f.getName()
									+ " with " + value);
						}
					} catch (Exception e) {
						logger.error(
								"Failed to initialize field: " + f.getName(), e);
					}
				}
			}
			clazz = clazz.getSuperclass();
		}
		
	}

}

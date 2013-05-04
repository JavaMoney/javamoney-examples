/*
 * JSR 354 Examples
 * Copyright 2012-2013, Credit Suisse AG, Red Hat, Inc. and/or its affiliates, 
 * and individual contributors by the @author tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.java.javamoney.examples.payment.events;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Werner Keil
 * 
 */
public enum PaymentType {

	CREDIT("1"), DEBIT("2");

	private final String value;

	static final Map<String, PaymentType> map = new HashMap<String, PaymentType>();

	static {
		for (PaymentType paymentType : PaymentType.values()) {
			map.put(paymentType.getValue(), paymentType);
		}
	}

	private PaymentType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static PaymentType of(String value) {
		return map.get(value);
	}
}

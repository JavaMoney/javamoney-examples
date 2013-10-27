/*
 * JavaMoney Examples
 * Copyright 2012 Red Hat, Inc. and/or its affiliates,
 * and individual contributors by the @author tags. See the copyright.txt in the
 * distribution for a full listing of individual contributors
 *
 * Copyright 2012-2013, Credit Suisse AG, Werner Keil 
 * and individual contributors by the @author tags.
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
package org.javamoney.examples.cdi.payment.handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.javamoney.examples.cdi.payment.events.PaymentEvent;
import org.javamoney.examples.cdi.payment.qualifiers.Credit;
import org.javamoney.examples.cdi.payment.qualifiers.Debit;


/**
 * @author Werner Keil
 *
 */
@SessionScoped
public class PaymentHandler implements Serializable,ICreditEventObserver, IDebitEventObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5039671005494708392L;

	@Inject
	private Logger logger;
	
	List<PaymentEvent> payments=new ArrayList<PaymentEvent>();
	
	@Produces
	@Named
	public List<PaymentEvent> getPayments() {
		return payments;
	}


	public void onCreditPaymentEvent(@Observes @Credit PaymentEvent event){

		logger.info("Processing the credit operation "+event);
		payments.add(event);
	}

	
	public void onDebitPaymentEvent(@Observes @Debit PaymentEvent event) {
		logger.info("Processing the debit operation "+event);
		payments.add(event);
		
	}

}

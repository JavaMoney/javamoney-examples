/*
 * JSR 354 Examples
 * Copyright 2012-2013, Credit Suisse, Red Hat, Inc., and individual
 * contributors by the @author tag. See the copyright.txt in the 
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

import java.util.Date;

import javax.money.MonetaryAmount;


/**
 * @author Werner Keil
 *
 */
public class PaymentEvent {
	private PaymentType type;  //credit or debit
	private Date datetime;
	private MonetaryAmount money;
	
	public PaymentType getType() {
		return type;
	}
	public void setType(PaymentType type) {
		this.type = type;
	}

	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String toString(){
		return "EVT:"+getDatetime()+":"+getMoney()+":"+getType();
	}
	public MonetaryAmount getMoney() {
		return money;
	}
	public void setMoney(MonetaryAmount money) {
		this.money = money;
	}
	
}

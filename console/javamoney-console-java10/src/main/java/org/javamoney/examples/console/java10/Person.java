/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 *
 * JavaMoney Examples
 * Copyright 2014, Werner Keil 
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
package org.javamoney.examples.console.java10;

import java.util.List;
import java.util.ArrayList;
import java.time.chrono.IsoChronology;
import java.time.LocalDate;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;

/**
 * 
 * @author Werner Keil
 *
 */
public class Person {

	public enum Sex {
		MALE, FEMALE
	}

	final String name;
	final LocalDate birthday;
	final Sex gender;
	final String emailAddress;
	final MonetaryAmount salary;

	Person(String nameArg, LocalDate birthdayArg, Sex genderArg,
			String emailArg, MonetaryAmount salArg) {
		name = nameArg;
		birthday = birthdayArg;
		gender = genderArg;
		emailAddress = emailArg;
		salary = salArg;
	}

	public int getAge() {
		return birthday.until(IsoChronology.INSTANCE.dateNow()).getYears();
	}

	public MonetaryAmount getSalary() {
		return salary;
	}

	public void printPerson() {
		System.out.println(name + ", " + this.getAge());
	}

	public Sex getGender() {
		return gender;
	}

	public String getName() {
		return name;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	LocalDate getBirthday() {
		return birthday;
	}

	public static int compareByAge(Person a, Person b) {
		return a.birthday.compareTo(b.birthday);
	}

	public static int compareBySalary(Person a, Person b) {
		return a.salary.compareTo(b.salary);
	}
	
	public static List<Person> createRoster() {
		final List<Person> roster = new ArrayList<>();
		roster.add(new Person("Fred", IsoChronology.INSTANCE.date(1980, 6, 20),
				Person.Sex.MALE, "fred@example.com", Money.of(100000, "USD")));
		roster.add(new Person("Jane", IsoChronology.INSTANCE.date(1990, 7, 15),
				Person.Sex.FEMALE, "jane@example.com", Money.of(80000, "USD")));
		roster.add(new Person("George", IsoChronology.INSTANCE
				.date(1991, 8, 13), Person.Sex.MALE, "george@example.com",
				Money.of(70000, "USD")));
		roster.add(new Person("Bob", IsoChronology.INSTANCE.date(2000, 9, 12),
				Person.Sex.MALE, "bob@example.com", Money.of(25000, "USD")));

		return roster;
	}

}

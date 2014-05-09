/*
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
package org.javamoney.examples.console.java8;

import java.util.List;
import java.util.ArrayList;
import java.time.chrono.IsoChronology;
import java.time.LocalDate;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;

public class Person {

	public enum Sex {
		MALE, FEMALE
	}

	String name;
	LocalDate birthday;
	Sex gender;
	String emailAddress;
	MonetaryAmount salary;

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

	public LocalDate getBirthday() {
		return birthday;
	}

	public static int compareByAge(Person a, Person b) {
		return a.birthday.compareTo(b.birthday);
	}

	public static List<Person> createRoster() {

		List<Person> roster = new ArrayList<>();
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

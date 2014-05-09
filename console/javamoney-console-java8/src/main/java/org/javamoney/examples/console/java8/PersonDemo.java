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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import javax.money.MonetaryAmount;

public class PersonDemo {

	public static void main(String[] args) {

		final List<Person> roster = Person.createRoster();

		// printPersonsOlderThan(roster, 30);

		// printPersons(roster, new CheckPersonEligibleForSelectiveService());

		/*
		 * printPersons( roster, new CheckPerson() { public boolean test(Person
		 * p) { return p.getGender() == Person.Sex.MALE && p.getAge() >= 18 &&
		 * p.getAge() <= 25; } } );
		 */

		/*
		 * printPersons(roster, (Person p) -> p.getGender() == Person.Sex.MALE
		 * && p.getAge() >= 18 && p.getAge() <= 25);
		 */

		/*
		 * printPersonsWithPredicate(roster, p -> p.getGender() ==
		 * Person.Sex.MALE && p.getAge() >= 18 && p.getAge() <= 25);
		 */

		/*
		 * processPersons( roster, p -> p.getGender() == Person.Sex.MALE &&
		 * p.getAge() >= 18 && p.getAge() <= 25, p -> p.printPerson());
		 */

		/*
		 * processPersonsWithFunction( roster, p -> p.getGender() ==
		 * Person.Sex.MALE && p.getAge() >= 18 && p.getAge() <= 25, p ->
		 * p.getEmailAddress(), email -> System.out.println(email));
		 */

		/*
		 * processElements( roster, p -> p.getGender() == Person.Sex.MALE &&
		 * p.getAge() >= 18 && p.getAge() <= 25, p -> p.getEmailAddress(), email
		 * -> System.out.println(email));
		 */

		/*
		 * roster.stream() .filter(p -> p.getGender() == Person.Sex.MALE &&
		 * p.getAge() >= 18 && p.getAge() <= 25 &&
		 * p.getSalary().getNumber().intValue() <= 100000) //.map(p ->
		 * p.getEmailAddress()) //.forEach(email -> System.out.println(email));
		 * .map(p -> p.getSalary()) .forEach(salary ->
		 * System.out.println(salary));
		 */

		processPersonsWithOperator(
				roster,
				p -> p.getGender() == Person.Sex.MALE && p.getAge() >= 18
						&& p.getAge() <= 25, s -> s,
				salary -> System.out.println(salary));

	}

//	private static void processPersonsWithOperator(List<Person> roster,
//			Predicate<Person> tester, Object mapper,
//			Consumer<MonetaryAmount> block) {
//		for (Person p : roster) {
//			if (tester.test(p)) {
//				MonetaryAmount data = mapper.apply(p.getSalary());
//				block.accept(data);
//			}
//		}
//		
//	}


	static void printPersonsOlderThan(List<Person> roster, int age) {
		for (Person p : roster) {
			if (p.getAge() >= age) {
				p.printPerson();
			}
		}
	}

	static void printPersonsWithinAgeRange(List<Person> roster, int low,
			int high) {
		for (Person p : roster) {
			if (low <= p.getAge() && p.getAge() < high) {
				p.printPerson();
			}
		}
	}

	static void printPersons(List<Person> roster, CheckPerson tester) {
		for (Person p : roster) {
			if (tester.test(p)) {
				p.printPerson();
			}
		}
	}

	static void printPersonsWithPredicate(List<Person> roster,
			Predicate<Person> tester) {
		for (Person p : roster) {
			if (tester.test(p)) {
				p.printPerson();
			}
		}
	}

	static void processPersons(List<Person> roster,
			Predicate<Person> tester, Consumer<Person> block) {
		for (Person p : roster) {
			if (tester.test(p)) {
				block.accept(p);
			}
		}
	}

	static void processPersonsWithFunction(List<Person> roster,
			Predicate<Person> tester, Function<Person, String> mapper,
			Consumer<String> block) {
		for (Person p : roster) {
			if (tester.test(p)) {
				String data = mapper.apply(p);
				block.accept(data);
			}
		}
	}

	static <X, Y> void processElements(Iterable<X> source,
			Predicate<X> tester, Function<X, Y> mapper, Consumer<Y> block) {
		for (X p : source) {
			if (tester.test(p)) {
				Y data = mapper.apply(p);
				block.accept(data);
			}
		}
	}

	static void processPersonsWithOperator(List<Person> roster,
			Predicate<Person> tester, UnaryOperator<MonetaryAmount> mapper,
			Consumer<MonetaryAmount> block) {
		for (Person p : roster) {
			if (tester.test(p)) {
				MonetaryAmount data = mapper.apply(p.getSalary());
				block.accept(data);
			}
		}
	}
}

interface CheckPerson {
	boolean test(Person p);
}

class CheckPersonEligibleForSelectiveService implements CheckPerson {
	public boolean test(Person p) {
		return p.gender == Person.Sex.MALE && p.getAge() >= 18
				&& p.getAge() <= 25;
	}
}

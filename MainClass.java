//package com.mssint.concept;
package MSS;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainClass extends BaseClass {

	static final String [][] peopleArray = {
			{"George", "Washington"},
			{"James", "Madison"},
			{"Alexander", "Hamilton"},
			{"Gouverneur", "Morris"},
			{"Robert", "Morris"},
			{"James", "Wilson"},
			{"Chas", "Pinckney"},
			{"John", "Rutledge"},
			{"Pierce", "Butler"}
	};
	
	static class Person {
		String firstname;
		String surname;
		Person(String ... name) {
			firstname = name[0];
			surname = name[1];
		}
		public String toString() {
			return surname + ", " + firstname;
		}
	}
	List<Person> people;
	Iterator<Person> iterator;
	Person currentPerson;
	
	Method loadPeople;
	Method printHeader;
	Method nextPerson;
	Method printPerson;
	Method printFooter;
	
	Method loadPeople() {
		people = new ArrayList<>();
		for(String [] person : peopleArray) {
			people.add(new Person(person));
		}
		iterator = people.iterator();
		return null;
	}
	
	Method printHeader() {
		System.out.println("Partial list of signers");
		System.out.println("-----------------------");
		return null;
	}
	
	Method nextPerson() {
		if(iterator.hasNext()) {
			currentPerson = iterator.next();
		} else {
			currentPerson = null;
		}
		return null;
	}
	
	Method printPerson() {
		if(currentPerson == null)
			return null;
		System.out.println("Person: " + currentPerson);
		return nextPerson;
	}
	
	Method printFooter() {
		System.out.println("-----------------------");
		return null;
	}

	public static void main(String[] args) {
		MainClass mainClass = new MainClass();
		mainClass.run();
	}
}

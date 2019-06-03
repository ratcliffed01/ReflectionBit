//package com.mssint.concept;
package MSS;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * This program should get numbers from keyboard. Each input is validated as a
 * valid integer and then added to the list. Once the list is complete, the
 * numbers are sorted using a bubble sort algorythm. 
 *
 */
public class MainClass2 extends BaseClass {
	
	private List<Integer> numberList;
	private String inputString;
	private Scanner input;
	
	Method start;
	Method createScanner;
	Method getInput;
	Method addInputToArray;
	Method doneWithInput;
	
	Method startSort;
	Method compare;
	Method needAnotheGo;
	Method sortComplete;
	
	private boolean swapped;
	private int index;
	
	
	Method start() {
		numberList = new ArrayList<>();
		return null;
	}
	
	Method createScanner() {
		input = new Scanner(System.in);
		return null;
	}
	
	Method getInput() {
		System.out.print("Add number to sort list. Enter when done: ");
		inputString = input.nextLine();
		if(inputString.trim().length() == 0)
			return doneWithInput;
		return null;
	}
	
	Method addInputToArray() {
		try {
			int value = Integer.parseInt(inputString.trim());
			numberList.add(value);
		} catch (NumberFormatException e) {
			System.out.println("\"" + inputString +"\" is not a valid number");
		}
		return getInput;
	}
	
	Method doneWithInput() {
		input.close();
		System.out.println("Array to be sorted: " + numberList);
		
		return null;
	}
	
	Method startSort() {
		if(numberList.size() < 2)
			return sortComplete;
		swapped = false;
		index = 1;
		return null;
	}

	Method compare() {
		if(numberList.get(index - 1) > numberList.get(index)) {
			int val = numberList.get(index);
			numberList.set(index, numberList.get(index - 1));
			numberList.set(index - 1, val);
			swapped = true;
		}
		index++;
		if(index < numberList.size()) {
			return compare;
		}
		return null;
	}
	
	Method needAnotheGo() {
		if(swapped)
			return startSort;
		return null;
	}
	
	Method sortComplete() {
		System.out.println("Sorted result: " + numberList);
		return null;
	}

	public static void main(String[] args) {
		MainClass2 mainClass = new MainClass2();
		mainClass.run();
	}
}

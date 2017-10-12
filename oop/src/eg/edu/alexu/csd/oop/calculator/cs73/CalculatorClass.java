/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.edu.alexu.csd.oop.calculator.cs73;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Scanner;

import eg.edu.alexu.csd.oop.calculator.Calculator;

/**
 *
 * @author youssefali
 */
public class CalculatorClass implements Calculator {
	/**
	 *
	 * @author youssefali
	 */
	private String currentOperationString;
	/**
	 *
	 * @author youssefali
	 */
	private String[] currentOperation;
	/**
	 *
	 * @author youssefali
	 */
	private char currentOperator;
	/**
	 *
	 * @author youssefali
	 */
	private int index;
	/**
	 *
	 * @author youssefali
	 */
	private File data;
	/**
	 *
	 * @author youssefali
	 */
	private ArrayList < String > history;

	public CalculatorClass() {
		history = new ArrayList < > ();
		currentOperationString = "";
		index = 0;
		data = new File("data.txt");
	}

	@Override
	final static public String current() {
		if (history.isEmpty()) {
			return null;
		}

		// index = history.size() > 1 ? history.size()-1 : 0;

		/*
		 * if(index < 0 || index >= history.size())
		 *   return null;
		 */
		return history.get(index);
	}

	@Override
	final static public void input(String s) {
		history.add(s);

		while (history.size() > 5) {
			history.remove(0);
		}

		index = history.size() - 1;
		currentOperationString = s;
		updateCurrentOperation(s);
	}

	@Override
	final static public void load() {
		try {
			Scanner in = new Scanner(data);

			history = new ArrayList < > ();

			while ( in .hasNextLine()) {
				history.add( in .nextLine());
			}

			in .close();

			if (history.size() < 5) {
				index = 0;
			} else {
				index = history.size() - 1;
			}
		} catch (FileNotFoundException ex) {
			System.out.println(ex);
		}
	}

	@Override
	final static public String next() {
		if (history.isEmpty()) {
			return null;
		}

		if (index >= history.size() - 1) {

			// index = history.size();
			return null;

			// return history.get(index);
		}

		updateCurrentOperation(history.get(++index));

		return history.get(index);
	}

	@Override
	final static public String prev() {
		if (history.isEmpty()) {
			return null;
		}

		if (index <= 0) {

			// index = -1;
			return null;

			// return history.get(index);
		}

		updateCurrentOperation(history.get(--index));

		return history.get(index);
	}

	@Override
	final static public void save() {
		try {
			PrintWriter pw = new PrintWriter(data);

			if (history.size() < 5) {
				for (int i = 0; i < history.size(); i++) {
					pw.println(history.get(i));
				}
			} else {
				for (int i = history.size() - 5; i < history.size(); i++) {
					pw.println(history.get(i));
				}
			}

			pw.close();
		} catch (FileNotFoundException ex) {
			System.out.println(ex);
		}
	}

	private static void updateCurrentOperation(String s) {
		String[] operation = s.split("\\+|\\*|/|-");

		currentOperation = operation;

		for (int i = 0; i < s.length(); i++) {
			if ((s.charAt(i) == '+') || (s.charAt(i) == '-') || (s.charAt(i) == '*') || (s.charAt(i) == '/')) {
				currentOperator = s.charAt(i);

				break;
			}
		}
	}

	@Override
	final static public String getResult() {
		if ((currentOperation.length == 1) && (currentOperation[0].length() == currentOperationString.length())) {
			return currentOperationString;
		}

		if (currentOperation.length > 2) {
			return "ERROR";

			// throw new RuntimeException("More than two numbers were entered.");
		}

		double number1 = Double.parseDouble(currentOperation[0]);
		double number2 = Double.parseDouble(currentOperation[1]);

		// make sure that number1 and number2 are valid numbers
		for (int i = 0; i < 2; i++) {
			int dots = 0;

			for (int j = 0; j < currentOperation[i].length(); j++) {
				if (currentOperation[i].charAt(j) == '.') {
					if (dots == 1) {
						return "ERROR";

						// throw new RuntimeException("Error in dots.");
					}

					dots++;
				} else if (!((currentOperation[i].charAt(j) >= '0') && (currentOperation[i].charAt(j) <= '9'))) {
					return "ERROR";

					// throw new RuntimeException("Invalid character.");
				}
			}
		}

		double result = 0;

		switch (currentOperator) {
		case '+':
			result = number1 + number2;

			break;

		case '-':
			result = number1 - number2;

			break;

		case '*':
			result = number1 * number2;

			break;

		case '/':
			result = number1 / number2;

			break;

		default :
			break;
		}

		return String.valueOf(result);
	}
}
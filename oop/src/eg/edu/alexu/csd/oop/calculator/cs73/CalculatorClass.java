/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.edu.alexu.csd.oop.calculator.cs73;
import eg.edu.alexu.csd.oop.calculator.Calculator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author youssefali
 */
public class CalculatorClass implements Calculator {
    
    private String currentOperationString = "";
    private String[] currentOperation;
    private char currentOperator;
    private int index = 0;
    private File data = new File("data.txt");
    
    private ArrayList<String> history = new ArrayList<>();

    @Override
    public void input(String s) {
        history.add(s);
        currentOperationString = s;
        String[] operation = s.split("\\+|\\*|/|-");
        currentOperation = operation;
        for(int i=0; i<s.length(); i++)
            if(s.charAt(i) == '+' || s.charAt(i) == '-' || s.charAt(i) == '*' || s.charAt(i) == '/'){
                currentOperator = s.charAt(i);
                break;
            }
    }

    @Override
    public String getResult() {
        if(currentOperation.length > 2)
            throw new RuntimeException("More than two numbers were entered.");
        
        double number1 = Double.parseDouble(currentOperation[0]);
        double number2 = Double.parseDouble(currentOperation[1]);
        
        // make sure that number1 and number2 are valid numbers
        for(int i=0; i<2; i++){
            int dots = 0;
            for(int j=0; j<currentOperation[i].length(); j++){
                if(currentOperation[i].charAt(j) == '.'){
                    if(dots == 1)
                        throw new RuntimeException("Error in dots.");
                    dots++;
                }else if(!(currentOperation[i].charAt(j) >= '0' && currentOperation[i].charAt(j) <= '9')){
                    throw new RuntimeException("Invalid character.");
                }
            }
        }
        
        double result = 0;
        switch(currentOperator){
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
        }
        return String.valueOf(result);
    }

    @Override
    public String current() {
        return currentOperationString;
    }

    @Override
    public String prev() {
        return history.get(--index);
    }

    @Override
    public String next() {
        return history.get(++index);
    }

    @Override
    public void save() {
        try {
            PrintWriter pw = new PrintWriter(data);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void load() {
    }
    
}

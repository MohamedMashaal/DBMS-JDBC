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
import java.util.Scanner;

/**
 *
 * @author youssefali
 */
public class CalculatorClass implements Calculator {
    
    private String currentOperationString;
    private String[] currentOperation;
    private char currentOperator;
    private int index;
    private File data;
    
    private ArrayList<String> history;

    public CalculatorClass(){
        history = new ArrayList<>();
        currentOperationString = "";
        index = 0;
        data = new File("data.txt");
    }
    
    @Override
    public void input(String s) {
        history.add(s);
        index = history.size()-1;
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
        if(currentOperation.length == 1 && currentOperation[0].length() == currentOperationString.length())
            return currentOperationString;
        if(currentOperation.length > 2){
            return "ERROR";
            //throw new RuntimeException("More than two numbers were entered.");
        }
        
        double number1 = Double.parseDouble(currentOperation[0]);
        double number2 = Double.parseDouble(currentOperation[1]);
        
        // make sure that number1 and number2 are valid numbers
        for(int i=0; i<2; i++){
            int dots = 0;
            for(int j=0; j<currentOperation[i].length(); j++){
                if(currentOperation[i].charAt(j) == '.'){
                    if(dots == 1){
                        return "ERROR";
                        //throw new RuntimeException("Error in dots.");
                    }
                    dots++;
                }else if(!(currentOperation[i].charAt(j) >= '0' && currentOperation[i].charAt(j) <= '9')){
                    return "ERROR";
                    //throw new RuntimeException("Invalid character.");
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
        if(history.isEmpty())
            return null;
        index = history.size() > 1 ? history.size()-1 : 0;
        return history.get(history.size()-1);
    }

    @Override
    public String prev() {
        if(history.isEmpty())
            return "";
        if(index == 0)
            return history.get(index);
        return history.get(--index);
    }

    @Override
    public String next() {
        if(history.isEmpty())
            return "";
        if(index == history.size()-1){
            return history.get(index);
        }
        return history.get(++index);
    }

    @Override
    public void save() {
        try {
            PrintWriter pw = new PrintWriter(data);
            if(history.size() < 5){
                for(int i=0; i<history.size(); i++){
                    pw.println(history.get(i));
                }
            }
            else{
                for(int i=history.size()-5; i<history.size(); i++){
                    pw.println(history.get(i));
                }
            }
            pw.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void load() {
        try {
            Scanner in = new Scanner(data);
            history = new ArrayList<>();
            while(in.hasNextLine())
                history.add(in.nextLine());
            in.close();
            index = history.size()-1;
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }
    
}

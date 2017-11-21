package eg.edu.alexu.csd.oop.db.cs73.Model;

import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.Column;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.Table;

import java.util.ArrayList;
import java.util.Stack;

public class ConditionHandler {

    Stack<String> stack;

    public ArrayList<Integer> getValidIndicesFrom(Table table, String condition){ // condition:String, after-where part.
        ArrayList<Integer> currentIndices = null;





        return currentIndices;
    }

    public ArrayList<String> infixToPostfix(ArrayList<String> infix){
        ArrayList<String> postfix = new ArrayList<>();
        stack = new Stack<>();

        int openPar = 0;
        boolean prevIsOpr = false;

        for(String part : infix){
            if(isOperator(part)){
                if(prevIsOpr){
                    throw new RuntimeException("Error: Invalid expression after where cluase. Hint: Check operators.");
                }

                while(!stack.isEmpty() && precedence(part) <= precedence(stack.peek())){
                    postfix.add(stack.pop());
                }
                stack.push(part);
                prevIsOpr = true;
            }
            else if(part.equalsIgnoreCase("(")){
                if(prevIsOpr){
                    throw new RuntimeException("Error: Invalid expression after where cluase. Hint: Check operators.");
                }

                openPar++;
                stack.push(part);
                prevIsOpr = true;
            }
            else if(part.equalsIgnoreCase(")")){
                if(prevIsOpr){
                    throw new RuntimeException("Error: Invalid expression after where cluase. Hint: Check operators.");
                }
                if(openPar == 0){
                    throw new RuntimeException("Error: Invalid expression after where cluase. Hint: Check parenthesis.");
                }

                while(!stack.peek().equalsIgnoreCase("(")){
                    postfix.add(stack.pop());
                }
                if(stack.peek().equalsIgnoreCase("(")){
                    stack.pop();
                }
                openPar--;
                prevIsOpr = true;
            }
            else{ // conditional term
                prevIsOpr = false;
                postfix.add(part);
            }
        }

        while(!stack.isEmpty()){
            postfix.add(stack.pop());
        }

        return postfix;
    }

    //public ArrayList<Integer> evaluate(){}

    private boolean isOperator(String part) {
        return part.equalsIgnoreCase("not") || part.equalsIgnoreCase("and") || part.equalsIgnoreCase("or");
    }

    private int precedence(String operator){
        if(operator.equalsIgnoreCase("not"))
            return 3;
        if(operator.equalsIgnoreCase("and"))
            return 2;
        if(operator.equalsIgnoreCase("or"))
            return 1;
        return -1;
    }
}

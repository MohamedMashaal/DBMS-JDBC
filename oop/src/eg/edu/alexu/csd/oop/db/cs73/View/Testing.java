package eg.edu.alexu.csd.oop.db.cs73.View;

import java.sql.SQLException;
import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.Database;

/*

    This class is just for testing purposes

 */

import eg.edu.alexu.csd.oop.db.cs73.Model.ConditionHandler;
import eg.edu.alexu.csd.oop.db.cs73.Model.DatabaseImp;

public class Testing {

    public static void main(String[] args){

       /*String x = "UPDATE Customers SET ContactName = 'Alfred Schmidt', City = 'Frankfurt' WHERE CustomerID = 1;" ;
        x = x.replaceAll("\\)", " ").replaceAll("\\(", " ").replaceAll("'", "").replaceAll("=", " = ").replaceAll("\\s+\\,", ",");
        System.out.println(x);
        String [] z = x.split("\\s+|\\,\\s*|\\(|\\)");
        for(String v : z)
        	System.out.println(v);*/

        ConditionHandler ch = new ConditionHandler();
        ArrayList<String> arr = new ArrayList<>();
        /*arr.add("not");
        arr.add("(");
        arr.add("a = 5");
        arr.add("and");
        arr.add("b > 6");
        arr.add(")");*/
        arr.add("a = 5");
        arr.add("and");
        arr.add("b > 5");
        arr.add("or");
        arr.add("c < 5");
        arr = ch.infixToPostfix(arr);
        System.out.println(arr);
    }

}

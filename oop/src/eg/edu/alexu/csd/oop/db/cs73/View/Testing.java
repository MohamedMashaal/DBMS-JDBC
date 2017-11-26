package eg.edu.alexu.csd.oop.db.cs73.View;

import eg.edu.alexu.csd.oop.db.commands.Insert;
import eg.edu.alexu.csd.oop.db.cs73.Model.ExtractingHandler;

public class Testing {

    public static void main(String[] args){

        /*String x = "UPDATE Customers SET ContactName = 'Alfred Schmidt', City = 'Frankfurt' WHERE CustomerID = 1;" ;
        x = "select col from table where a > 5 and b = 'john wick'";
        x = x.replaceAll("\\)", " ").replaceAll("\\(", " ").replaceAll("\\s+\\,", ",")
                .replaceAll("\\s*\"\\s*","\"").replaceAll("\\s*'\\s*","'").replaceAll("=", " = ");
        System.out.println(x);
        String [] z = x.split("\\s+|\\,\\s*|\\(|\\)");
        //System.out.print("[");
        for(String v : z)
        	System.out.println(v);
        //System.out.println(" ]");

        /*ConditionHandler ch = new ConditionHandler();
        ArrayList<String> arr = new ArrayList<>();
//        arr.add("not");
//        arr.add("(");
//        arr.add("a = 5");
//        arr.add("and");
//        arr.add("b > 6");
//        arr.add(")");
        arr.add("a = 5");
        arr.add("and");
        arr.add("b > 5");
        arr.add("or");
        arr.add("c < 5");
        arr = ch.infixToPostfix(arr);
        System.out.println(arr);

        String[] test = {"a",">","5","and","b","=","'john","wick'"};
        System.out.println(ch.getWillFormedArrayOf(test));*/
    	String query = "UPDATE Customers SET ContactName='Juan' WHERE Country='Mexico'";
    	String [] splittedQuery = query.replaceAll("\\)", " ").replaceAll("\\(", " ").replaceAll("=", " = ").replaceAll("\\s+\\,", ",").split("\\s+(?=(?:[^\']*\'[^\']*\')*[^\']*$)|\\,\\s*|\\(|\\)");
    	for(String x : splittedQuery)
    		System.out.println(x);
    	ExtractingHandler exHandler = new ExtractingHandler();
    	splittedQuery = exHandler.filterQuotes(splittedQuery);
    	System.out.println("After filtering");
    	for(String x : splittedQuery) {
    		System.out.println(x);
    	}
    	/*Database db = new DatabaseImp();
        db.createDatabase("db1", true);
        try {
            db.executeStructureQuery("create table table1 (name varchar, age int)");
            db.executeUpdateQuery("insert into table1 values ('ahmed', 15)");
            db.executeUpdateQuery("insert into table1 (name) values ('khaled')");
            db.executeUpdateQuery("insert into table1 values ('hassan', 26)");
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

}

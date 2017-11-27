package eg.edu.alexu.csd.oop.db.cs73.View;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs73.Model.DatabaseImp;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.DBContainer;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.Table;

import java.io.File;
import java.sql.SQLException;

public class Testing {

    public static void main(String[] args){

//        String x = "CREATE TABLE table_name1(column_name1 varchar , column_name2 int, column_name3 varchar)" ;
//        String [] z = x.replaceAll("\\)", " ").replaceAll("\\(", " ").replaceAll("'", "").replaceAll("\\s+\\,", ",").split("\\s+|\\,\\s*|\\(|\\)|\\=");
//        for(String v : z)
//        System.out.println(v);
//        //String [] z = x.split("\\s+|\\,\\s*|\\(|\\)");
        //System.out.print("[");
        /*for(String v : z)
        	System.out.println(v);
        *///System.out.println(" ]");

/*        ConditionHandler ch = new ConditionHandler();
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
        System.out.println(ch.getWillFormedArrayOf(test));
    	String query = "UPDATE Customers SET ContactName='Juan' WHERE Country='Mexico'";
    	String [] splittedQuery = query.replaceAll("\\)", " ").replaceAll("\\(", " ").replaceAll("=", " = ").replaceAll("\\s+\\,", ",").split("\\s+(?=(?:[^\']*\'[^\']*\')*[^\']*$)|\\,\\s*|\\(|\\)");
    	for(String x : splittedQuery)
    		System.out.println(x);
    	ExtractingHandler exHandler = new ExtractingHandler();
    	splittedQuery = exHandler.filterQuotes(splittedQuery);
    	System.out.println("After filtering");
    	for(String x : splittedQuery) {
    		System.out.println(x);
    	}*/
    	Database db = new DatabaseImp();
        String x = db.createDatabase("db2", false);
        try {
        	db.executeStructureQuery("create table table1 (name varchar, age int)");
        	db.executeStructureQuery("create table table2 (name varchar, age int)");
        	for(File dbFile : new File("data").listFiles()){
                
                    DBContainer dbObj = new DBContainer(dbFile.getName());
                    for(File tableFile : dbFile.listFiles()){
                        System.out.println(tableFile.getName());
                    }
                    }
            db.executeStructureQuery("create table table1 (name varchar, age int)");
            db.executeUpdateQuery("insert into table1 values ('hassan', 15)");
            db.executeUpdateQuery("insert into table1 (name) values ('hesham')");
            db.executeUpdateQuery("insert into table1 values ('hassan', 60)");
            db.executeUpdateQuery("insert into table1 values ('hassan', 100)");
            //db.executeQuery("select name,age from table2");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.createDatabase("db2", true);
        try {
			db.executeStructureQuery("drop database db2");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}

}

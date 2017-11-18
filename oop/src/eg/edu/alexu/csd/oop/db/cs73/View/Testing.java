package eg.edu.alexu.csd.oop.db.cs73.View;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

/*

    This class is just for testing purposes

 */

import eg.edu.alexu.csd.oop.db.cs73.Model.DatabaseImp;

public class Testing {

    public static void main(String[] args){
        String x = "Create TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)";
        String [] z = x.split("\\s|\\,\\s*|\\(|\\)");
        for(String v : z)
        	System.out.println(v);
    }

}

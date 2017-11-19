package eg.edu.alexu.csd.oop.db.cs73.View;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

/*

    This class is just for testing purposes

 */

import eg.edu.alexu.csd.oop.db.cs73.Model.DatabaseImp;

public class Testing {

    public static void main(String[] args){
        /*String x = "CREATE TABLE Persons (PersonID int,LastName varchar(255),FirstName varchar(255),Address varchar(255),City varchar(255) )";
        //Create TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)
        // INSERT INTO table_name8(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4) INSERT INTO table_name8(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)
        //UPDATE table_name9 SET column_name1='value1', column_name2=15, column_name3='value2' UPDATE table_name9 SET column_name1='value1', column_name2=15, column_name3='value2'
        x = x.replaceAll("\\)", " ").replaceAll("\\(", " ");
        System.out.println(x);
        String [] z = x.split("\\s+|\\,\\s*|\\(|\\)");
        for(String v : z)
        	System.out.println(v);*/

        String q = "SELECT col1,col2,col3 FROM table";
        String[] qs = q.split(" ");
        for(String s : qs){
            System.out.println(s);
        }
    }

}

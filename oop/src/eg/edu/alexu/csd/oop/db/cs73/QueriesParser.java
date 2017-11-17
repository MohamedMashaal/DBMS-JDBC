package eg.edu.alexu.csd.oop.db.cs73;

import eg.edu.alexu.csd.oop.db.Database;

import java.io.File;
import java.sql.SQLException;

public class QueriesParser {
    Database database;
    File dataDirectory;

    public QueriesParser(){
        database = new DatabaseImp();
    }

    public boolean parseQuery(String query) throws SQLException {
        String[] splittedQuery = query.trim().split(" ");
        for(String s : splittedQuery){
            System.out.println("\"" + s + "\"");
        }
        if(splittedQuery[0].equalsIgnoreCase("create") && splittedQuery[0].equalsIgnoreCase("database")){
            //CREATE DATABASE databasename; or CREATE TABLE table_name (....);
            // TODO Check if exists
            database.createDatabase(splittedQuery[2],true); //this method will call createDatabase if necessary.
            return true; // successful query
        }

        if(splittedQuery[0].equalsIgnoreCase("create") && splittedQuery[0].equalsIgnoreCase("table")){
            //CREATE DATABASE databasename; or CREATE TABLE table_name (....);
            // TODO Check if exists
            database.executeStructureQuery(query); //this method will call createDatabase if necessary.
            return true; // successful query
        }

        if(splittedQuery[0].equalsIgnoreCase("select")){
            database.executeQuery(query);
            return true;
        }

        if(splittedQuery[0].equalsIgnoreCase("update") || splittedQuery[0].equalsIgnoreCase("delete")){
            database.executeUpdateQuery(query);
            return true;
        }

        return false; // invalid query
    }
}

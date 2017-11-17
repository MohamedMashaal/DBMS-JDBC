package eg.edu.alexu.csd.oop.db.cs73;

import eg.edu.alexu.csd.oop.db.Database;

import java.sql.SQLException;

public class QueriesParser {
    Database database = new DatabaseImp();

    public boolean parseQuery(String query) throws SQLException {
        String[] splittedQuery = query.trim().split(" ");
        if(splittedQuery[0].equalsIgnoreCase("create")){ //CREATE DATABASE databasename;
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

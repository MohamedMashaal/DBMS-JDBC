package eg.edu.alexu.csd.oop.db.cs73.Controller;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.commands.Create;
import eg.edu.alexu.csd.oop.db.commands.Delete;
import eg.edu.alexu.csd.oop.db.commands.Drop;
import eg.edu.alexu.csd.oop.db.commands.Insert;
import eg.edu.alexu.csd.oop.db.commands.Select;
import eg.edu.alexu.csd.oop.db.commands.Update;
import eg.edu.alexu.csd.oop.db.cs73.Model.DatabaseImp;
import eg.edu.alexu.csd.oop.db.cs73.Model.DirectoryHandler;

import java.sql.SQLException;

/*

    This class may be considered our controller; it takes submitted queries from the view (console in this case)
    and matches those queries with a function from the Database Interface.

 */

public class QueriesExecutor {
    SyntaxParser syntaxParser;
    Database dbManager;
    public QueriesExecutor(){
       syntaxParser = new SyntaxParser();
       dbManager = new DatabaseImp();
    }

    public String executeQuery(String query) throws SQLException {
        String[] splittedQuery = query.trim().split("\\s+");
        if(splittedQuery[0].equalsIgnoreCase("create")){
            if(new Create().syntaxParse(query)) {
            	boolean done = dbManager.executeStructureQuery(query);
            	if(done)
            		return new String(splittedQuery[1].toUpperCase() + " CREATED SUCCESSFULLY");
            	else
            		return new String(splittedQuery[1].toUpperCase() + " WASN'T CREATED SUCCESSFULLY");
            }
            return "Wrong Syntax" ;
        }
        else if (splittedQuery[0].equalsIgnoreCase("drop")) {
        	if(new Drop().syntaxParse(query)) {
        		boolean done = dbManager.executeStructureQuery(query);
            	if(done)
            		return new String(splittedQuery[1].toUpperCase() + " DROPPED SUCCESSFULLY");
            	else
            		return new String(splittedQuery[1].toUpperCase() + " WASN'T DROPPED SUCCESSFULLY");
            }
            return "Wrong Syntax" ;
        }
        
        else if(splittedQuery[0].equalsIgnoreCase("select")){
            if(new Select().syntaxParse(query)) {
            	Object [][] table = dbManager.executeQuery(query);
            	StringBuilder st = new StringBuilder();
            	for(int i = 0 ; i < table.length ; i ++) {
            		for(int j = 0 ; j < table[0].length ; j++) {
            			st.append(table[i][j].toString() + " ");
            		}
            		st.append("\n");
            	}
            	return st.toString();
            }
            return "Wrong Syntax" ;
        }
        
        else if(splittedQuery[0].equalsIgnoreCase("update")){
            if (new Update().syntaxParse(query)) {
            	return dbManager.executeUpdateQuery(query) + " ROWS HAS BEEN UPDATED";
            }
            return "Wrong Syntax" ;
        }
        else if(splittedQuery[0].equalsIgnoreCase("delete")){
        	if (new Delete().syntaxParse(query)) {
            	return dbManager.executeUpdateQuery(query) + " ROWS HAS BEEN UPDATED";
            }
        	return "Wrong Syntax" ;
        }
        else if(splittedQuery[0].equalsIgnoreCase("insert")){
        	if (new Insert().syntaxParse(query)) {
            	return dbManager.executeUpdateQuery(query) + " ROWS HAS BEEN UPDATED";
            }
        	return "Wrong Syntax" ;
        }
        else {
        	return "Un Supported Query";
        }
         
    }    
}

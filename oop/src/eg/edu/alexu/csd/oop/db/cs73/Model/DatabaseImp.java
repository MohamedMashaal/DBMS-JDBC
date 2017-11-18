package eg.edu.alexu.csd.oop.db.cs73.Model;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs73.Controller.QueriesParser;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.DBContainer;

import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseImp implements Database{
	
    QueriesParser queriesParser;
    ArrayList<DBContainer> data;
    DirectoryHandler dirHandler ;
    
    //public DatabaseImp() {}
    
    public DatabaseImp(){
        this.queriesParser = new QueriesParser(this);
        this.data = new ArrayList<>();
        this.dirHandler = new DirectoryHandler();
    }

    @Override
    public String createDatabase(String databaseName, boolean dropIfExists) {
        String query = "";
        if(dropIfExists){
            query = "DROP DATABASE " + databaseName;
            dirHandler.deleteDatabase(databaseName);
            query = "CREATE DATABASE " + databaseName;
            dirHandler.createDatabase(databaseName);
        }
        else{
            query = "CREATE DATABASE " + databaseName;
            dirHandler.createDatabase(databaseName);
        }
        try {
        	executeStructureQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return dirHandler.getPathOf(databaseName);
    }

    @Override
    public boolean executeStructureQuery(String query) throws SQLException {
    	String[] splittedQuery = query.split(" ");
    	if(splittedQuery[1].equalsIgnoreCase("database")) {
    		DBContainer dbc = new DBContainer(splittedQuery[2]);
    		if(splittedQuery[0].equalsIgnoreCase("create")) {
    			if(dbExists(splittedQuery[1])) {
    				dbc = data.get(dbIndex(splittedQuery[1]));
    				data.remove(dbc);
    			}
				data.add(dbc);    			
    		}
    		else if (splittedQuery[0].equalsIgnoreCase("drop")) {
    			if(dbExists(splittedQuery[1])) {
    				data.remove(dbIndex(splittedQuery[1]));
    			}
    		}
    	}
        return true;
    }

	@Override
    public Object[][] executeQuery(String query) throws SQLException {
    	throw new RuntimeException(query);
    	//return new Object[0][];
    }

    @Override
    public int executeUpdateQuery(String query) throws SQLException {
    	throw new RuntimeException(query);
    	//return 0;
    }
    
    private int dbIndex(String string) {
    	int i = 0 ;
    	for(DBContainer db : data) {
			if(db.getName().equals(string)) {	
				return i;
			}
			i++ ;
		}
		return -1;
	}

	private boolean dbExists(String string) {
		for(DBContainer db : data) {
			if(db.getName().equals(string)) {
				return true ;
			}
		}
		return false;
	}
    
}
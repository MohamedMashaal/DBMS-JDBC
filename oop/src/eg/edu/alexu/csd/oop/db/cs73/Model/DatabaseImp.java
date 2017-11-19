package eg.edu.alexu.csd.oop.db.cs73.Model;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs73.Controller.QueriesParser;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.DBContainer;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.Table;
import sun.net.www.content.text.plain;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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
            try {
				executeStructureQuery(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
            query = "CREATE DATABASE " + databaseName;
            try {
				executeStructureQuery(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        else{
            query = "CREATE DATABASE " + databaseName;
            try {
				executeStructureQuery(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        return dirHandler.getPathOf(databaseName);
    }

    @Override
    public boolean executeStructureQuery(String query) throws SQLException {
    	String[] splittedQuery = query.split("\\s|\\,\\s*|\\(|\\)");
    	if(splittedQuery[1].equalsIgnoreCase("database")) {
    		String databaseName = splittedQuery[2];
    		DBContainer dbc = new DBContainer(splittedQuery[2]);
    		if(splittedQuery[0].equalsIgnoreCase("create")) {
    			if(dbExists(databaseName)) {
    				dbc = data.get(dbIndex(databaseName));
    				data.remove(dbc);
    			}
				data.add(dbc);
				dirHandler.createDatabase(databaseName);
    		}
    		else if (splittedQuery[0].equalsIgnoreCase("drop")) {
    			if(dbExists(databaseName)) {
    				data.remove(dbIndex(databaseName));
    			}
    			dirHandler.deleteDatabase(databaseName);
    		}
    	}
    	else if (splittedQuery[1].equalsIgnoreCase("table")) {
    		String tableName = splittedQuery[2];
    		String [] columns = getColumns(splittedQuery);
    		if(splittedQuery[0].equalsIgnoreCase("create")) {
    			Table table = new Table(splittedQuery[2] ,columns);
    			if(data.get(data.size()-1).tableExists(tableName)) {
    				data.get(data.size()-1).remove(tableName);
    			}
				data.get(data.size()-1).add(table);
				dirHandler.createTable(tableName , data.get(data.size()-1).getName());
    		}
    		else if (splittedQuery[0].equalsIgnoreCase("drop")) {
    			if(data.get(data.size()-1).tableExists(tableName)) {
    				data.get(data.size()-1).remove(tableName);
    			}
    			dirHandler.deleteTable(tableName , data.get(data.size()-1).getName());
    		}
    	}
        return true;
    }

	@Override
    public Object[][] executeQuery(String query) throws SQLException {
    	//throw new RuntimeException(query);
    	return new Object[0][];
    }

    @Override
    public int executeUpdateQuery(String query) throws SQLException {
    	String [] splittedQuery = query.replaceAll("\\)", " ").replaceAll("\\(", " ").replaceAll("'", "").split("\\s+|\\,\\s*|\\(|\\)");
    	int updated = 0 ;
    	if(splittedQuery[0].equalsIgnoreCase("insert")) {
    		String [][] cloumnsValues = getColumnsValues(splittedQuery);
    		updated = data.get(data.size()-1).insert(splittedQuery[2] , Arrays.asList(cloumnsValues[0]) , Arrays.asList(cloumnsValues[1]));
    	}
    	return updated;
    }
    
    private String[][] getColumnsValues(String[] splittedQuery) {
		int length = 0;
		for(int i = 3 ; i < splittedQuery.length ; i ++) {
			if(splittedQuery[i].equalsIgnoreCase("values")) {
				break ;
			}
			length++;
		}
    	String [][] columnsValues = new String [2][length];
    	for(int i = 3 , j = i + length+1 , k = 0 ;j < splittedQuery.length; j++, i++ , k++) {
    		columnsValues[0][k] = splittedQuery[i];
    		columnsValues[1][k] = splittedQuery[j];
    	}
		return columnsValues;
	}

	private int dbIndex(String string) {
    	int i = 0 ;
    	for(DBContainer db : data) {
			if(db.getName().equalsIgnoreCase(string)) {	
				return i;
			}
			i++ ;
		}
		return -1;
	}

	private boolean dbExists(String string) {
		for(DBContainer db : data) {
			if(db.getName().equalsIgnoreCase(string)) {
				return true ;
			}
		}
		return false;
	}
	
	private String[] getColumns(String[] splittedQuery) {
		String [] columns = new String [splittedQuery.length-3];
		for(int i = 3 , j = 0 ; i < splittedQuery.length && j < columns.length ; i++,j++ ) {
			columns[j] = splittedQuery[i];
		}
		return columns;
	}
	
}
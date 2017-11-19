package eg.edu.alexu.csd.oop.db.cs73.Model;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs73.Controller.QueriesParser;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.Column;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.DBContainer;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.Record;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.Table;
import sun.net.www.content.text.plain;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseImp implements Database{
	
	private QueriesParser queriesParser;
	private ArrayList<DBContainer> data;
	private DirectoryHandler dirHandler;
	private InternalParser inParser;
   // boolean testing = false ;
    //public DatabaseImp() {}
    
    public DatabaseImp(){
        this.queriesParser = new QueriesParser(this);
        this.data = new ArrayList<>();
        this.dirHandler = new DirectoryHandler();
        this.inParser = new InternalParser();
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
    	String[] splittedQuery = query.replaceAll("\\)", " ").replaceAll("\\(", " ").replaceAll("'", "").replaceAll("\\s+\\,", ",").split("\\s+|\\,\\s*|\\(|\\)|\\=");
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
    		//String [] columns = getColumns(splittedQuery);
    		if(splittedQuery[0].equalsIgnoreCase("create")) {
    			Table table = new Table(splittedQuery[2] ,inParser.getColumns(splittedQuery));
    			if(data.get(data.size()-1).tableExists(tableName)) {
    				return false ;
    				//data.get(data.size()-1).remove(tableName);
    			}
				data.get(data.size()-1).add(table);
				//dirHandler.deleteTable(tableName, data.get(data.size()-1).getName());
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
		String [] splittedQuery = query.split(" ");
		String colName = splittedQuery[1];
		String tableName = splittedQuery[3];

		DBContainer currDB = data.get(data.size()-1);

		// check if table  exists
		if(! currDB.tableExists(tableName)){
			throw new RuntimeException("Table" + tableName +
					                   "is not exists in " + currDB.getName());
		}

		Table currTable = currDB.getTables().get(currDB.getTableIndex(tableName));

		// check if column exists in table
		int columnIndex = currTable.columnIndex(colName);
		if(columnIndex == -1 && !colName.equals("*")){
			throw  new RuntimeException("Column" + tableName +
					"is not exists in " + currTable.getName());
		}

		// fetching data part (with out applying "where" conditions)

		// one column
		if(!colName.equals("*")){
			Column queriedColumn = currTable.getColumns().get(columnIndex);
			Object[] fetchedData = queriedColumn.getData();

			if(queriedColumn.getType().equals("int")){
				Integer[] intColumn = (Integer[]) fetchedData;
				Object[][] retData = new Object[][]{intColumn};
				return applyWhere(retData, query, currTable);
			}
			else if(queriedColumn.getType().equals("varchar")){
				String[] varcharColumn = (String[]) fetchedData;
				Object[][] retData = new Object[][]{varcharColumn};
				return applyWhere(retData, query, currTable);
			}
		}
		// all columns
		else{
			ArrayList<Column> columns = currTable.getColumns();
			int maxRecords = (int) 1e6, i = 0;
			Object[][] fetchedData = new Object[currTable.getColumns().size()][maxRecords];
			for(Column column : columns){
				Object[] columnData = column.getData();

				if(column.getType().equals("int")){
					Integer[] intColumn = (Integer[]) columnData;
					fetchedData[i++] = intColumn;
				}
				else if(column.getType().equals("varchar")){
					String[] varcharColumn = (String[]) columnData;
					fetchedData[i++] = varcharColumn;
				}
			}
			return applyWhere(fetchedData, query, currTable);
		}

//		throw new RuntimeException(query);
    	return new Object[0][];
    }

    @Override
    public int executeUpdateQuery(String query) throws SQLException {
    	String [] splittedQuery = query.replaceAll("\\)", " ").replaceAll("\\(", " ").replaceAll("'", "").replaceAll("\\s+\\,", ",").split("\\s+|\\,\\s*|\\(|\\)|\\=");
    	int updated = 6 ;
    	if(splittedQuery[0].equalsIgnoreCase("insert")) {
    		String [][] cloumnsValues = inParser.getColumnsValues(splittedQuery);
    		if(data.get(data.size()-1).tableExists(splittedQuery[2]))
    			updated = data.get(data.size()-1).insert(splittedQuery[2] , Arrays.asList(cloumnsValues[0]) , Arrays.asList(cloumnsValues[1]));
    		else {
    			throw new SQLException();
    		}
    	}
    	else if (splittedQuery[0].equalsIgnoreCase("update")) {
    		ArrayList<ArrayList<String>> columnsValues = inParser.getUpdatedColumnsValues(splittedQuery);
    		ArrayList<String> toUpdate = inParser.getUpdateWhere(splittedQuery);
    		if(data.get(data.size()-1).tableExists(splittedQuery[1]))
    			updated = data.get(data.size()-1).update(splittedQuery[1] , columnsValues.get(0) , columnsValues.get(1));
    		else {
    			throw new SQLException();
    		}
    	}
    	return updated;
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
}
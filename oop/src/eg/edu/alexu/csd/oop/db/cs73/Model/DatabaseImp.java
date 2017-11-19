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
	
    QueriesParser queriesParser;
    ArrayList<DBContainer> data;
    DirectoryHandler dirHandler ;
   // boolean testing = false ;
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
    				//testing = true ;
    				return false ;
    				//data.get(data.size()-1).remove(tableName);
    			}
				data.get(data.size()-1).add(table);
				dirHandler.deleteTable(tableName, data.get(data.size()-1).getName());
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
    	String [] splittedQuery = query.replaceAll("\\)", " ").replaceAll("\\(", " ").replaceAll("'", "").split("\\s+|\\,\\s*|\\(|\\)");
    	int updated = 6 ;
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
    	for(int i = 3 , j = i + length+1 , k = 0 ;i-3 < length &&j < splittedQuery.length; j++, i++ , k++) {
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
	
	private String[] getColumns(String[] splittedQuery) throws SQLException {
		String [] columns = new String [splittedQuery.length-3];
		if(splittedQuery.length-3 == 0 && !splittedQuery[0].equalsIgnoreCase("drop")) {
			throw new SQLException("Wrong Create Query");
		}
		for(int i = 3 , j = 0 ; i < splittedQuery.length && j < columns.length ; i++,j++ ) {
			columns[j] = splittedQuery[i];
		}
		return columns;
	}

	private Object[][] applyWhere(Object[][] cols, String query, Table table) { // without the bonus (later)
		Object[][] filteredCols = new Object[cols.length][cols[0].length];
		int colIndex = 0;
		String[] splittedQuery = query.split(" ");
    	if(splittedQuery.length == 4) // there is no where condition
    		return cols;

    	String columnName = splittedQuery[5];
    	String operator = splittedQuery[6];
    	String comparedValue = splittedQuery[7];

		if(!table.columnExists(columnName)){
			throw new RuntimeException("Error in where clause; there is no such column: " + columnName);
		}

		Column comparedColumn = table.getColumns().get(table.columnIndex(columnName));

		if(comparedColumn.getType().equals("int")){
			try{
				int intValue = Integer.parseInt(comparedValue), i = 0;
				for(Object[] column : cols){
					ArrayList<Object> filteredRecords = new ArrayList<>();
					for(Object record : column){
						if(operator.equals("=")) {
							if((Integer)(comparedColumn.getRecords().get(i)) == intValue) {
								filteredRecords.add(record);
							}
						}
						if(operator.equals(">")){
							if((Integer)(comparedColumn.getRecords().get(i)) > intValue){
								filteredRecords.add(record);
							}
						}
						if(operator.equals("<")){
							if((Integer)(comparedColumn.getRecords().get(i)) < intValue){
								filteredRecords.add(record);
							}
						}
						i++;
					}
					filteredCols[colIndex++] = filteredRecords.toArray();
				}
			}
			catch(Exception e){
				throw new RuntimeException("Error! You are trying to compare non-integer with an integer.");
			}
		}

		if(comparedColumn.getType().equals("varchar")){
			int i = 0;
			for(Object[] column : cols){
				ArrayList<Object> filteredRecords = new ArrayList<>();
				for(Object record : column){
					Record<String> castedRecord = (Record<String>) comparedColumn.getRecords().get(i);
					int comparingVal = castedRecord.getValue().compareTo(comparedValue);
					if(operator.equals("=")) {
						if(comparingVal == 0) {
							filteredRecords.add(record);
						}
					}
					if(operator.equals(">")){
						if(comparingVal == 1){
							filteredRecords.add(record);
						}
					}
					if(operator.equals("<")){
						if(comparingVal == -1){
							filteredRecords.add(record);
						}
					}
					i++;
				}
				filteredCols[colIndex++] = filteredRecords.toArray();
			}

		}

		return filteredCols;
	}
}
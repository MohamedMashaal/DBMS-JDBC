package eg.edu.alexu.csd.oop.db.cs73.Model;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs73.Controller.QueriesExecutor;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.Column;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.DBContainer;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.Record;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.Table;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseImp implements Database{
	private static DatabaseImp uniqueInstance = new DatabaseImp();
	private ArrayList<DBContainer> data;
	private DirectoryHandler dirHandler;
	private ExtractingHandler extractor;
	private ConditionHandler conditionHandler;
	private XMLParser xmlParser;
    
    private DatabaseImp(){
        this.data = new ArrayList<>();
        this.dirHandler = new DirectoryHandler();
        this.extractor = new ExtractingHandler();
        this.conditionHandler = new ConditionHandler();
        this.xmlParser = new XMLParser();
	}
    
    public static Database getUniqueInstance() {
    	return uniqueInstance;
    }
    
    @Override
    public String createDatabase(String databaseName, boolean dropIfExists) {
        databaseName = databaseName.toLowerCase();
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
    		String databaseName = splittedQuery[2].toLowerCase();
    		DBContainer dbc = new DBContainer(databaseName);
    		if(splittedQuery[0].equalsIgnoreCase("create")) {
    			if(dirHandler.dbExists(databaseName)) {
    				int dbIndex = dbIndex(databaseName);
    				if(dbIndex != -1){
						dbc = data.get(dbIndex(databaseName));
						data.remove(dbc);
					}
    				dbc = dirHandler.loadDB(databaseName);
    			}
				dirHandler.createDatabase(databaseName);
				data.add(dbc);
			}
    		else if (splittedQuery[0].equalsIgnoreCase("drop")) {
    			if(dirHandler.dbExists(databaseName)) {
    				int dbIndex = dbIndex(databaseName);
    				if(dbIndex != -1){
						data.remove(dbIndex);
					}
    			}
    			dirHandler.deleteDatabase(databaseName);
    		}
    	}
    	else if (splittedQuery[1].equalsIgnoreCase("table")) {
    		String tableName = splittedQuery[2].toLowerCase();
    		if(splittedQuery[0].equalsIgnoreCase("create")) {
    			Table table = new Table(tableName ,extractor.getColumnsTypes(splittedQuery));
    			if(data.get(data.size()-1).tableExists(tableName)) {
    				return false ;
    			}
				data.get(data.size()-1).add(table);
				dirHandler.createTable(tableName , data.get(data.size()-1).getName());
				String tablePath = dirHandler.getPathOf(tableName , data.get(data.size()-1).getName());
				try {
					xmlParser.saveTableToXML(tablePath, table);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
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
		String [] splittedQuery = query.replaceAll("\\)", " ").replaceAll("\\(", " ")
				.replaceAll("\\s+\\,", ",").replaceAll("\\s*\"\\s*","\"")
				.replaceAll("\\s*'\\s*","'").replaceAll("=", " = ")
				.split("\\s+|\\(|\\)");
		String colName = splittedQuery[1];
		String tableName = splittedQuery[3];

		DBContainer currDB = data.get(data.size()-1);

		// check if table  exists
		if(! currDB.tableExists(tableName)){
			throw new RuntimeException("Table " + tableName +
					" is not exists in " + currDB.getName());
		}

		Table currTable = currDB.getTables().get(currDB.getTableIndex(tableName));

		// fetching data part (with out applying "where" conditions)

		// all columns
		if(colName.equals("*")){
			/*Column queriedColumn = currTable.getColumns().get(columnIndex);
			Object[] fetchedData = queriedColumn.getData();

			if(queriedColumn.getType().equals("int")){
				//Integer[] intColumn = (Integer[]) fetchedData;
				Object[][] retData = new Object[][]{fetchedData};
				return applyWhere(retData, query, currTable);
			}
			else if(queriedColumn.getType().equals("varchar")){
				//String[] varcharColumn = (String[]) fetchedData;
				Object[][] retData = new Object[][]{fetchedData};
				return applyWhere(retData, query, currTable);
			}*/
			ArrayList<Column> columns = currTable.getColumns();
			int i = 0;
			Object[][] fetchedData = new Object[columns.size()][];
			for(Column column : columns){
				Object[] columnData = column.getData();

				/*if(column.getType().equals("int")){
					Integer[] intColumn = (Integer[]) columnData;
					fetchedData[i++] = intColumn;
				}
				else if(column.getType().equals("varchar")){
					String[] varcharColumn = (String[]) columnData;
					fetchedData[i++] = varcharColumn;
				}*/
				fetchedData[i++] = columnData;
			}
			return applyWhere(fetchedData, query, currTable);
		}

		// more than one column
		if(colName.contains(",")){
			String[] columnsName = colName.split("\\s*,\\s*");

			ArrayList<Column> columns = currTable.getColumns();
			int i = 0;
			Object[][] fetchedData = new Object[columnsName.length][];
			for(Column column : columns){
				boolean ok = false;
				for(String oneCol : columnsName){
					if(oneCol.equals(column.getName())){
						ok = true;
						break;
					}
				}
				if(!ok){
					continue;
				}

				Object[] columnData = column.getData();

				/*if(column.getType().equals("int")){
					Integer[] intColumn = (Integer[]) columnData;
					fetchedData[i++] = intColumn;
				}
				else if(column.getType().equals("varchar")){
					String[] varcharColumn = (String[]) columnData;
					fetchedData[i++] = varcharColumn;
				}*/
				fetchedData[i++] = columnData;
			}
			return applyWhere(fetchedData, query, currTable);
		}

		// one column
		else{
			// check if column exists in table
			int columnIndex = currTable.columnIndex(colName);
			if(columnIndex == -1 && !colName.equals("*")){
				throw  new RuntimeException("Column " + colName +
						" is not exists in " + currTable.getName());
			}
			/*ArrayList<Column> columns = currTable.getColumns();
			int i = 0;
			Object[][] fetchedData = new Object[currTable.getColumns().size()][];
			for(Column column : columns){
				Object[] columnData = column.getData();

				/*if(column.getType().equals("int")){
					Integer[] intColumn = (Integer[]) columnData;
					fetchedData[i++] = intColumn;
				}
				else if(column.getType().equals("varchar")){
					String[] varcharColumn = (String[]) columnData;
					fetchedData[i++] = varcharColumn;
				}*/
				/*fetchedData[i++] = columnData;
			}
			return applyWhere(fetchedData, query, currTable);*/
			Column queriedColumn = currTable.getColumns().get(columnIndex);
			Object[] fetchedData = queriedColumn.getData();

			if(queriedColumn.getType().equals("int")){
				//Integer[] intColumn = (Integer[]) fetchedData;
				Object[][] retData = new Object[][]{fetchedData};
				return applyWhere(retData, query, currTable);
			}
			else if(queriedColumn.getType().equals("varchar")){
				//String[] varcharColumn = (String[]) fetchedData;
				Object[][] retData = new Object[][]{fetchedData};
				return applyWhere(retData, query, currTable);
			}
		}

//		throw new RuntimeException(query);
		return new Object[0][];
    }

    @Override
    public int executeUpdateQuery(String query) throws SQLException {
    	String [] splittedQuery = query.replaceAll("\\)", " ").replaceAll("\\(", " ").replaceAll("'", "").replaceAll("=", " = ").replaceAll("\\s+\\,", ",").split("\\s+|\\,\\s*|\\(|\\)");
    	//String [] splittedQuery = query.replaceAll("\\)", " ").replaceAll("\\(", " ").replaceAll("=", " = ").replaceAll("\\s+\\,", ",").split("\\s+(?=(?:[^\']*\'[^\']*\')*[^\']*$)|\\,\\s*|\\(|\\)");
    	//splittedQuery = extractor.filterQuotes(splittedQuery);
    	int updated = 0 ;
    	String tableName = null ;
    	if(splittedQuery[0].equalsIgnoreCase("insert")) {
    		String [][] cloumnsValues = extractor.getColumnsValues(splittedQuery);
    		if(data.get(data.size()-1).tableExists(splittedQuery[2])) {
    			updated = data.get(data.size()-1).insert(splittedQuery[2] , Arrays.asList(cloumnsValues[0]) , Arrays.asList(cloumnsValues[1]));
    			tableName = splittedQuery[2];
    		}
    		else {
    			throw new SQLException();
    		}
    	}
    	else if (splittedQuery[0].equalsIgnoreCase("update")) {
    		ArrayList<ArrayList<String>> columnsValues = extractor.getUpdatedColumnsValues(splittedQuery);
    		ArrayList<String> toUpdate = extractor.getWhere(splittedQuery);
    		if(data.get(data.size()-1).tableExists(splittedQuery[1])) {
    			updated = data.get(data.size()-1).update(splittedQuery[1] , columnsValues.get(0) , columnsValues.get(1),toUpdate);
    			tableName = splittedQuery[1];
    		}
    		else {
    			throw new SQLException();
    		}
    	}
    	else if (splittedQuery[0].equalsIgnoreCase("delete")) {
    		ArrayList<String> toUpdate = extractor.getWhere(splittedQuery);
    		String table = splittedQuery[1].equalsIgnoreCase("from") ? splittedQuery[2] : splittedQuery[3];
    		if(data.get(data.size()-1).tableExists(table)) {
    			updated = data.get(data.size()-1).delete(table , toUpdate);
    			tableName = table;
    		}
    		else {
    			throw new SQLException();
    		}
    	}
    	if(tableName != null) {
    		String tablePath = dirHandler.getPathOf(tableName , data.get(data.size()-1).getName());
    		try {
    			int currTableIndex = data.get(data.size()-1).getTableIndex(tableName);
    			Table currTable = data.get(data.size()-1).getTables().get(currTableIndex);
    			xmlParser.saveTableToXML(tablePath, currTable);
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
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

	private Object[][] applyWhere(Object[][] cols, String query, Table table) { // without the bonus (later)
		Object[][] filteredCols = new Object[cols.length][cols[0].length];
		int colIndex = 0;
		String[] splittedQuery = query.split(" ");
		if(splittedQuery.length == 4) // there is no where condition
			return inverse(cols);
			//return cols;

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
					i = 0 ;
					for(Object record : column){
						Record x = comparedColumn.getRecord(i);
						Integer comparedColumnRecord = (Integer)x.getValue();
						if(operator.equals("=")) {
							if(comparedColumnRecord.intValue() == intValue) {
								filteredRecords.add(record);
							}
						}
						else if(operator.equals(">")){
							if(comparedColumnRecord.intValue() > intValue){
								filteredRecords.add(record);
							}
						}
						else if(operator.equals("<")){
							if(comparedColumnRecord.intValue() < intValue){
								filteredRecords.add(record);
							}
						}
						i++;
					}
					filteredCols[colIndex++] = filteredRecords.toArray();
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}

		if(comparedColumn.getType().equals("varchar")){
			int i = 0;
			colIndex = 0;
			for(Object[] column : cols){
				ArrayList<Object> filteredRecords = new ArrayList<>();
				i = 0 ;
				for(Object record : column){
					Record<String> castedRecord = (Record<String>) comparedColumn.getRecords().get(i);
					int comparingVal = castedRecord.getValue().compareTo(comparedValue);
					if(operator.equals("=")) {
						if(comparingVal == 0) {
							filteredRecords.add(record);
						}
					}
					else if(operator.equals(">")){
						if(comparingVal == 1){
							filteredRecords.add(record);
						}
					}
					else if(operator.equals("<")){
						if(comparingVal == -1){
							filteredRecords.add(record);
						}
					}
					i++;
				}
				filteredCols[colIndex++] = filteredRecords.toArray();
			}

		}

		return inverse(filteredCols);
		//return filteredCols;
	}

	private Object[][] inverse(Object[][] cols) {
		Object[][] newArray = new Object[cols[0].length][cols.length] ;
		for(int i = 0 ; i < cols.length ; i++) {
			for(int j = 0 ; j< cols[0].length ; j++) {
				newArray[j][i] = cols[i][j];
			}
		}
		return newArray;
	}
}
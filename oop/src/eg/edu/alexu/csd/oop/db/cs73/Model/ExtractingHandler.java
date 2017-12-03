package eg.edu.alexu.csd.oop.db.cs73.Model;

import java.sql.SQLException;
import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.Column;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.DBContainer;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.Table;

public class ExtractingHandler {
	public String[][] getColumnsValues(String[] splittedQuery) throws SQLException {
		int length = 0;
		for(int i = 3 ; i < splittedQuery.length ; i ++) {
			if(splittedQuery[i].equalsIgnoreCase("values")) {
				break ;
			}
			length++;
		}
		String [][] columnsValues= null;
		if(length != 0) {
			columnsValues = new String [2][length];
    	for(int k = 0 , i = 3 , j = i + length + 1; k < length; j++ , i++ , k++) {
    		columnsValues[0][k] = splittedQuery[i];
    		columnsValues[1][k] = splittedQuery[j];
    	}}
    	else {
    		columnsValues = new String [][] {new String [0] , new String[splittedQuery.length-4]};
    		for(int i = 4 , j = 0 ; i < splittedQuery.length ;j++ , i++) {
    			columnsValues[1][j] = splittedQuery[i];
    		}
    	}
		return columnsValues;
	}
	
	public String[] getColumnsTypes(String[] splittedQuery) throws SQLException {
		String [] columns = new String [splittedQuery.length-3];
		if(splittedQuery.length-3 == 0 && !splittedQuery[0].equalsIgnoreCase("drop")) {
			throw new SQLException("Wrong Create Query");
		}
		for(int i = 3 , j = 0 ; i < splittedQuery.length && j < columns.length ; i++,j++ ) {
			columns[j] = splittedQuery[i];
		}
		return columns;
	}
	
	public ArrayList<ArrayList<String>> getUpdatedColumnsValues(String[] splittedQuery){
			ArrayList<ArrayList<String>> columnsValues = new ArrayList<ArrayList<String>>();
			columnsValues.add(new ArrayList<String>());
			columnsValues.add(new ArrayList<String>());
			String [] filteredSplittedQuery = filterQueyColVal(splittedQuery).toArray(new String [0]);
			int length = getWhereIndex(filteredSplittedQuery) == -1 ? filteredSplittedQuery.length : getWhereIndex(filteredSplittedQuery) ;
			for(int i = 3 ; i < length ; i+=2) {
				columnsValues.get(0).add(filteredSplittedQuery[i]);
				columnsValues.get(1).add(filteredSplittedQuery[i+1]);
			}
			return columnsValues;
    }
	
	private ArrayList<String> filterQueyColVal(String[] splittedQuery) {
		ArrayList<String> filtered = new ArrayList<>();
		for(String x : splittedQuery) {
			if(!x.equalsIgnoreCase("=")) {
				filtered.add(x);
			}
		}
		return filtered;
	}

	private int getWhereIndex(String [] splittedQuery) {
		int i = 0;
		for(String x : splittedQuery) {
			if(x.equalsIgnoreCase("where")) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public ArrayList<String> getWhere(String[] splittedQuery) {
		int whereIndex = getWhereIndex(splittedQuery);
		ArrayList<String> whereValue = new ArrayList<>();
		if(whereIndex != -1)
		for(int i = whereIndex+1 ; i < splittedQuery.length ; i ++) {
			whereValue.add(splittedQuery[i]);
		}
		return whereValue;
	}

	public String[] filterQuotes(String[] splittedQuery) {
		ArrayList<String> filtered = new ArrayList<>();
		for(String x : splittedQuery) {
			if(x.charAt(0) == '\'') {
				filtered.add(x.substring(1, x.length()-1));
			}
			else {
				filtered.add(x);
			}
		}
		return filtered.toArray(new String [0]);
	}
	
	public String[][] getColumnsInfoSelect(DBContainer currDB, String query) {
		String[][] columnsInfo;
		String [] splittedQuery = query.replaceAll("\\)", " ").replaceAll("\\(", " ")
				.replaceAll("\\s+\\,", ",").replaceAll("\\s*\"\\s*","\"")
				.replaceAll("\\s*'\\s*","'").replaceAll("=", " = ")
				.split("\\s+|\\(|\\)");
		String colName = splittedQuery[1];
		String tableName = splittedQuery[3];

		Table currTable = currDB.getTables().get(currDB.getTableIndex(tableName));

		// all columns
		if(colName.equals("*")){
			ArrayList<Column> columns = currTable.getColumns();
			columnsInfo = new String[columns.size()][2];
			int i = 0;
			for(Column column : columns){
				columnsInfo[i][0] = column.getName();
				columnsInfo[i][1] = column.getType();
				i++;
			}
			return columnsInfo;
		}

		// more than one column
		if(colName.contains(",")){
			String[] columnsName = colName.split("\\s*,\\s*");
			ArrayList<Column> columns = currTable.getColumns();
			columnsInfo = new String[columns.size()][2];
			int i = 0;

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
				columnsInfo[i][0] = column.getName();
				columnsInfo[i][1] = column.getType();
				i++;
			}
			return columnsInfo;
		}

		// one column
		else{
			// check if column exists in table
			int columnIndex = currTable.columnIndex(colName);
			if(columnIndex == -1 && !colName.equals("*")){
				throw  new RuntimeException("Column " + colName +
						" is not exists in " + currTable.getName());
			}
			Column queriedColumn = currTable.getColumns().get(columnIndex);
			columnsInfo = new String[1][2];
			columnsInfo[0][0] = queriedColumn.getName();
			columnsInfo[0][1] = queriedColumn.getType();
			return columnsInfo;
		}
	}
}
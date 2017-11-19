package eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects;

import java.util.ArrayList;
import java.util.List;

public class Table {
	String name ;
    ArrayList<Column> columns;

    public Table(String name){
    	this.name = name ;
        this.columns = new ArrayList<>();
    }
    
    public Table(String name , String [] columns) {
    	this.name = name ;
    	this.columns = new ArrayList<>();
    	addColumns(columns);
    }
    
    public void addColumns(String [] columns) {
    	for(int i = 0 ; i < columns.length ; i+=2) {
    		if(columns[i+1].equalsIgnoreCase("int")) {
    			this.columns.add(new Column<Integer>(columns[i])); 
    		}
    		else if (columns[i+1].equalsIgnoreCase("varchar")) {
    			this.columns.add(new Column<String>(columns[i]));
    		}
    	}
    }
    
    public String getName() {
		return name;
	}

	public ArrayList<Column> getColumns() {
		return columns;
	}

	public void insert(List<String> columns, List<String> values) {
		for(Column column : this.columns) {
			int index = getIndex(columns , column.getName());
			if(index == -1) {
				column.addRecord(null);
			}
			else {
				column.addRecord(new Record<>(values.get(index)));
			}
		}
	}

	private int getIndex(List<String> columns, String name) {
		for(int i = 0 ; i < columns.size()-1 ; i++) {
			if(columns.get(i).equalsIgnoreCase(name)) {
				return i ;
			}
		}
		return -1;
	}
}

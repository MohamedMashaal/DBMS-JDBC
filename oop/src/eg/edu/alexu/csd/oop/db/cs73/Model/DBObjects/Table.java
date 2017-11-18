package eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects;

import java.util.ArrayList;

public class Table {
	String name ;
    ArrayList<Column> columns;

    public Table(String name){
    	this.name = name ;
        this.columns = new ArrayList<>();
    }
    
    public Table(String name , String [] columns) {
    	this.name = name ;
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
}

package eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects;

import java.util.ArrayList;

public class Table {
	String name ;
    ArrayList<Column> columns;

    public Table(String name){
    	this.name = name ;
        this.columns = new ArrayList<>();
    }
    
    public String getName() {
		return name;
	}
}

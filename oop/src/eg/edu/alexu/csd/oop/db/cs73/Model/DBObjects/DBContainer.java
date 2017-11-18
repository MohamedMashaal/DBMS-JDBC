package eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects;

import java.util.ArrayList;

public class DBContainer {
	String name;
    ArrayList<Table> tables;

    public DBContainer(String name){
        this.tables = new ArrayList<>();
        this.name = name;
    }

	public String getName() {
		return name;
	}
	
	public void remove(String tableName) {
		for(Table table :tables) {
			if(table.getName().equalsIgnoreCase(tableName)) {
				tables.remove(table);
			}
		}
	}
	
	public void add(Table table) {
		tables.add(table);
	}

	public boolean tableExists(String tableName) {
		for(Table table : tables) {
			if(table.getName().equalsIgnoreCase(tableName))
				return true ;
		}
		return false;
	}
	
}

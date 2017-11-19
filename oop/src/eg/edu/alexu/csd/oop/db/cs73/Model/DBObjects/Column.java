package eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects;

import java.util.ArrayList;

public class Column <T> {
	String name ;
	String type;
    ArrayList<Record<T>> records;

    public Column(String name, String type){
    	this.name = name ;
        this.records = new ArrayList<>();
    }
    
    public String getName() {
		return name;
	}

    public String getType() {
        return type;
    }

    public void addRecord(Record<T> record) {
    	records.add(record);
    }

    public ArrayList<Record<T>> getRecords() {
        return records;
    }
}

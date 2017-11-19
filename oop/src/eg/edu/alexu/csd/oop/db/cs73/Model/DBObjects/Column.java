package eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects;

import java.util.ArrayList;

public class Column <T> {
	private String name ;
	private String type;
	private ArrayList<Record<T>> records;

    public Column(String name, String type){
    	this.type = type ;
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

    public T[] getData() {
        ArrayList<T> colData = new ArrayList<>();
        for(Record<T> record : records){
            colData.add(record.getValue());
        }
        return (T[]) colData.toArray();
    }
}

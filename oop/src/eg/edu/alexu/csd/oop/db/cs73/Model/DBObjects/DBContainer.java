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
}

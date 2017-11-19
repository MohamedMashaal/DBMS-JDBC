package eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects;

import java.util.ArrayList;

public class Column {

    ArrayList<Table> records;

    public Column(){
        this.records = new ArrayList<>();
    }

    public ArrayList<Table> getRecords() {
        return records;
    }
}

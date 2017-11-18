package eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects;

import java.util.ArrayList;

public class Column <T> {

    ArrayList<Record<T>> records;

    public Column(){
        this.records = new ArrayList<>();
    }
}

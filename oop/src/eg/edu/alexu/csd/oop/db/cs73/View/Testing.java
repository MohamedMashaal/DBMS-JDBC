package eg.edu.alexu.csd.oop.db.cs73.View;

import eg.edu.alexu.csd.oop.db.Database;

/*

    This class is just for testing purposes

 */

import eg.edu.alexu.csd.oop.db.cs73.Model.DatabaseImp;

public class Testing {

    public static void main(String[] args){
        Database db = new DatabaseImp();
        db.createDatabase("7amada", true);
    }

}

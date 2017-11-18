package eg.edu.alexu.csd.oop.db.cs73.View;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import eg.edu.alexu.csd.oop.db.Database;

/*

    This class is just for testing purposes

 */

import eg.edu.alexu.csd.oop.db.cs73.Controller.QueriesParser;
import eg.edu.alexu.csd.oop.db.cs73.Model.DatabaseImp;

public class Testing {

    public static void main(String[] args){
        Database db = new DatabaseImp();
        db.createDatabase("7amada", true);
        File f = new File("okk.txt");
        f.delete();
    }

}

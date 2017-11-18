package eg.edu.alexu.csd.oop.db.cs73.Model;

/*

    This class handles the main directory of databases:
        Checks if a newly created database already exists
        Creates new databases
        Creates new tables

 */

import java.io.File;
import java.io.IOException;

public class DirectoryHandler {

    File mainDirectory;

    public DirectoryHandler(){
        // TODO Finish It
        mainDirectory = new File("~/oop/Data/readme.txt");
        mainDirectory.getParentFile().mkdirs();
        try {
            mainDirectory.getParentFile().createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(mainDirectory.getPath());
        System.out.println(mainDirectory.isDirectory());
    }

    public boolean exists(){
        // TODO Complete It
        for(File dir : mainDirectory.listFiles()){

        }
        return false;
    }

    public boolean deleteDatabase(String databaseName){
        // TODO
        return true;
    }


    public String getPathOf(String databaseName) {
        // TODO
        return null;
    }
}

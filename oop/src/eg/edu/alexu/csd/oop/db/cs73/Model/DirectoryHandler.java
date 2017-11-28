package eg.edu.alexu.csd.oop.db.cs73.Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.DBContainer;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.Table;

public class DirectoryHandler {

    File mainDirectory;
    XMLParser xmlParser;

    public DirectoryHandler(){
        mainDirectory = new File("data");
        mainDirectory.mkdirs();
        xmlParser = new XMLParser();
    }

    public void deleteDatabase(String databaseName){
    	File dir = new File(mainDirectory.getAbsolutePath() + System.getProperty("file.separator") + databaseName);
    	deleteDir(dir);
    }

    private void deleteDir(File dir) {
    	File[] files = dir.listFiles();
        if(files!=null) {
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteDir(f);
                } else {
                    f.delete();
                }
            }
        }
        dir.delete();
	}

	public String getPathOf(String databaseName) {
    	File dataFile = new File(mainDirectory.getAbsolutePath() + System.getProperty("file.separator") + databaseName);
        return dataFile.getAbsolutePath();
    }

    public String getPathOf(String tableName, String databaseName) {
        File tableFile = new File(mainDirectory.getAbsolutePath() + System.getProperty("file.separator") + databaseName
        + System.getProperty("file.separator") + tableName + ".xml");
        return tableFile.getAbsolutePath();
    }
    
	public void createDatabase(String databaseName) {
		File dataFile = new File(mainDirectory.getAbsolutePath() + System.getProperty("file.separator") + databaseName.toLowerCase());
        dataFile.mkdirs();
	}

	public void createTable(String tableName , String databaseName) {
		//Just for now
		File table = new File(mainDirectory.getAbsolutePath() + System.getProperty("file.separator") + databaseName +System.getProperty("file.separator")+ tableName +".xml");
		try {
			table.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteTable(String tableName, String databaseName) {
		File table = new File(mainDirectory.getAbsolutePath() + System.getProperty("file.separator") + databaseName +System.getProperty("file.separator")+ tableName +".xml");
		table.delete();
	}

	public ArrayList<DBContainer> loadAllDBs(){
        ArrayList<DBContainer> allDBs = new ArrayList<>();
        for(File dbFile : mainDirectory.listFiles()){
            DBContainer dbObj = new DBContainer(dbFile.getName());

            for(File tableFile : dbFile.listFiles()){
                Table tableObj = xmlParser.loadTableFromXML(tableFile.getAbsolutePath());
                dbObj.getTables().add(tableObj);
            }

            allDBs.add(dbObj);
        }

        return allDBs;
    }

    public boolean dbExists(String databaseName) {
        for(File dir : mainDirectory.listFiles()){
            if(dir.getName().equalsIgnoreCase(databaseName))
            {
                return true;
            }
        }
        //return new File(getPathOf(databaseName)).exists();
        return false;
    }

    public DBContainer loadDB(String databaseName) {
        for(File dbFile : mainDirectory.listFiles()){
            if(dbFile.getName().equalsIgnoreCase(databaseName)){
                DBContainer dbObj = new DBContainer(dbFile.getName());
                for(File tableFile : dbFile.listFiles()){
                    if(!tableFile.getAbsolutePath().substring(tableFile.getAbsolutePath().length()-3).equalsIgnoreCase("xml")){
                        continue;
                    }
                    Table tableObj = xmlParser.loadTableFromXML(tableFile.getAbsolutePath());
                    //Table tableObj = xmlParser.loadTableFromXML(getPathOf(tableFile.getName(), dbFile.getName()));
                    if(tableObj != null)
                        dbObj.getTables().add(tableObj);
                }
                return dbObj;
            }
        }
        return null;
    }
}

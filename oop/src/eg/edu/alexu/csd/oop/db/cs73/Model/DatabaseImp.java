package eg.edu.alexu.csd.oop.db.cs73.Model;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs73.Controller.QueriesParser;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.DBContainer;

import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseImp implements Database{

    QueriesParser queriesParser;
    ArrayList<DBContainer> data;
    
    public DatabaseImp() {}
    
    public DatabaseImp(QueriesParser queriesParser){
        this.queriesParser = queriesParser;
        this.data = new ArrayList<>();
    }

    @Override
    public String createDatabase(String databaseName, boolean dropIfExists) {
        String query = "";
        if(dropIfExists){
            query = "DROP DATABASE " + databaseName +";";
        }
        else{
            query = "CREATE DATABASE " + databaseName +";";
        }
        try {
            executeStructureQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //return queriesParser.getDirectoryHandler().getPathOf(databaseName);
        throw new RuntimeException(databaseName);
    }

    @Override
    public boolean executeStructureQuery(String query) throws SQLException {
        return true;
    }

    @Override
    public Object[][] executeQuery(String query) throws SQLException {
        return new Object[0][];
    }

    @Override
    public int executeUpdateQuery(String query) throws SQLException {
        return 0;
    }
}
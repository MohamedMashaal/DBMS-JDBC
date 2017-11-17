package eg.edu.alexu.csd.oop.db.cs73;

import eg.edu.alexu.csd.oop.db.Database;
import java.sql.SQLException;

public class DatabaseImp implements Database{

    QueriesParser queriesParser;

    public DatabaseImp(QueriesParser queriesParser){
        this.queriesParser = queriesParser;
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

        return queriesParser.getDirectoryHandler().getPathOf(databaseName);
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
package eg.edu.alexu.csd.oop.db.cs73;

import eg.edu.alexu.csd.oop.db.Database;
import java.sql.SQLException;

public class DatabaseImp implements Database{

    @Override
    public String createDatabase(String databaseName, boolean dropIfExists) {
        return null;
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
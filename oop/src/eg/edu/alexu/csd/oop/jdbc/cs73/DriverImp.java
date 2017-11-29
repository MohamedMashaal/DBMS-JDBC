package eg.edu.alexu.csd.oop.jdbc.cs73;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;


public class DriverImp implements Driver {
    private Properties properties ;
	@Override
    public Connection connect(String s, Properties properties) throws SQLException {
    	this.properties = properties;
    	StringBuilder builder = new StringBuilder();
    	for(java.util.Map.Entry<Object, Object> x : properties.entrySet()) {
    		builder.append(x.getKey() + "  : " + x.getValue());
    	}
    	throw new RuntimeException(builder.toString());
		//return new ConnectionImp();
    }

    @Override
    public boolean acceptsURL(String s) throws SQLException {
    	return true ;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String s, Properties properties) throws SQLException {
    	throw new UnsupportedOperationException();
    }

    @Override
    public int getMajorVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMinorVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean jdbcCompliant() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException();
    }
}

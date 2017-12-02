package eg.edu.alexu.csd.oop.jdbc.cs73;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class DriverImp implements Driver {
	private Properties properties;

	@Override
	public Connection connect(String s, Properties properties) throws SQLException {
		this.properties = properties;
		DBLogger.getInstance().log.info("Attempting to connect...");
		File dir = null;
		if (properties.contains("path") && !properties.getProperty("path").equalsIgnoreCase("")) {
			dir = (File) properties.get("path");
			String path = dir.getAbsolutePath();
			DBLogger.getInstance().log.info("Connecting to specified path...");
			return new ConnectionImp(path);
		}
		return new ConnectionImp("");
	}

	@Override
	public boolean acceptsURL(String s) throws SQLException {
		DBLogger.getInstance().log.info("Access to " + s + "has no security.");
		return true;
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
		return DBLogger.getInstance().log;
	}
}
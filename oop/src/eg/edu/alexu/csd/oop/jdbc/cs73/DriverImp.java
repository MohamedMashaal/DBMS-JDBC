package eg.edu.alexu.csd.oop.jdbc.cs73;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Logger;

public class DriverImp implements Driver {
	@Override
	public boolean acceptsURL(final String s) throws SQLException {
		DBLogger.getInstance().log.info("Access to " + s + "has no security.");
		return true;
	}

	@Override
	public Connection connect(final String s, final Properties properties) throws SQLException {
		DBLogger.getInstance().log.info("Attempting to connect...");
		File dir = null;
		if (properties.containsKey("path") && !(properties.get("path") == null)) {
			dir = (File) properties.get("path");
			final String path = dir.getAbsolutePath();
			DBLogger.getInstance().log.info("Connecting to specified path...");
			return new ConnectionImp(path);
		}
		return new ConnectionImp("");
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
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return DBLogger.getInstance().log;
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(final String s, final Properties properties) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean jdbcCompliant() {
		throw new UnsupportedOperationException();
	}
}
package eg.edu.alexu.csd.oop.jdbc.cs60;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class ConnectionImp implements Connection {
	private String path;
	private boolean closed;

	public ConnectionImp() {
		closed = false;
		DBLogger.getInstance().log.info("Connection established.");
	}

	public ConnectionImp(final String path) {
		this.path = path;
	}

	@Override
	public void abort(final Executor executor) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void close() throws SQLException {
		if (closed) {
			throw new SQLException();
		}
		DBLogger.getInstance().log.warning("Closing connection.");
		closed = true;
	}

	@Override
	public void commit() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Array createArrayOf(final String s, final Object[] objects) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Blob createBlob() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Clob createClob() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public NClob createNClob() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Statement createStatement() throws SQLException {
		DBLogger.getInstance().log.info("Generating statement..");
		if (closed) {
			throw new SQLException();
		}
		if (path == null || path.equalsIgnoreCase("")) {
			return new StatementImp(this);
		}
		return new StatementImp(path, this);
	}

	@Override
	public Statement createStatement(final int i, final int i1) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Statement createStatement(final int i, final int i1, final int i2) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Struct createStruct(final String s, final Object[] objects) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCatalog() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getClientInfo(final String s) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getHoldability() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSchema() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isClosed() throws SQLException {
		DBLogger.getInstance().log.info("Checking connection status..");
		return closed;
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isValid(final int i) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isWrapperFor(final Class<?> aClass) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String nativeSQL(final String s) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public CallableStatement prepareCall(final String s) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public CallableStatement prepareCall(final String s, final int i, final int i1) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public CallableStatement prepareCall(final String s, final int i, final int i1, final int i2) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(final String s) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(final String s, final int i) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(final String s, final int i, final int i1) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(final String s, final int i, final int i1, final int i2)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(final String s, final int[] ints) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(final String s, final String[] strings) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void releaseSavepoint(final Savepoint savepoint) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void rollback() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void rollback(final Savepoint savepoint) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAutoCommit(final boolean b) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCatalog(final String s) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setClientInfo(final Properties properties) throws SQLClientInfoException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setClientInfo(final String s, final String s1) throws SQLClientInfoException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setHoldability(final int i) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setNetworkTimeout(final Executor executor, final int i) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setReadOnly(final boolean b) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Savepoint setSavepoint(final String s) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSchema(final String s) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTransactionIsolation(final int i) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTypeMap(final Map<String, Class<?>> map) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(final Class<T> aClass) throws SQLException {
		throw new UnsupportedOperationException();
	}
}
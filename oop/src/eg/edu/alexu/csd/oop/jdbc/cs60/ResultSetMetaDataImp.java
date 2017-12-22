package eg.edu.alexu.csd.oop.jdbc.cs60;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

public class ResultSetMetaDataImp implements ResultSetMetaData {

	protected Object[][] table;
	protected String[][] columns;
	protected String tableName;

	public ResultSetMetaDataImp(final Object[][] table, final String[][] columns, final String tableName) {
		DBLogger.getInstance().log.info("Generating meta data.");
		this.table = table;
		this.columns = columns;
		this.tableName = tableName;
	}

	@Override
	public String getCatalogName(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getColumnClassName(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getColumnCount() throws SQLException {
		if (table.length != 0 && table[0] != null) {
			return table[0].length;
		}
		return 0;
	}

	@Override
	public int getColumnDisplaySize(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getColumnLabel(final int column) throws SQLException {
		if (column <= 0 || column > columns.length) {
			throw new SQLException();
		}
		return columns[column - 1][0];
	}

	@Override
	public String getColumnName(final int column) throws SQLException {
		if (column <= 0 || column > columns.length) {
			throw new SQLException();
		}
		return columns[column - 1][0];
	}

	@Override
	public int getColumnType(final int column) throws SQLException {
		if (column <= 0) {
			throw new SQLException();
		}
		return columns[column - 1][1].equalsIgnoreCase("int") ? Types.INTEGER : Types.VARCHAR;
	}

	@Override
	public String getColumnTypeName(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getPrecision(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getScale(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSchemaName(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getTableName(final int column) throws SQLException {
		if (column <= 0) {
			throw new SQLException();
		}
		if (tableName == null) {
			return "";
		}
		return tableName;
	}

	@Override
	public boolean isAutoIncrement(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCaseSensitive(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCurrency(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isDefinitelyWritable(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int isNullable(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isReadOnly(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSearchable(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSigned(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isWrapperFor(final Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isWritable(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(final Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}
}

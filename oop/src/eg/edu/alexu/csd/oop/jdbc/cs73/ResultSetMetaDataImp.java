package eg.edu.alexu.csd.oop.jdbc.cs73;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

public class ResultSetMetaDataImp implements ResultSetMetaData {

	protected Object[][] table;
	protected String[][] columns;

	public ResultSetMetaDataImp(Object[][] table, String[][] columns) {
		this.table = table;
		this.columns = columns;
	}

	@Override
	public int getColumnCount() throws SQLException {
		return columns.length;
	}

	@Override
	public String getColumnLabel(int column) throws SQLException {
		return columns[0][column - 1];
	}

	@Override
	public String getColumnName(int column) throws SQLException {
		return columns[0][column - 1];
	}

	@SuppressWarnings("null")
	@Override
	public int getColumnType(int column) throws SQLException {
		if (columns[1][column - 1].getClass()
				.getSimpleName()
				.equalsIgnoreCase("Varchar")) {
			return Types.VARCHAR;
		} else if (columns[1][column - 1].getClass()
				.getSimpleName()
				.equalsIgnoreCase("Integer")) {
			return Types.INTEGER;
		} else {
			return (Integer) null;
		}
	}

	@Override
	public String getTableName(int column) throws SQLException {
		String tableName = "";
		
		return tableName;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCatalogName(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getColumnClassName(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getColumnDisplaySize(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getColumnTypeName(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getPrecision(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getScale(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSchemaName(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isAutoIncrement(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCaseSensitive(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCurrency(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isDefinitelyWritable(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int isNullable(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isReadOnly(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSearchable(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSigned(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isWritable(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}
}


package eg.edu.alexu.csd.oop.jdbc.cs73;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

public class ResultSetMetaDataImp implements ResultSetMetaData {

	protected Object[][] table;
	protected String[][] columns;
	protected String tableName;

	public ResultSetMetaDataImp(Object[][] table, String[][] columns, String tableName) {
		DBLogger.getInstance().log.info("Generating meta data.");
		this.table = table;
		this.columns = columns;
		this.tableName = tableName;
	}

	@Override
	public int getColumnCount() throws SQLException {
		if(table.length != 0 && table[0] != null) {
			return table[0].length;
		}
		return 0 ;
	}

	@Override
	public String getColumnLabel(int column) throws SQLException {
		if(column <= 0 || column > columns.length){
			throw new SQLException();
		}
		return columns[column - 1][0];
	}

	@Override
	public String getColumnName(int column) throws SQLException {
		if(column <= 0 || column > columns.length){
			throw new SQLException();
		}
		return columns[column - 1][0];
	}

	@Override
	public int getColumnType(int column) throws SQLException {
		if(column <= 0){
			throw new SQLException();
		}
		return columns[column-1][1].equalsIgnoreCase("int")? Types.INTEGER : Types.VARCHAR;
	}

	@Override
	public String getTableName(int column) throws SQLException {
		if(column <= 0){
			throw new SQLException();
		} if (tableName == null) {
			return "";
		}
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

package eg.edu.alexu.csd.oop.jdbc.cs60;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * ResultSet implementation.
 *
 * @author H
 *
 */
public class ResultsetImp implements ResultSet {

	private String[][] colInfo;
	private Object[][] res;
	private String tableName;
	private int rowCursor;
	private int colCursor;
	private final int rows;
	private int cols;
	private boolean closed;
	private final Statement statementCreator;
	private DBLogger logger;

	public ResultsetImp(final Object[][] res, final Statement statementCreator) {
		logger = DBLogger.getInstance();
		logger.log.info("Building ResultSet object.");
		this.res = res;
		this.statementCreator = statementCreator;
		closed = false;
		rows = res.length;
		if (res.length != 0 && res[0] != null) {
			cols = res[0].length;
		} else {
			cols = 0;
		}
		colCursor = 0;
		rowCursor = 0;
	}

	/**
	 * This is the one and only constructor for the Result object.
	 *
	 * @param res
	 *            result of query as 2D Object array.
	 * @param colNames
	 *            String array of column names where 0-indexed indices
	 *            correspond to 1-indexed column names.
	 * @param tableName
	 *            name of the table.
	 * @param statementCreator
	 *            the very same Statement object that created this ResultSet
	 *            object.
	 */
	public ResultsetImp(final Object[][] res, final String[][] colNames, final String tableName,
			final Statement statementCreator) {
		logger = DBLogger.getInstance();
		logger.log.info("Building up ResultSet object.");
		this.statementCreator = statementCreator;
		this.colInfo = colNames;
		this.res = res;
		this.tableName = tableName;
		closed = false;
		rows = res.length;
		if (res.length != 0 && res[0] != null) {
			cols = res[0].length;
		} else {
			cols = 0;
		}
		colCursor = 0;
		rowCursor = 0;
	}

	@Override
	public boolean absolute(final int row) throws SQLException {
		if (closed) {
			throw new SQLException("Result set closed.");
		}
		logger.log.info("Moving head to absolute position " + row);
		if (row > 0) {
			rowCursor = row;
		} else if (row < 0) {
			rowCursor = rows + 1 + row;
		} else {
			rowCursor = 0;
		}
		return rowCursor > 0 && rowCursor < rows + 1;
	}

	@Override
	public void afterLast() throws SQLException {
		if (closed) {
			throw new SQLException("Result set closed.");
		}
		if (rows != 0) {
			rowCursor = rows + 1;
		}
	}

	@Override
	public void beforeFirst() throws SQLException {
		if (closed) {
			throw new SQLException("Result set closed.");
		}
		rowCursor = 0;
	}

	@Override
	public void cancelRowUpdates() throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void close() throws SQLException {// No more access to data.
		if (!closed) {
			DBLogger.getInstance().log.warning("Closing ResultSet.");
			closed = true;
			res = null;// It shall be cleaned by garbage collector
			// unless other references are pointing to it so handle that!
		}
		throw new SQLException();
	}

	@Override
	public void deleteRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int findColumn(final String columnLabel) throws SQLException {
		if (closed) {
			throw new SQLException("Result set closed.");
		}
		if (columnLabel == null) {
			DBLogger.getInstance().log.warning("Given null in findColumn!");
			throw new SQLException("Given null in findColumn!");
		} else {
			for (int i = 0; i < colInfo.length; i++) {
				if (columnLabel.equalsIgnoreCase(colInfo[0][i])) {
					colCursor = i;
					return colCursor;
				}
			} // Not found.
			throw new SQLException("Specified ColumnLabel doesn't exist in ResultSet");
		}
	}

	@Override
	public boolean first() throws SQLException {
		if (closed) {
			throw new SQLException("Result set closed.");
		}
		rowCursor = 1;
		return rows != 0;
	}

	@Override
	public Array getArray(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Array getArray(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getAsciiStream(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getAsciiStream(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(final int columnIndex, final int scale) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(final String columnLabel, final int scale) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getBinaryStream(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getBinaryStream(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Blob getBlob(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Blob getBlob(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getBoolean(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getBoolean(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte getByte(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte getByte(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte[] getBytes(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte[] getBytes(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader getCharacterStream(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader getCharacterStream(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Clob getClob(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Clob getClob(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getConcurrency() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCursorName() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(final int columnIndex, final Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(final String columnLabel, final Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getDouble(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getDouble(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getFetchDirection() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getFetchSize() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public float getFloat(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public float getFloat(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getHoldability() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getInt(final int columnIndex) throws SQLException {
		if (closed) {
			throw new SQLException("Result set closed.");
		}
		logger.log.info("Fetching result at column " + columnIndex);
		if (columnIndex > cols) {
			logger.log.info("Given invalid column index to getInt.");
			throw new SQLException("Invalid column index.");
		}
		if (isAfterLast() || isBeforeFirst()) {
			return 0;
		}
		int returner = 0;
		try {
			final Object x = res[rowCursor - 1][columnIndex - 1];
			if (x instanceof String) {
				final String z = (String) x;
				if (z.equalsIgnoreCase("null")) {
					return 0;
				}
			}
			returner = (Integer) x;
			return returner;
		} catch (final Exception e) {
			throw new SQLException();
		}
	}

	@Override
	public int getInt(final String columnLabel) throws SQLException {
		return getInt(findColumn(columnLabel));
	}

	@Override
	public long getLong(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getLong(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		if (closed) {
			throw new SQLException("Result set closed.");
		}
		return new ResultSetMetaDataImp(res, colInfo, tableName);
	}

	@Override
	public Reader getNCharacterStream(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader getNCharacterStream(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public NClob getNClob(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public NClob getNClob(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getNString(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getNString(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getObject(final int columnIndex) throws SQLException {
		if (closed) {
			throw new SQLException("Result set closed.");
		}
		logger.log.info("Fetching result at " + columnIndex);
		if (columnIndex > cols) {
			logger.log.info("Given invalid column index to getObject.");
			throw new SQLException("Invalid column index.");
		}
		if (isAfterLast() || isBeforeFirst()) {
			throw new RuntimeException();
		}
		final Object x = res[rowCursor - 1][columnIndex - 1];
		if (x instanceof String) {
			final String z = (String) x;
			if (z.equalsIgnoreCase("null")) {
				return null;
			}
		}
		return res[rowCursor - 1][columnIndex - 1];
	}

	@Override
	public <T> T getObject(final int columnIndex, final Class<T> type) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getObject(final int columnIndex, final Map<String, Class<?>> map) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getObject(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T getObject(final String columnLabel, final Class<T> type) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getObject(final String columnLabel, final Map<String, Class<?>> map) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Ref getRef(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Ref getRef(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public RowId getRowId(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public RowId getRowId(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getShort(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getShort(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLXML getSQLXML(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLXML getSQLXML(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Statement getStatement() throws SQLException {
		if (closed) {
			throw new SQLException("Result set closed.");
		}
		return statementCreator;
	}

	@Override
	public String getString(final int columnIndex) throws SQLException {
		if (closed) {
			throw new SQLException("Result set closed.");
		}
		if (columnIndex > cols) {
			logger.log.info("Given invalid column index to getString.");
			throw new SQLException("Invalid column index.");
		}
		if (isAfterLast() || isBeforeFirst()) {
			throw new SQLException();
		}
		try {
			logger.log.info("Fetching result at column " + columnIndex);
			final Object x = res[rowCursor - 1][columnIndex - 1];
			if (x instanceof String) {
				final String z = (String) x;
				if (z.equalsIgnoreCase("null")) {
					return null;
				}
			}
			final String returner = (String) x;
			return returner;
		} catch (final Exception e) {
			throw new SQLException();
		}
	}

	@Override
	public String getString(final String columnLabel) throws SQLException {
		return getString(findColumn(columnLabel));
	}

	@Override
	public Time getTime(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Time getTime(final int columnIndex, final Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Time getTime(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Time getTime(final String columnLabel, final Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(final int columnIndex, final Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(final String columnLabel, final Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getType() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getUnicodeStream(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getUnicodeStream(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public URL getURL(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public URL getURL(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void insertRow() throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean isAfterLast() throws SQLException {
		return rowCursor == rows + 1;
	}

	@Override
	public boolean isBeforeFirst() throws SQLException {
		return rowCursor == 0;
	}

	@Override
	public boolean isClosed() throws SQLException {
		return closed;
	}

	@Override
	public boolean isFirst() throws SQLException {
		if (closed) {
			throw new SQLException("Result set closed.");
		}
		return rowCursor == 1;
	}

	@Override
	public boolean isLast() throws SQLException {
		if (closed) {
			throw new SQLException("Result set closed.");
		}
		return rowCursor == rows;
	}

	@Override
	public boolean isWrapperFor(final Class<?> arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean last() throws SQLException {
		if (closed) {
			throw new SQLException("Result set closed.");
		}
		logger.log.info("Head is moved to last row.");
		rowCursor = rows;
		return rows != 0;
	}

	@Override
	public void moveToCurrentRow() throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void moveToInsertRow() throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean next() throws SQLException {
		if (closed) {
			throw new SQLException("Result set closed.");
		}
		logger.log.info("Advancing head.");
		rowCursor++;
		return !isAfterLast();
	}

	@Override
	public boolean previous() throws SQLException {
		if (closed) {
			throw new SQLException("Result set closed.");
		}
		rowCursor--;
		logger.log.info("Moving head backwards.");
		return !isBeforeFirst();
	}

	@Override
	public void refreshRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean relative(final int rows) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean rowDeleted() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean rowInserted() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean rowUpdated() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFetchDirection(final int direction) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setFetchSize(final int rows) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public <T> T unwrap(final Class<T> arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateArray(final int columnIndex, final Array x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateArray(final String columnLabel, final Array x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateAsciiStream(final int columnIndex, final InputStream x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateAsciiStream(final int columnIndex, final InputStream x, final int length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateAsciiStream(final int columnIndex, final InputStream x, final long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateAsciiStream(final String columnLabel, final InputStream x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateAsciiStream(final String columnLabel, final InputStream x, final int length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateAsciiStream(final String columnLabel, final InputStream x, final long length)
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBigDecimal(final int columnIndex, final BigDecimal x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBigDecimal(final String columnLabel, final BigDecimal x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBinaryStream(final int columnIndex, final InputStream x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBinaryStream(final int columnIndex, final InputStream x, final int length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBinaryStream(final int columnIndex, final InputStream x, final long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBinaryStream(final String columnLabel, final InputStream x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBinaryStream(final String columnLabel, final InputStream x, final int length)
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBinaryStream(final String columnLabel, final InputStream x, final long length)
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBlob(final int columnIndex, final Blob x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBlob(final int columnIndex, final InputStream inputStream) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBlob(final int columnIndex, final InputStream inputStream, final long length)
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBlob(final String columnLabel, final Blob x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBlob(final String columnLabel, final InputStream inputStream) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBlob(final String columnLabel, final InputStream inputStream, final long length)
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBoolean(final int columnIndex, final boolean x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBoolean(final String columnLabel, final boolean x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateByte(final int columnIndex, final byte x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateByte(final String columnLabel, final byte x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBytes(final int columnIndex, final byte[] x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBytes(final String columnLabel, final byte[] x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateCharacterStream(final int columnIndex, final Reader x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateCharacterStream(final int columnIndex, final Reader x, final int length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateCharacterStream(final int columnIndex, final Reader x, final long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateCharacterStream(final String columnLabel, final Reader reader) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateCharacterStream(final String columnLabel, final Reader reader, final int length)
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateCharacterStream(final String columnLabel, final Reader reader, final long length)
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateClob(final int columnIndex, final Clob x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateClob(final int columnIndex, final Reader reader) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateClob(final int columnIndex, final Reader reader, final long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateClob(final String columnLabel, final Clob x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateClob(final String columnLabel, final Reader reader) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateClob(final String columnLabel, final Reader reader, final long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateDate(final int columnIndex, final Date x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateDate(final String columnLabel, final Date x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateDouble(final int columnIndex, final double x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateDouble(final String columnLabel, final double x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateFloat(final int columnIndex, final float x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateFloat(final String columnLabel, final float x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateInt(final int columnIndex, final int x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateInt(final String columnLabel, final int x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateLong(final int columnIndex, final long x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateLong(final String columnLabel, final long x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNCharacterStream(final int columnIndex, final Reader x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNCharacterStream(final int columnIndex, final Reader x, final long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNCharacterStream(final String columnLabel, final Reader reader) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNCharacterStream(final String columnLabel, final Reader reader, final long length)
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNClob(final int columnIndex, final NClob nClob) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNClob(final int columnIndex, final Reader reader) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNClob(final int columnIndex, final Reader reader, final long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNClob(final String columnLabel, final NClob nClob) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNClob(final String columnLabel, final Reader reader) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNClob(final String columnLabel, final Reader reader, final long length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNString(final int columnIndex, final String nString) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNString(final String columnLabel, final String nString) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNull(final int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateNull(final String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateObject(final int columnIndex, final Object x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateObject(final int columnIndex, final Object x, final int scaleOrLength) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateObject(final String columnLabel, final Object x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateObject(final String columnLabel, final Object x, final int scaleOrLength) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateRef(final int columnIndex, final Ref x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateRef(final String columnLabel, final Ref x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateRow() throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateRowId(final int columnIndex, final RowId x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateRowId(final String columnLabel, final RowId x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateShort(final int columnIndex, final short x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateShort(final String columnLabel, final short x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateSQLXML(final int columnIndex, final SQLXML xmlObject) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateSQLXML(final String columnLabel, final SQLXML xmlObject) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateString(final int columnIndex, final String x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateString(final String columnLabel, final String x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateTime(final int columnIndex, final Time x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateTime(final String columnLabel, final Time x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateTimestamp(final int columnIndex, final Timestamp x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateTimestamp(final String columnLabel, final Timestamp x) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean wasNull() throws SQLException {
		throw new UnsupportedOperationException();
	}

}

package eg.edu.alexu.csd.oop.jdbc.cs73.view;

import java.io.File;
import java.sql.*;
import java.util.Properties;

import eg.edu.alexu.csd.oop.jdbc.cs73.ConnectionImp;
import eg.edu.alexu.csd.oop.jdbc.cs73.DriverImp;

public class Controller {

	File mainDirectory = new File("all__data");
	String path = mainDirectory.getAbsolutePath();
	Connection connect;
	Statement statement;
	ResultSet resultSet;
	int updatedRows;
	ResultSetMetaData metaData;
	Driver driver = new DriverImp();
	String[] rows;

	public String[] getResult(String sql) {
		Properties properties = new Properties();
		if (path != null) {
			properties.setProperty("path", path);
		} else {
			properties.setProperty("path", "");
		}
		try {
			connect = driver.connect(sql, properties);
			statement = connect.createStatement();
			boolean state = statement.execute(sql);

			if(sql.trim().split("\\s+")[0].equalsIgnoreCase("create") || sql.trim().split("\\s+")[0].equalsIgnoreCase("drop")) {
				updatedRows = 0;
				//statement.close();
				//connect.close();
				String[] count = new String[1];
				count[0] = Integer.toString(updatedRows);
				return count;
			}
			else if(sql.trim().split("\\s+")[0].equalsIgnoreCase("insert") || sql.trim().split("\\s+")[0].equalsIgnoreCase("delete")||sql.trim().split("\\s+")[0].equalsIgnoreCase("update")) {
				updatedRows = statement.getUpdateCount();
				updatedRows = 0;
				//statement.close();
				//connect.close();
				String[] count = new String[1];
				count[0] = Integer.toString(updatedRows);
				return count;
			}
			else if (sql.trim().split("\\s+")[0].equalsIgnoreCase("select")) {
				resultSet = statement.getResultSet();
				metaData = resultSet.getMetaData();
				int size = 0, counter = 1;
				while(resultSet.next()){
					size++;
				}
				rows = new String[size];
				while(resultSet.previous()){
					rows[size - 1] = "";
					while(counter <= metaData.getColumnCount()){
						String label = metaData.getColumnLabel(counter);
						String data = "";
						if (metaData.getColumnType(counter) == Types.VARCHAR) {
							data = resultSet.getString(label);
						} else if (metaData.getColumnType(counter) == Types.INTEGER) {
							data = Integer.toString(resultSet.getInt(label));
						}
						if (rows[size - 1].equals("")) {
							rows[size - 1] = label + ": " + data;
						} else {
							rows[size - 1] = rows[size - 1] + ", " + label + ": " + data;
						}
						counter++;
					}
					size--;
				}
				//resultSet.close();
				//statement.close();
				//connect.close();
				return rows;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void getPath(String databaseName) {
		path = mainDirectory.getAbsolutePath() + File.separator + databaseName;
		connect = new ConnectionImp(path);
	}
}

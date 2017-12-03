package eg.edu.alexu.csd.oop.jdbc.cs73.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class JdbcController {
	private JdbcView view ;
	private Driver driver;
	private Connection connection;
	private Statement statement;
	private String path = "";
	private ArrayList<String> history;
	
	public JdbcController(JdbcView view , Driver driver) {
		this.view = view;
		this.driver = driver;
		history = new ArrayList<>();
		setupConnection();
		addListeners(view);
	}
	private void setupConnection() {
		Properties properties = new Properties();
		properties.put("path", new File(path));
		try {
			connection = driver.connect("", properties);
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void addListeners(JdbcView view) {
		view.getPathButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new PathChooser(view, getController());
			}
		});
		
		view.getQueryButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String sql =view.getQueryField().getText();
					if(sql.trim().equals("")) {
						return ;
					}
					history.add(sql);
					view.getHistory().setListData(history.toArray(new String [0]));
					String query = sql.trim().split("\\s+")[0];
					if(query.equalsIgnoreCase("create") || query.equalsIgnoreCase("drop")) {
						boolean res = statement.execute(sql);
						if(res == true)
							view.getTextArea().setText("Query has been executed successfully");
						else
							view.getTextArea().setText("Query hasn't been executed successfully");
					}
					else if(query.equalsIgnoreCase("insert") || query.equalsIgnoreCase("delete")||query.equalsIgnoreCase("update")) {
						int res = statement.executeUpdate(sql);
						System.out.println(res);
						view.getTextArea().setText(res + " has been changed");
					}
					else if(query.equalsIgnoreCase("select")) {
						updateTable(statement.executeQuery(sql));
					}
					else {
						view.getTextArea().setText("Wrong Query");
					}
				}
				catch(Exception exception) {
					view.getTextArea().setText("Wrong Format");
				}
			}
		});
	}
	public void setPath(String path) {
		this.path = path;
		view.setPathLabel(path);
		view.clear();
		setupConnection();
	}
	
	private JdbcController getController() {
		return this;
	}
	
	private void updateTable(ResultSet res) {
		System.out.println("i'm here");
		ArrayList<String> titles = new ArrayList<>();
		String [][] data = new String [0][];
		int rows = 0;
		try {
			for(int i = 1 ; i <= res.getMetaData().getColumnCount() ; i++) {
				titles.add(res.getMetaData().getColumnLabel(i));
			}
			res.first();
			if(!res.isBeforeFirst())
				rows ++ ;
			while(!res.isLast()) {
				rows ++ ;
				res.next();
			}
			data = new String[rows][res.getMetaData().getColumnCount()];
			System.out.println(rows + " " + res.getMetaData().getColumnCount());
			res.first();
			for(int i = 0 ; i < rows ; i ++) {
				for(int j = 0 ; j < res.getMetaData().getColumnCount() ; j++)
					data[i][j] = res.getObject(j+1).toString();
				res.next();
			}
			
		} catch (SQLException e) {
			view.getTextArea().setText("Something went Wrong");
		}
		view.getTable().setModel(new DefaultTableModel(data , titles.toArray(new String [0])));
	}
}

package eg.edu.alexu.csd.oop.jdbc.cs73.view;

import eg.edu.alexu.csd.oop.jdbc.cs73.DriverImp;

public class mainEntry {
	public static void main(String [] args) {
		new JdbcController(new JdbcView(), new DriverImp());
	}
}

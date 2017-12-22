package eg.edu.alexu.csd.oop.jdbc.cs60.view;

import eg.edu.alexu.csd.oop.jdbc.cs60.DriverImp;

public class MainEntry {
	public static void main(String [] args) {
		new JdbcController(new JdbcView(), new DriverImp());
	}
}

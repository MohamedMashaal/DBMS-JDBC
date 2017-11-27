package eg.edu.alexu.csd.oop.db.cs73.View;

import java.sql.SQLException;
import java.util.Scanner;

import eg.edu.alexu.csd.oop.db.cs73.Controller.QueriesExecutor;

public class UI {
	public static void main(String [] args) {
		Scanner sc = new Scanner(System.in);
		QueriesExecutor executor = new QueriesExecutor();
		while(true) {
			System.out.println("Input command :");
			String query = sc.nextLine();
			try {
				System.out.println(executor.executeQuery(query));
			} catch (SQLException e) {
				System.out.println("Wrong Format");
			}
		}
	}
}

package eg.edu.alexu.csd.oop.db.cs60.View;

import java.sql.SQLException;
import java.util.Scanner;

import eg.edu.alexu.csd.oop.db.cs60.Controller.QueriesExecutor;

public class UI {
	private static Scanner sc;
	public static void main(String [] args) {
		sc = new Scanner(System.in);
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

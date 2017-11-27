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

	@Test
	public void testCreateAndOpenAndDropDatabase() {
		File dummy = null;
		Database db =  (Database)TestRunner.getImplementationInstance();
		{
			File dbDir = createDatabase(db, "SaMpLe", true);
			String files[] = dbDir.list();
			Assert.assertTrue("Database directory is not empty!", files == null || files.length == 0);
			dummy = new File(dbDir, "dummy");
			try {
				boolean created = dummy.createNewFile();
				Assert.assertTrue("Failed t create file into DB", created && dummy.exists());
			} catch (IOException e) {
				TestRunner.fail("Failed t create file into DB", e);
			}
		}
		{
			File dbDir = createDatabase(db, "sAmPlE", false);
			String files[] = dbDir.list();
			Assert.assertTrue("Database directory is empty after opening!", files.length > 0);
			Assert.assertTrue("Failed t create find a previously created file into DB", dummy.exists());
		}
		{
			File dbDir = createDatabase(db, "SAMPLE", true);
			String files[] = dbDir.list();
			Assert.assertTrue("Database directory is not empty after drop!", files == null || files.length == 0);
		}
	}
}

package eg.edu.alexu.csd.oop.jdbc.cs73;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class does the job of handling the logger for the JDBC&DBMS.
 * @author H
 *
 */
public class DBLogger {

	public Logger log;
	private FileHandler fh;

	public DBLogger() {
		File f = new File("log.txt");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				System.out.println("Failed to create log file.");
				e.printStackTrace();
			}
		}
		try {
			fh = new FileHandler("log.txt", true);
		} catch (SecurityException | IOException e) {
			System.out.println("Failed to handle log file.");
			e.printStackTrace();
		}// Appends to log.txt file.
		log = Logger.getLogger("MainLog");
		log.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());
	}

}

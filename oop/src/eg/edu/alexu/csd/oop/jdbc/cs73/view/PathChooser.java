package eg.edu.alexu.csd.oop.jdbc.cs73.view;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class PathChooser  extends JFileChooser{
	JdbcView view;
	JdbcController controller;
	File fileLoaded;

	public PathChooser(JdbcView view ,JdbcController controller) {
		super();
		this.view = view;
		this.controller = controller;
		setAcceptAllFileFilterUsed(false);
		setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnVal = showDialog(view.getMainWindow(), "Choose");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			controller.setPath(getSelectedFile().getAbsolutePath());
			
		}
	}
}

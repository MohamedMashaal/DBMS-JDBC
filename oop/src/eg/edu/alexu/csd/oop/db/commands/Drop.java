package eg.edu.alexu.csd.oop.db.commands;

import eg.edu.alexu.csd.oop.db.Command;

public class Drop implements Command{

	@Override
	public boolean syntaxParse(String query) {
		String nQuery = query.toUpperCase();
		nQuery = nQuery.charAt(nQuery.length()-1) == ';' ? nQuery.substring(0, nQuery.length()-1) : nQuery ;
		nQuery = nQuery.trim().replaceAll("\\s+", " ");
		if(nQuery.matches("DROP DATABASE \\w+"))
			return true ;
		else if (nQuery.matches("DROP TABLE \\w+"))
			return true ;
		return false;
	}

}

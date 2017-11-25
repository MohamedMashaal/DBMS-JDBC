package eg.edu.alexu.csd.oop.db.commands;

import eg.edu.alexu.csd.oop.db.Command;

public class Delete implements Command{

	@Override
	public boolean syntaxParse(String query) {
		String nQuery = query.toUpperCase();
		nQuery = nQuery.charAt(nQuery.length()-1) == ';' ? nQuery.substring(0, nQuery.length()-1) : nQuery ;
		nQuery = nQuery.trim().replaceAll("\\s+", " ");
		if (nQuery.matches("DELETE FROM \\w+") || nQuery.matches("DELETE \\* FROM \\w+")) {
			return true;
		}
		else {
			String [] splittedQuery = nQuery.split(" ");
			if (splittedQuery[0].equalsIgnoreCase("delete") && splittedQuery[1].equalsIgnoreCase("from") && splittedQuery[3].equalsIgnoreCase("where") && splittedQuery.length > 4) {
				return true ;
			}
			else if (splittedQuery[0].equalsIgnoreCase("delete") && splittedQuery[1].equalsIgnoreCase("*") &&splittedQuery[2].equalsIgnoreCase("from") && splittedQuery[4].equalsIgnoreCase("where") && splittedQuery.length > 5)
				return true;
		}
		return false;
	}
}

package eg.edu.alexu.csd.oop.db.commands;

import eg.edu.alexu.csd.oop.db.Command;

public class Select implements Command{

	@Override
	public boolean syntaxParse(String query) {
		
		if(query.toUpperCase().matches("SELECT \\* FROM \\w+"))
			return true;
		else {
			String [] splittedQuery = query.split("\\s+");
			if(splittedQuery[0].equalsIgnoreCase("select")) {
				int i = 1 ;
				while(i < splittedQuery.length && !splittedQuery[i].equalsIgnoreCase("from")) {
					i++ ;
				}
				if(i < splittedQuery.length && splittedQuery[i].equalsIgnoreCase("from") && splittedQuery.length - (i+1) == 1) {
					return true;
				}
				else if(i < splittedQuery.length && splittedQuery[i].equalsIgnoreCase("from") && i+2 < splittedQuery.length && splittedQuery[i+2].equalsIgnoreCase("where")) {
					return true ;
				} 
			}
		}
		return false;
	}
}

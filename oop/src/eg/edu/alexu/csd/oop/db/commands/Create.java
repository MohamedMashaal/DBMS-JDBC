package eg.edu.alexu.csd.oop.db.commands;

import eg.edu.alexu.csd.oop.db.Command;

public class Create implements Command{

	@Override
	public boolean syntaxParse(String query) {
		String nQuery = query.toUpperCase();
		nQuery = nQuery.charAt(nQuery.length()-1) == ';' ? nQuery.substring(0, nQuery.length()-1) : nQuery ;
		nQuery = nQuery.trim().replaceAll("\\s+", " ");
		if(nQuery.matches("CREATE DATABASE \\w+"))
			return true ;
		else {
			//Create table
			nQuery = nQuery.replaceAll("\\(", " \\( ").replaceAll("\\)", " \\) ").replaceAll(",", " , ").replaceAll("\\s+", " ").toUpperCase();
			String [] splittedQuery = nQuery.split("\\s+");
			if(splittedQuery[0].equalsIgnoreCase("create") && splittedQuery[1].equalsIgnoreCase("table") && splittedQuery[2].matches("\\w+")) {
				if(splittedQuery[3].equalsIgnoreCase("(") && splittedQuery[splittedQuery.length-1].equalsIgnoreCase(")")){
					for(int i = 4 ; i < splittedQuery.length-1 ; i++) {
						if(i % 3 == 1 && splittedQuery[i].matches("\\w+")) {
							continue ;
						}
						else if (i % 3 == 2 && (splittedQuery[i].equalsIgnoreCase("varchar") || splittedQuery[i].equalsIgnoreCase("int"))) {
							continue;
						}
						else if (i % 3 == 0 && splittedQuery[i].equalsIgnoreCase(",")) {
							continue;
						}
						else {
							return false ;
						}
					}
					return true ;
				}
			}
		}
		return false;
	}

}

package eg.edu.alexu.csd.oop.db.commands;

import eg.edu.alexu.csd.oop.db.Command;

public class Update implements Command{

	@Override
	public boolean syntaxParse(String query) {
		String [] splittedQuery = query.replaceAll("\\)", " ").replaceAll("\\(", " ").replaceAll("=", " = ").replaceAll("\\s+\\,", ",").split("\\s+(?=(?:[^\']*\'[^\']*\')*[^\']*$)|\\,\\s*|\\(|\\)");
		if(splittedQuery[0].equalsIgnoreCase("update") && splittedQuery[2].equalsIgnoreCase("set")) {
			int i = 3 ;
			int cnt1 = 0 ;
			int cnt2 = 0 ;
			while(i < splittedQuery.length && !splittedQuery[i].equalsIgnoreCase("where")) {
				if(i % 3 == 0)
					cnt1 ++ ;
				else if(i % 3 == 1 && !splittedQuery[i].equalsIgnoreCase("=")) {
					return false;
				}
				else if (i % 3 == 2)
					cnt2 ++;
				i++;
			}
			if(cnt1 != cnt2) {
				return false ;
			}
			else {
				if (i+1 < splittedQuery.length && splittedQuery[i].equalsIgnoreCase("where"))
					return true;
				else if (i == splittedQuery.length && !splittedQuery[i-1].equalsIgnoreCase("where")) {
					return true ;
				}
			}
		}
		return false;
	}

}

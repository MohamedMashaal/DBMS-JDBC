package eg.edu.alexu.csd.oop.db.commands;

import eg.edu.alexu.csd.oop.db.Command;

public class Insert implements Command{
	@Override
	public boolean syntaxParse(String query) {
		query = query.charAt(query.length()-1) == ';' ? query.substring(0, query.length()-1) : query ;
		String [] nQuery = query.replaceAll("\\)", " ").replaceAll("\\(", " ").replaceAll("\\s+\\,", ",").split("\\s+(?=(?:[^\']*\'[^\']*\')*[^\']*$)|\\,\\s*|\\(|\\)|\\=");
		if(nQuery[0].equalsIgnoreCase("insert") && nQuery[1].equalsIgnoreCase("into") && nQuery[3].equalsIgnoreCase("values") && nQuery.length > 4) {
			return true ;
		}
		else if (nQuery[0].equalsIgnoreCase("insert") && nQuery[1].equalsIgnoreCase("into")) {
			int cnt1 = 0 ;
			int cnt2 = 0 ;
			int i = 3 ;
			while(i < nQuery.length && !nQuery[i].equalsIgnoreCase("values")) {
				cnt1 ++ ;
				i++;
			}
			i++;
			while(i < nQuery.length) {
				cnt2++;
				i++;
			}
			if(cnt1 == cnt2 && cnt1 != 0)
				return true ;
		}
		return false;
	}

}

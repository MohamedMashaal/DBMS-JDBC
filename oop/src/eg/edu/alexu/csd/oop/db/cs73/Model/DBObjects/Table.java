package eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects;

import java.util.ArrayList;
import java.util.List;

public class Table {
	private String name ;
    private ArrayList<Column> columns;

    public Table(String name){
    	this.name = name ;
        this.columns = new ArrayList<>();
    }
    
    public Table(String name , String [] columns) {
    	this.name = name ;
    	this.columns = new ArrayList<>();
    	addColumns(columns);
    }
    
    public void addColumns(String [] columns) {
    	for(int i = 0 ; i < columns.length ; i+=2) {
    		if(columns[i+1].equalsIgnoreCase("int")) {
    			this.columns.add(new Column<Integer>(columns[i], "int"));
    		}
    		else if (columns[i+1].equalsIgnoreCase("varchar")) {
    			this.columns.add(new Column<String>(columns[i], "varchar"));
    		}
    	}
    }
    
    public String getName() {
		return name;
	}

	public ArrayList<Column> getColumns() {
		return columns;
	}
	
	public boolean columnExists(String name){
    	for(Column column : columns){
    		if(column.getName().equalsIgnoreCase(name))
    			return true;
		}
		return false;
	}

	public int columnIndex(String name){
		int i = 0;
		for(Column column : columns){
			if(column.getName().equals(name))
				return i;
			i++;
		}
		return -1;
	}
	
	public void insert(List<String> columns, List<String> values) {
		for(Column column : this.columns) {
			int index = getIndex(columns , column.getName());
			if(index == -1) {
				column.addRecord(null);
			}
			else {
				if(column.getType().equalsIgnoreCase("int"))
					column.addRecord(new Record<>(new Integer(Integer.parseInt(values.get(index)))));
				else if(column.getType().equalsIgnoreCase("varchar")) 
					column.addRecord(new Record<>(values.get(index)));
			}
		}
	}

	private int getIndex(List<String> columns, String name) {
		for(int i = 0 ; i < columns.size() ; i++) {
			if(columns.get(i).equalsIgnoreCase(name)) {
				return i ;
			}
		}
		return -1;
	}

	public int update(ArrayList<String> columns, ArrayList<String> values) {
		int size = 0 ;
		for(int i = 0 ; i < columns.size() ; i++) {
			int index = getIndex(columns.get(i));
			if(index != -1) {
				ArrayList<Record> records = this.columns.get(index).getRecords() ;
				String type = this.columns.get(index).getType();
				size = records.size();
				for(int j = 0 ; j < records.size() ; j++) {
					if(type.equalsIgnoreCase("int")) {
						records.get(j).setValue(Integer.parseInt((values.get(i))));
					}
					else if (type.equalsIgnoreCase("varchar")) {
						records.get(j).setValue(values.get(i));
					}
				}
			}
		}
		return size ;
	}

	private int getIndex(String cl) {
		int i = 0;
		for(Column column : columns) {
			if(column.getName().equalsIgnoreCase(cl))
				return i ;
			i++;
		}
		return -1;
	}
	
	public void setColumns(List<Column> columns) {
		this.columns = (ArrayList<Column>) columns;
	}

	public int update(ArrayList<String> columns, ArrayList<String> values, ArrayList<String> toUpdate) {
		if(toUpdate.size() == 2) {
			String whereColumn = toUpdate.get(0);
			String whereValue = toUpdate.get(1);
			int index = getIndex(whereColumn);
			String type = this.columns.get(index).getType();
			if(index != -1) {
				ArrayList<Record> records = this.columns.get(index).getRecords();
				for(int i = 0 ; i < records.size() ; i++) {
					if(type.equalsIgnoreCase("int")) {
						Integer recordValue = (Integer)(records.get(i).getValue());
						if(recordValue.intValue() == Integer.parseInt(whereValue)) {
							for(Column cl : this.columns) {
								int indexCl = getIndex(columns, cl.getName());
								if(indexCl != -1) {
									if(cl.getType().equalsIgnoreCase("int"))
										cl.getRecord(i).setValue(new Integer(Integer.parseInt(values.get(indexCl))));
									else if(cl.getType().equalsIgnoreCase("varchar")) {
										cl.getRecord(i).setValue(values.get(indexCl));
									}
								}
							}
						}
					}
					else if(type.equalsIgnoreCase("varchar")) {
						String recordValue = (String)(records.get(i).getValue());
						if(recordValue.equalsIgnoreCase(whereValue)) {
							for(Column cl : this.columns) {
								int indexCl = getIndex(columns, cl.getName());
								if(indexCl != -1) {
									if(cl.getType().equalsIgnoreCase("int"))
										cl.getRecord(i).setValue(new Integer(Integer.parseInt(values.get(indexCl))));
									else if(cl.getType().equalsIgnoreCase("varchar")) {
										cl.getRecord(i).setValue(values.get(indexCl));
									}
								}
							}
						}
					}
					
				}
			}
		}
		return 0;
	}
}

package com.mbi.oes.db.controls;

import java.util.ArrayList;
import java.util.List;

public class MultiLineTableData {

	String[] mainRow;
	List<String> childRows = new ArrayList<String>();
	
	public MultiLineTableData(String[] mainRow, List<String> childRows) {
		super();
		this.mainRow = mainRow;
		this.childRows = childRows;
	}
	public String[] getMainRow() {
		return mainRow;
	}
	public void setMainRow(String[] mainRow) {
		this.mainRow = mainRow;
	}
	public List<String> getChildRows() {
		return childRows;
	}
	public void setChildRows(List<String> childRows) {
		this.childRows = childRows;
	}
	
	
}

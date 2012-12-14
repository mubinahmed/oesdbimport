package com.mbi.oes.db.controls;

import org.eclipse.swt.widgets.TableItem;

public class ItemEntryTableItem extends TableItem{

	ItemEntryTable myTable;
	int rowNum;
	private boolean updateData = true;
	public ItemEntryTableItem(ItemEntryTable parent, int style) {
		super(parent, style);
		myTable = parent;
	}

	public ItemEntryTableItem(ItemEntryTable parent, int style, int rowNum) {
		super(parent, style);
		myTable = parent;
		this.rowNum = rowNum;
	}
	
	/*@Override
	public String getText(int index) {
		return myTable.getText(rowNum, index);
	}*/
	
	@Override
	public void setText(int colNum, String value) {
		//System.out.println("ColNum: "+colNum+" Value: "+value);
		super.setText(colNum, value);
		if (updateData) {
			myTable.updateData(rowNum, colNum, value);
		} else {
			updateData = true;
		}
	}
	
	@Override
	public void setText(String[] string) {
		//super.setText(string);
		int i = 0;
		for (String string2 : string) {
			updateData = false;
			setText(i++, string2);
		}
	}
	
	public void setUpdateData(boolean b) {
		updateData  = b;
	}
	
	@Override
	protected void checkSubclass() {

	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof ItemEntryTableItem)) {
			return false;
		}
		
		ItemEntryTableItem anotherItem = (ItemEntryTableItem)obj;
		/*System.out.println("Comparing: ");
		System.out.println(this);
		System.out.println(anotherItem);*/
		
		for (int i = 1; i < myTable.getColumnCount(); i++) {
			if (!getText(i).equals(anotherItem.getText(i)))	{
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString() {
		String retVal = "";
		for (int i = 0; i < myTable.getColumnCount(); i++) {
			retVal = retVal + getText(i) + " ";
		}
		return retVal;
	}
}

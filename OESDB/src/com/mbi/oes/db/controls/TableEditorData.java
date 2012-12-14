package com.mbi.oes.db.controls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.TableItem;

public class TableEditorData {

	private int editableCells[];
	private int editableCellDataTypes[];
	private TableEditorValidator validators[];
	private List<TableCellFormatter> cellFormatters = new ArrayList<TableCellFormatter>();
	private static Logger logger_ = Logger.getLogger(TableEditorData.class);
	
	
	public TableCellFormatter getCellFormatter(int col) {
		for (TableCellFormatter cF : cellFormatters) {
			if(cF.isForWholeTable() || cF.getColumn() == col) {
				return cF;
			}
		}
		return null;
	}
	
	public TableCellFormatter getCellFormatter(int row, int col) {
		for (TableCellFormatter cF : cellFormatters) {
			logger_.info(cF.getRow() == row);
			logger_.info(cF.getColumn() == col);
			logger_.info(cF.isForAllRows());
			if(cF.isForWholeTable() || (cF.getColumn() == col && (cF.getRow() == row || cF.isForAllRows()))) {
				return cF;
			}
		}
		return null;
	}
	
	public void applyFormatting(ItemEntryTableItem tableItem) {
		for (TableCellFormatter cF : cellFormatters) {
			if(cF.isForAllRows() || cF.isForWholeTable()) {
				cF.applyFormatting(tableItem);
			}
		}
	} 

	public void applyFormatting(ItemEntryTableItem item, int col) {
		if (item != null) {
			logger_.info("TableItem is not null");
			TableCellFormatter formatter = getCellFormatter(item.rowNum, col);
			if(formatter != null) {
				logger_.info("Applying formatting to the table item");
				formatter.applyFormatting(item);
			}
		}
	}
	
	
	public void addCellFormatter(TableCellFormatter formatter) {
		logger_.info("Added a cell formatter. Current Cell formatters size: "+cellFormatters.size());
		cellFormatters.add(formatter);
	}
	
	public List<TableCellFormatter> getCellFormatters() {
		return cellFormatters;
	}
	
	public void setCellFormatters(List<TableCellFormatter> cellFormatters) {
		this.cellFormatters = cellFormatters;
	}
	
	public int[] getEditableCells() {
		return editableCells;
	}
	public void setEditableCells(int[] editableCells) {
		this.editableCells = editableCells;
	}
	public int[] getEditableCellDataTypes() {
		return editableCellDataTypes;
	}
	public void setEditableCellDataTypes(int[] editableCellDataTypes) {
		this.editableCellDataTypes = editableCellDataTypes;
	}
	public TableEditorValidator[] getValidators() {
		return validators;
	}
	
	public void setValidators(TableEditorValidator[] validators) {
		this.validators = validators;
	}
	public int getCell(int index) {
		return editableCells[index];
	}
	public int getCellCount() {
		return editableCells.length;
	}
	public int getType(int index) {
		return editableCellDataTypes[getColumnIndex(index)];
	}
	public boolean needsValidation(int index) {
		index = getColumnIndex(index);
		if(index == -1) {
			return false;
		}
		if(validators[index] == null) {
			return false;
		} else {
			return true;
		}
	}
	/*public boolean isValid(int i, String val) {
		int index = getColumnIndex(i);
		if(needsValidation(i)) {
			return validators[index].validate(val);
		} else {
			return true;
		}
	}*/
	
	public boolean isValid(int i, TableItemValidationEvent evt) {
		int index = getColumnIndex(i);
		if(needsValidation(i)) {
			return validators[index].validate(evt);
		} else {
			return true;
		}
	}
	
	private int getColumnIndex(int col) {
		for(int i = 0; i < getCellCount(); i++) {
			if(getCell(i) == col) {
				return i;
			}
		}
		return -1;
	}
	/*public boolean validateRow(ItemEntryTableItem currentEditableRow) {
		for (int index : editableCells) {
			if(!isValid(index, currentEditableRow.getText(index))) {
				return false;
			}
		}
		return true;
	}*/

	public boolean validateRow(TableItem currentEditableRow) {
		TableItemValidationEvent evt = new TableItemValidationEvent();
		evt.item = currentEditableRow;
		for (int index : editableCells) {
			evt.valueToValidate =currentEditableRow.getText(index); 
			if(!isValid(index, evt)) {
				return false;
			}
		}
		return true;
	}

	public boolean isEditable(int index) {
		if(editableCells == null || editableCells.length == 0) {
			return false;
		}
		for (int cellIndex : editableCells) {
			if(cellIndex == index) {
				return true;
			}
		}
		return false;
	}

}

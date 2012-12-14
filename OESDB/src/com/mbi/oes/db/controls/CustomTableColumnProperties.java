package com.mbi.oes.db.controls;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.ResourceManager;

import com.mbi.oes.db.utils.DataTypes;
import com.mbi.oes.db.utils.TableColumnProperties.IsInitiallyHidden;
import com.mbi.oes.db.utils.TableColumnProperties.NeedsSelectAllCheckBox;
import com.mbi.oes.db.utils.TableColumnProperties.Sortable;

public class CustomTableColumnProperties {

	private String columnName;
	private int dataType = DataTypes.STRING;
	private boolean isSortable = true;
	private int width = 100;
	private Map<Integer, Listener> listenersMap = new HashMap<Integer, Listener>();
	private boolean isInitiallyHidden = false;
	private boolean needsSelectAllCheckBox = false;
	private boolean selectAllChecked = false;
	private TableColumn myColumn;
	private Logger logger_ = Logger.getLogger(CustomTableColumnProperties.class);
	
	
	public CustomTableColumnProperties(String columnName, int dataType,
			Sortable isSortable) {
		this.columnName = columnName;
		this.dataType = dataType;
		this.isSortable = isSortable.value;
	}
	

	public CustomTableColumnProperties(String columnName, int dataType) {
		this.columnName = columnName;
		this.dataType = dataType;
	}
	
	public CustomTableColumnProperties(String columnName, Sortable isSortable) {
		this.columnName = columnName;
		this.isSortable = isSortable.value;
	}
	
	public CustomTableColumnProperties(String columnName) {
		this.columnName = columnName;
	}
	
	public CustomTableColumnProperties(String columnName, IsInitiallyHidden isInitiallyHidden) {
		this.columnName = columnName;
		this.isInitiallyHidden = isInitiallyHidden.value;
	}

	public CustomTableColumnProperties(int width, String columnName) {
		this.columnName = columnName;
		this.width = width;
	}

	public CustomTableColumnProperties(int width, IsInitiallyHidden isInitiallyHidden, String columnName) {
		this.columnName = columnName;
		this.width = width;
		this.isInitiallyHidden = isInitiallyHidden.value;
	}
	
	public CustomTableColumnProperties(int width, String columnName, int dataType) {
		this.columnName = columnName;
		this.width = width;
		this.dataType = dataType;
	}
	
	public CustomTableColumnProperties(int width, String columnName, Sortable isSortable) {
		this.columnName = columnName;
		this.width = width;
		this.isSortable = isSortable.value;
	}
	
	public CustomTableColumnProperties(IsInitiallyHidden isInitiallyHidden, String columnName, int dataType) {
		this.columnName = columnName;
		this.dataType = dataType;
		this.isInitiallyHidden = isInitiallyHidden.value;
	}
	
	public CustomTableColumnProperties(int width, IsInitiallyHidden isInitiallyHidden, String columnName, int dataType) {
		this.columnName = columnName;
		this.width = width;
		this.dataType = dataType;
		this.isInitiallyHidden = isInitiallyHidden.value;
	}
	
	public CustomTableColumnProperties(int width, String columnName, int dataType, Sortable isSortable) {
		this.columnName = columnName;
		this.width = width;
		this.dataType = dataType;
		this.isSortable = isSortable.value;
	}
	
	public CustomTableColumnProperties(int width, String columnName, Sortable isSortable,
			NeedsSelectAllCheckBox needsSelectAll) {
		this.width = width;
		this.columnName = columnName;
		this.isSortable = isSortable.value;
		this.needsSelectAllCheckBox = needsSelectAll.value;
	}


	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public boolean isSortable() {
		return isSortable;
	}

	public void setSortable(boolean isSortable) {
		this.isSortable = isSortable;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public void addListener(int listenerType, Listener listener) {
		listenersMap.put(listenerType, listener);
	}
	
	
	
	public TableColumn createTableColumn(Table table) {
		if(table instanceof ItemEntryTable) {
			return createItemEntryTableColumn((ItemEntryTable)table);
		}
		
		return null;
	}

	private ItemEntryTableColumn createItemEntryTableColumn(ItemEntryTable table) {

		ItemEntryTableColumn col = new ItemEntryTableColumn(table, SWT.NONE, isSortable, dataType, isInitiallyHidden, this);
		col.setWidth(width);
		myColumn = col;
		Set<Integer> keys = listenersMap.keySet();
		for (Integer eventType : keys) {
			Listener listener = listenersMap.get(eventType);
			col.addListener(eventType, listener);
		}
		
		col.setText(columnName);
		if(needsSelectAllCheckBox ) {
			Image img = ResourceManager.getImageDescriptor(CustomTableColumnProperties.class, "/com/oes/resources/icons/unchecked.png").createImage();
			col.setImage(img);
			col.setContainsSelectAll(true);
			col.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					logger_.info("Select All clicked");
					processSelectAllCheckBox();
				}
			});
		}
		
		return col;
	
	}
	

	protected void processSelectAllCheckBox() {
		Image img = null;
		logger_.info("Entry: Is checked "+selectAllChecked);
		if (selectAllChecked) {
			//SelectAll button is unchecked
			Table myTable = myColumn.getParent();
			if(myTable != null && myTable instanceof ItemEntryTable) {
					ItemEntryTable table = (ItemEntryTable)myTable;
					table.unCheckAll(true);
			}
			selectAllChecked = false;
		} else {
			//SelectAll button is Checked
			Table myTable = myColumn.getParent();
			if(myTable != null && myTable instanceof ItemEntryTable) {
				logger_.info("Base Table is ItemEntryTable");
				ItemEntryTable table = (ItemEntryTable)myTable;
				table.checkAll(true);
			}
			selectAllChecked = true;
		}

		logger_.info("Exit: Is checked "+selectAllChecked);
	}


	public void setSelectAllChecked(boolean isChecked) {
		this.selectAllChecked = isChecked;
	}
	public void markAsEditable() {
		myColumn.setText("* " + myColumn.getText());
	}
	
}

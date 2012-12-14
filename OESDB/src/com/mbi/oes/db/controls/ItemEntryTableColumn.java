package com.mbi.oes.db.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableColumn;

import com.mbi.oes.db.utils.DataTypes;


public class ItemEntryTableColumn extends TableColumn {
	private ItemEntryTable parent;
	private int currentWidth = 100;
	private boolean sortEnabled_ = false;
	private int sortingOrder_ = -1 ;
	private int dataType_ = DataTypes.STRING;
	private boolean isInitallyHidden_ = false;
	private boolean containsSelectAll_ = false;
	private CustomTableColumnProperties myProperties;

	public ItemEntryTableColumn(ItemEntryTable parent, int style, boolean sortEnabled) {
		super(parent, style);
		this.parent = parent;
		this.sortEnabled_ = sortEnabled;
		setMoveable(true);
		addListeners();
	}
	
	public ItemEntryTableColumn(ItemEntryTable parent, int style, boolean sortEnabled, int dataType) {
		super(parent, style);
		this.parent = parent;
		this.sortEnabled_ = sortEnabled;
		this.dataType_ = dataType;
		setMoveable(true);
		addListeners();
	}

	public ItemEntryTableColumn(ItemEntryTable itemTable, int style) {
		super(itemTable, style);
		this.parent = itemTable;
		setMoveable(true);
		addListeners();
	}
	
	public ItemEntryTableColumn(ItemEntryTable parent, int style, int index) {
		super(parent, style, index);
		this.parent = parent;
		setMoveable(true);
		addListeners();
	}

	public ItemEntryTableColumn(ItemEntryTable parent, int style, boolean isSortable,
			int dataType, boolean isInitiallyHidden) {
		super(parent, style);
		this.parent = parent;
		this.sortEnabled_ = isSortable;
		this.dataType_ = dataType;
		this.isInitallyHidden_ = isInitiallyHidden;
		setMoveable(true);
		addListeners();
	}
	
	public ItemEntryTableColumn(ItemEntryTable parent, int style, boolean isSortable,
			int dataType, boolean isInitiallyHidden, CustomTableColumnProperties props) {
		super(parent, style);
		this.parent = parent;
		this.sortEnabled_ = isSortable;
		this.dataType_ = dataType;
		this.isInitallyHidden_ = isInitiallyHidden;
		this.myProperties = props;
		setMoveable(true);
		addListeners();
	}
	

	public CustomTableColumnProperties getProperties() {
		return myProperties;
	}

	public void setMyProperties(CustomTableColumnProperties myProperties) {
		this.myProperties = myProperties;
	}

	@Override
	protected void checkSubclass() {
	}

	public void hide() {
		parent.setRedraw(false);
		currentWidth = getWidth();
		setWidth(0);
		setResizable(false);
		parent.setRedraw(true);
	}

	public void display() {
		parent.setRedraw(false);
		setWidth(currentWidth);
		setResizable(true);
		parent.setRedraw(true);
	}

	public boolean sortEnabled() {
		return sortEnabled_ ;
	}

	public int getSortingOrder() {
		return sortingOrder_ -1 ;
	}

	public void setSortingOrder(int sortOrder) {
		sortingOrder_ = sortOrder;		
	}

	public int getDataType() {
		return dataType_ ;
	}

	public boolean isInitallyHidden() {
		return isInitallyHidden_ ;
	}
	
	public void setInitiallyHidden(boolean initiallyHidden) {
		this.isInitallyHidden_ = initiallyHidden;
	}
	
	public void addListeners() {
		this.addListener(SWT.Move, new Listener() {
			
			@Override
			public void handleEvent(Event ev) {
				//System.out.println("Column Moving event: "+ev.widget+" "+ev.detail);
			}
		});
	}

	public boolean containsSelectAll() {
		return containsSelectAll_;
	}
	
	public void setContainsSelectAll(boolean b) {
		containsSelectAll_ = b;
	}
}

package com.mbi.oes.db.controls;

public interface TableItemChangeListener {
	void itemStart(TableItemChangeEvent e) ;
	void itemChanged(TableItemChangeEvent e) ;
	void itemChangeCompleted(TableItemChangeEvent e);
	void focusLost(TableItemChangeEvent e);
	void itemRemoved();
	void columnModified(TableItemChangeEvent e);
	void selectAllChecked();
	void selectAllUnchecked();
	void itemChecked(TableItemChangeEvent e);
	void itemUnchecked(TableItemChangeEvent e);
}

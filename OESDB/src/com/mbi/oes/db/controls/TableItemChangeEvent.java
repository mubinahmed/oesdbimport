package com.mbi.oes.db.controls;

import org.eclipse.swt.widgets.TableItem;

import com.mbi.oes.db.utils.StringPool;


public class TableItemChangeEvent {

	public int detail = -1;
	public boolean valueChanged = false;
	public boolean validValue = true;
	public String data = StringPool.BLANK;
	public int currentRowNumber = -1;
	public TableItem item = null;
	public int rowNumber = -1;
}

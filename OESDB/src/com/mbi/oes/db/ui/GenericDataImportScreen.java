package com.mbi.oes.db.ui;

import com.mbi.oes.listeners.PageChangedListener;
import org.eclipse.swt.widgets.Composite;

public abstract class GenericDataImportScreen extends Composite{
	public GenericDataImportScreen(Composite parent, int style) {
		super(parent, style);
	}

	public abstract void addPageChangeListener(PageChangedListener listener);
}

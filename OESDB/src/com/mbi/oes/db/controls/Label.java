package com.mbi.oes.db.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

public class Label extends org.eclipse.swt.widgets.Label{

	public Label(Composite parent, int style) {
		super(parent, style);
		setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
	}

	@Override
	protected void checkSubclass() {
		//Allow subclassing.
	}
}

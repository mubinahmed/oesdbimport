package com.mbi.oes.db.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class CustomLabel extends Label {

	public CustomLabel(Composite parent, int style) {
		super(parent, style);
		setDefaultFormatting();
	}

	private void setDefaultFormatting() {
		setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
	}

	@Override
	protected void checkSubclass() {
		//Do nothing
	}
}

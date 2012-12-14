package com.mbi.oes.db.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mbi.oes.db.utils.FormatUtil;
import com.mbi.oes.db.utils.StringPool;

public class CustomLabelAsField extends Label {

	public CustomLabelAsField(Composite parent, int style) {
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

	public double getDoubleValue() {
		try {
			return Double.parseDouble(getText());
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	public float getFloatValue() {
		try {
			return Float.parseFloat(getText());
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	public double getCurrencyAsDouble() {
		if(getText().length() == 0) {
			return 0;
		}
		try {
			double d = FormatUtil.getValueFromCurrency(getText());
			return d;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public float getCurrencyAsFloat() {
		if(getText().length() == 0) {
			return 0;
		}
		try {
			float d = (float)FormatUtil.getValueFromCurrency(getText());
			return d;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public void setAsCurrency(double d) {
		setText(FormatUtil.formatCurrency(d));
	}

	public void clear() {
		setText(StringPool.BLANK);
	}
}

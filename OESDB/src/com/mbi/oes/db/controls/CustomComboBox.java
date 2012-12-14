package com.mbi.oes.db.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class CustomComboBox extends Combo{

	Control next;
	Control previous;
	Label myLabel_;
	private boolean isMandatory_ = false;
	
	public CustomComboBox(Composite parent, int style) {
		super(parent, style | SWT.READ_ONLY);
		setDefaultFormatting();
	}
	
	//TODO: By default all comboboxes are non-editable. Use below constructor for creating editable combo-boxes.
	//Pass in some dummy value for 3rd parameter.
	public CustomComboBox(Composite parent, int style, int readOnly) {
		super(parent, style);
		setDefaultFormatting();
	}
	
	private void setDefaultFormatting() {
		setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.BOLD));
	}
	
	public CustomComboBox(Composite parent, int style, boolean mandatory) {
		this(parent, style);
		isMandatory_ = mandatory;
		setDefaultFormatting();
	}
	

	public CustomComboBox(Composite parent, int style, boolean mandatory, Control next, Control previous) {
		this(parent, style);
		isMandatory_ = mandatory;
		this.next = next;
		this.previous = previous;
		setDefaultFormatting();
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if(hasLabel()) {
			myLabel_.setVisible(visible);
		}
	}

	public String getValue() {
		if(getSelectionIndex() == -1) {
			return "";
		} else {
			return getItem(getSelectionIndex());
		}
	}
	
	public void addKeyListener(final Control allControls[]){
		
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == 13 || e.keyCode == SWT.ESC) {
					for (int i = 0; i < allControls.length; i++) {
						String controlClassName = allControls[i].getClass().getName();
						if("com.oes.controls.CustomComboBox".equalsIgnoreCase(controlClassName)){
							CustomComboBox text = (CustomComboBox)allControls[i];
							if(text == CustomComboBox.this) {
								previous = (i >0 ) ? allControls[i-1] : null;
								next = (i+1 < allControls.length) ? allControls[i+1] : null;
								break;
							}
						}
					}
				}
				if(e.keyCode == 13){
					
					if (next != null) {
						next.setFocus();
					}
				} else if(e.keyCode == SWT.ESC) {
					if(previous != null){
						previous.setFocus();
					}
				}
			}
		});
	}
	
	
	@Override
	protected void checkSubclass() {
		//Do nothing
	}

	public boolean hasLabel() {
		return myLabel_ != null;
	}

	public Control getLabel() {
		return myLabel_;
	}
	
	public void setLabel(Label label) {
		myLabel_ = label;
	}

	public boolean isMandatory() {
		return isMandatory_ ;
	}
}

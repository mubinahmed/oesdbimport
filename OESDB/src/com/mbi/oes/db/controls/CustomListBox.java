package com.mbi.oes.db.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

public class CustomListBox extends List{

	Control next;
	Control previous;
	private boolean isPrimary = false;
	private boolean mandatory = false;
	private Label myLabel = null;
	
	public CustomListBox(Composite parent, int style) {
		super(parent, style);
	}
	
	public CustomListBox(Composite parent, int style, boolean mandatory) {
		super(parent, style);
		this.mandatory  = mandatory;
	}
	
	public void addKeyListener(final Control allControls[]){
		
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == 13 || e.keyCode == SWT.ESC) {
					for (int i = 0; i < allControls.length; i++) {
						String controlClassName = allControls[i].getClass().getName();
						if("com.oes.controls.CustomListBox".equalsIgnoreCase(controlClassName)){
							CustomListBox text = (CustomListBox)allControls[i];
							if(text == CustomListBox.this) {
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

	public void setAsPrimary() {
		isPrimary = true;		
	}
	
	public boolean isPrimary(){
		return isPrimary;
	}

	public boolean hasLabel() {
		if(myLabel != null) {
			return true;
		} else {
			return false;
		}
	}

	public Label getLabel() {
		return myLabel;
	}
	
	public boolean isMandatory(){
		return mandatory;
	}

	public void setLabel(Label label) {
		myLabel = label;		
	}
}

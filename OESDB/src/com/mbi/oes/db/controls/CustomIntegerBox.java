package com.mbi.oes.db.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.mbi.oes.db.utils.FormatUtil;

public class CustomIntegerBox extends CustomTextBox {

	protected int rangeFrom_ = -1;
	protected int rangeTo_ = -1;
	private boolean checkRange_ = false;
	
	public CustomIntegerBox(Composite composite, int style) {
		super(composite, style);
		addConstraint();
	}


	public CustomIntegerBox(Composite composite, int style, boolean isMandatory) {
		super(composite, style, isMandatory);
		addConstraint();
	}

	public CustomIntegerBox(Composite composite, int style, Control next) {
		super(composite, style, next);
		addConstraint();
	}

	public CustomIntegerBox(Composite composite, int style, Control next,
			boolean isMandatory) {
		super(composite, style, next, isMandatory);
		addConstraint();
	}

	public CustomIntegerBox(Composite composite, int style,
			boolean isMandatory, String dataType) {
		super(composite, style, isMandatory, dataType);
		addConstraint();
	}

	public CustomIntegerBox(Composite composite, int style, Control next,
			Control previous) {
		super(composite, style, next, previous);
		addConstraint();
	}

	public CustomIntegerBox(Composite composite, int style, Control next,
			Control previous, boolean isMandatory) {
		super(composite, style, next, previous, isMandatory);
		addConstraint();
	}

	public CustomIntegerBox(Composite composite, int style, Control next,
			Control previous, boolean validate,
			boolean isMandatory) {
		super(composite, style, next, previous, "integer", validate, isMandatory);
		addConstraint();
	}

	public CustomIntegerBox(Composite composite, int style,
			boolean validate, boolean isMandatory) {
		super(composite, style, "integer", validate, isMandatory);
		addConstraint();
	}

	@Override
	protected void addConstraint() {
		this.addVerifyKeyListener(new VerifyKeyListener() {
			public void verifyKey(VerifyEvent event) {
				if (byPassVerifyListener_) {
					// We want to by-pass this check
					event.doit = true;
					return;
				}
				
				if (event.keyCode != 13 && event.keyCode != SWT.ESC) {
					if (getSelectionText().length() == getText().length()) { //Overwriting existing text of the widget
						setText("");
					}
				}
				
				// Assume we don't allow it
				event.doit = false;

				// Get the character typed
				char myChar = event.character;
				boolean allowed = false;
				// Allow 0-9
				if (Character.isDigit(myChar) && getText().length() < length_
						&& validRange(getText()+myChar)) {
					//event.doit = true;
					allowed  = true;
				}

				// Allow backspace
				if (myChar == '\b' || event.keyCode == SWT.ARROW_LEFT || event.keyCode == SWT.ARROW_RIGHT) {
					allowed = true;

				}
				
				//Check Range
				if(checkRange_ && !allowed) {
					String txt = getText();
					if(Character.isDigit(myChar) || myChar == '.') {
						txt = txt + myChar;
					}
					int d = 0;
					try {
						d = Integer.parseInt(txt);
					} catch (NumberFormatException e) {
						System.out.println("NFE in CustomIntegerBox: "+txt);
						d = 0;
					}
					if( d >= rangeFrom_ && d <= rangeTo_) {
						allowed = true;
					} else {
						allowed = false;
					}
				}
				
				event.doit = allowed;
			}
		});
	}
	
	public void setRange(int rangeFrom, int rangeTo){
		this.rangeFrom_ = rangeFrom;
		this.rangeTo_ = rangeTo;
	}
	
	public int getRangeFrom() {
		return rangeFrom_;
	}

	public int getRangeTo() {
		return rangeTo_;
	}
	
	protected boolean validRange(String newValue) {
		if(rangeFrom_ == -1 || rangeTo_ == -1)
			return true;
		if(newValue.trim().length() == 0) {
			return false;
		}
		//!"".equalsIgnoreCase(newValue) && 
		if(Integer.parseInt(newValue) <= rangeTo_ && Integer.parseInt(newValue) >= rangeFrom_){
			return true;
		}
		return false;
	}

	public int getValue() {
		return FormatUtil.getIntegerValue(getText());
	}


	public double getFromRange() {
		return rangeFrom_;
	}
}

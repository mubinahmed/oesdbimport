package com.mbi.oes.db.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.mbi.oes.db.utils.FormatUtil;
import com.mbi.oes.db.utils.StringUtils;


public class CustomFloatBox extends CustomTextBox {

	private int precision = 2;
	private float rangeFrom_;
	private float rangeTo_;
	private boolean checkRange_ = false;
	
	public CustomFloatBox(Composite composite, int style) {
		super(composite, style);
		addConstraint();
	}

	public CustomFloatBox(Composite composite, int style, String dataType) {
		super(composite, style, dataType);
		addConstraint();
	}

	public CustomFloatBox(Composite composite, int style, boolean isMandatory) {
		super(composite, style, isMandatory);
		addConstraint();
	}

	public CustomFloatBox(Composite composite, int style, Control next) {
		super(composite, style, next);
		addConstraint();
	}

	public CustomFloatBox(Composite composite, int style, Control next,
			boolean isMandatory) {
		super(composite, style, next, isMandatory);
		addConstraint();
	}

	public CustomFloatBox(Composite composite, int style, boolean isMandatory,
			String dataType) {
		super(composite, style, isMandatory, dataType);
		addConstraint();
	}

	public CustomFloatBox(Composite composite, int style, Control next,
			Control previous) {
		super(composite, style, next, previous);
		addConstraint();
	}

	public CustomFloatBox(Composite composite, int style, Control next,
			Control previous, boolean isMandatory) {
		super(composite, style, next, previous, isMandatory);
		addConstraint();
	}

	public CustomFloatBox(Composite composite, int style, Control next,
			Control previous, String dataType, boolean validate) {
		super(composite, style, next, previous, dataType, validate);
		addConstraint();
	}

	public CustomFloatBox(Composite composite, int style, Control next,
			Control previous, String dataType, boolean validate,
			boolean isMandatory) {
		super(composite, style, next, previous, dataType, validate, isMandatory);
		addConstraint();
	}

	public CustomFloatBox(Composite composite, int style, String dataType,
			boolean validate, boolean isMandatory) {
		super(composite, style, dataType, validate, isMandatory);
		addConstraint();
	}

	public double getFromRange() {
		return rangeFrom_;
	}
	
	public double getToRange() {
		return rangeTo_;
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
				boolean allowed = false;
				// Get the character typed
				char myChar = event.character;
				// Allow 0-9
				if ( ((Character.isDigit(myChar) && getText().length() < length_)
						|| ((StringUtils.countMatches(getText(), ".") == 0 && myChar == '.')))
						&& validPrecision()) {
					allowed = true;
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
					float d = 0;
					try {
						d = Float.parseFloat(txt);
					} catch (NumberFormatException e) {
						System.out.println("NFE in CustomFloatBox: "+txt);
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
	
	public void setRange(float from, float to) {
		rangeFrom_ = from;
		rangeTo_ = to;
		checkRange_ = true;
	}
	
	public double getValue() {
		return FormatUtil.getFloatValue(getText());
	}
	protected boolean validPrecision() {
		String text = getText();
		int x = text.indexOf('.');
		int y = text.length();

		if(StringUtils.countMatches(text, ".") == 0) {
			return true;
		} else if(StringUtils.countMatches(text, ".") == 1 && y-x <= precision) {
			return true;
		} else {
			return false;
		}
	}

	public void setPrecision(int i) {
		precision = i;
	}

	public void setValue(double d) {
		setText(FormatUtil.format(d));		
	}
}

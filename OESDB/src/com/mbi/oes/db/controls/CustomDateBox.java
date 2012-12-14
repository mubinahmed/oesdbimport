package com.mbi.oes.db.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.widgets.Composite;

public class CustomDateBox extends CustomTextBox{

	int numOfDays[] = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	public CustomDateBox(Composite composite, int style) {
		super(composite, style);
		length_ = 10;
		maxSize_ = 10;
		minSize_ = 10;
		validationErrorMsg = "Invalid date entered!!!";
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
				// Assume we don't allow it
				event.doit = false;

				// Get the character typed
				char myChar = event.character;
				// Allow backspace
				if (myChar == '\b' || event.keyCode == SWT.ARROW_LEFT || event.keyCode == SWT.ARROW_RIGHT) {
					event.doit = true;
					return;
				}
				// Allow 0-9
				String text = getText();
				if(text.length() == length_) {
					return;
				}
				//System.out.println("text is "+text+" :"+text.length());
				switch(text.length()){
					case 0:
						if(Character.isDigit(myChar) && Integer.parseInt(myChar+"") <= 1) {
							event.doit = true;
						}
						break;
					case 1:
						if(Character.isDigit(myChar) && Integer.parseInt(text+myChar) <= 12 && Integer.parseInt(text+myChar) > 0) {
							event.doit = true;
						}
						break;
					case 2:
					case 5:
						if(myChar == '-') {
							event.doit = true;
						}
						break;
					case 3:
						if(Character.isDigit(myChar) && Integer.parseInt(myChar+"") <= 3) {
							event.doit = true;
						}
						break;
					case 4:
						String num = Integer.parseInt(text.charAt(3)+"")+""+myChar;
						int mon = Integer.parseInt(text.substring(0, 2));
						if(Character.isDigit(myChar) && Integer.parseInt(num) <= numOfDays[mon-1]) {
							event.doit = true;
						}
						break;
					case 6:
					case 7:
					case 8:
					case 9:
						if(Character.isDigit(myChar)) {
							event.doit = true;
						}
						break;
				}			
			}
		});
	}

	protected boolean validDate() {
		return true;
	}
}

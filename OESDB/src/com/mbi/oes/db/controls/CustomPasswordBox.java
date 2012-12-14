package com.mbi.oes.db.controls;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.mbi.oes.db.utils.OESUtils;
import com.mbi.oes.db.utils.StringPool;


public class CustomPasswordBox extends CustomTextBox {

	private String password = StringPool.BLANK;
	public CustomPasswordBox(Composite composite, int style) {
		super(composite, style);
		addConstraint();
	}


	public CustomPasswordBox(Composite composite, int style, boolean isMandatory) {
		super(composite, style, isMandatory);
		addConstraint();
	}

	public CustomPasswordBox(Composite composite, int style, Control next) {
		super(composite, style, next);
		addConstraint();
	}

	public CustomPasswordBox(Composite composite, int style, Control next,
			boolean isMandatory) {
		super(composite, style, next, isMandatory);
		addConstraint();
	}

	public CustomPasswordBox(Composite composite, int style,
			boolean isMandatory, String dataType) {
		super(composite, style, isMandatory, dataType);
		addConstraint();
	}

	public CustomPasswordBox(Composite composite, int style, Control next,
			Control previous) {
		super(composite, style, next, previous);
		addConstraint();
	}

	public CustomPasswordBox(Composite composite, int style, Control next,
			Control previous, boolean isMandatory) {
		super(composite, style, next, previous, isMandatory);
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
				// Allow 0-9
				if (OESUtils.validPasswordChar(myChar)) {
				    event.doit = false;
				    password = password + myChar;
				    event.text = "*";
				    String pwdText = StringUtils.defaultIfBlank(CustomPasswordBox.this.getText(), "") ;
				    CustomPasswordBox.this.setText(pwdText+ "*");
				    CustomPasswordBox.this.setCaretOffset(pwdText.length()+1);
				}

				// Allow backspace
				if (myChar == '\b') {
					event.doit = true;
					if(password.length() >0) {
					    password = password.substring(0, password.length() - 1);
					}

				}
				
			}
		});
	}


	public String getPassword() {
	    return password;
	}


	public void setPassword(String password) {
	    this.password = password;
	    setText(StringUtils.leftPad("", password.length(), "*"));
	}
	
	@Override
	public void clear() {
		super.clear();
		password = StringPool.BLANK;
	}
}

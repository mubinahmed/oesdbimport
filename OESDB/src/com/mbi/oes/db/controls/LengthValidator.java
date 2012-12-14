package com.mbi.oes.db.controls;

import org.eclipse.jface.dialogs.IInputValidator;

public class LengthValidator implements IInputValidator {
	  /**
	   * Validates the String. Returns null for no error, or an error message
	   * 
	   * @param newText the String to validate
	   * @return String
	   */
	  public String isValid(String newText) {
	    int len = newText.length();

	    // Determine if input is too short or too long
	    if (len < 5) return "Too short";
	    if (len > 8) return "Too long";

	    // Input must be OK
	    return null;
	  }
	}

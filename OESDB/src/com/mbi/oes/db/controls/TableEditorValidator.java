package com.mbi.oes.db.controls;

public interface TableEditorValidator {
	//public boolean validate(String value);
	public boolean validate(TableItemValidationEvent event);
}

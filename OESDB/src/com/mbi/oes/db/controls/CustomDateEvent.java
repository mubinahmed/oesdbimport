package com.mbi.oes.db.controls;


public class CustomDateEvent {

	public static enum Values { YEAR, DATE, MONTH }

	public Values data;
	
	@Override
	public String toString() {
		return data.name();
	}
}

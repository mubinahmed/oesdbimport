package com.mbi.oes.db.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatesUtils {

	public static List<Date> monthStartDates() {
		List<Date> retList = new ArrayList<Date>();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		for(int i=0; i<12; i++) {
			c.set(Calendar.MONTH, i);
			retList.add(c.getTime());
		}
		return retList;
	}
	
	public static List<Date> monthEndDates() {
		List<Date> retList = new ArrayList<Date>();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		for(int i=0; i<12; i++) {
			c.set(Calendar.MONTH, i);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			retList.add(c.getTime());
		}
		return retList;
	}
	
	public static List<Date> monthStartDates(String year) {
		List<Date> retList = new ArrayList<Date>();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(year));
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		for(int i=0; i<12; i++) {
			c.set(Calendar.MONTH, i);
			retList.add(c.getTime());
		}
		return retList;
	}
	
	public static List<Date> monthEndDates(String year) {
		List<Date> retList = new ArrayList<Date>();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(year));
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		for(int i=0; i<12; i++) {
			c.set(Calendar.MONTH, i);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			retList.add(c.getTime());
		}
		return retList;
	}
}

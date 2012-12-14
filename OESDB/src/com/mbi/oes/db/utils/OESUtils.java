package com.mbi.oes.db.utils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;


public class OESUtils {


	
	/*public static String getStatus(String status) {
		String str = statusMap.get(status);
		if(StringUtils.isNotBlank(str)) {
			return str;
		} 
		return status;
	}*/

	public static long getNumberOfDaysFrom(Date date) {
		return (new java.util.Date().getTime() - date.getTime())/(24*60*60*1000);
	}



	public static String getKeyFromMap(Map<String, String> map, String value) {
		Set<String> keys = map.keySet();
		for (String key : keys) {
			if (map.get(key).equalsIgnoreCase(value)) {
				return key;
			}
		}
		return null;
	}

	public static boolean validPasswordChar(char ch) {
		if (Character.isLetterOrDigit(ch) || ch == '@' || ch == '_'
				|| ch == '$' || ch == '#' || ch == '!' || ch == '%')
			return true;
		return false;
	}

	public static Point centerWindow(Shell shell) {
	    Monitor m = Display.getCurrent().getPrimaryMonitor();
	    Rectangle bounds = m.getBounds();
	    Rectangle rect = shell.getBounds();
	    int x = bounds.x + (bounds.width - rect.width)/2;
	    int y = bounds.y + (bounds.height - rect.height)/2;
	    return new Point(x, y);
	}


	
	public static String toCSVString(List<String> stringList, String delimiter) {
		StringBuffer csvString = new StringBuffer();
			for(String str : stringList){
				csvString.append(str).append(delimiter);
			}
			if(csvString.length() > 1){
				csvString.deleteCharAt(csvString.length() - 1);
			}
		return csvString.toString();
	}
	
	
	
	public static void main(String[] arg){
		//toCSVString(new String[]{"1234"}, StringPool.COMMA);
	}

	public static boolean isDateBetween(Date currentDate, Date fromDate, Date toDate) {
		if(fromDate == null || toDate == null){
			return false;
		}
		Calendar current = Calendar.getInstance();
		current.setTime(currentDate);
		
		Calendar from = Calendar.getInstance();
		from.setTime(fromDate);
		
		Calendar to = Calendar.getInstance();
		to.setTime(toDate);
		
		if(current.after(from) && current.before(to)){
			return true;
		}
		return false;
	}
	
	public static List<Integer> getAsList(int array[]) {
		List<Integer> retVal = new ArrayList<Integer>(array.length);
		for (Integer integer : array) {
			retVal.add(integer);
		}
		return retVal;
	}
}

package com.mbi.oes.db.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class FormatUtil {

	static NumberFormat doubleFormatter = new DecimalFormat("###0.00");
	static NumberFormat currencyFormatter = new DecimalFormat("$ #,#,##,##0.00");
	//MM-dd-yyyy
	static final SimpleDateFormat displayDateFormat = new SimpleDateFormat("MM/dd/yyyy");
	static final SimpleDateFormat pdfDisplayDateFormat = new SimpleDateFormat("MM/dd/yy");
	static final SimpleDateFormat qbDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static String formatCurrency(double d) {
		return currencyFormatter.format(d);
	}

	public static double getValueFromCurrency(String amount) {
		amount = amount.replaceAll("\\$", StringPool.BLANK);
		amount = amount.replaceAll(",", StringPool.BLANK);
		amount = amount.replaceAll(" ", StringPool.BLANK);
		return Double.parseDouble(amount);
	}

	public static String format(Integer d) {
		if(d == null) {
			return StringPool.BLANK + 0;
		}
		return StringPool.BLANK + d.intValue();
	}
	
	public static String format(double d) {
		try {
			return doubleFormatter.format(d);
		} catch (Exception e) {
			return "0";
		}
	}

	public static String format(Double d) {
		if(d == null){
			return "0.0";
		}
		try {
			return doubleFormatter.format(d);
		} catch (Exception e) {
			return "0";
		}
	}
	

	public static float getFloatValue(String string) {
		float f = 0;

		try {
			f = Float.parseFloat(string);
		} catch (NumberFormatException e) {
		} catch (NullPointerException e) {
		}
		return f;
	}

	public static int getIntegerValue(String string) {
	    int f = 0;
	    try {
	    	f = Integer.parseInt(string);
	    }catch (NumberFormatException e) {
		
	    }catch (NullPointerException npe) {
	    	
	    }
	    return f;
	}
	
	public static double getDoubleValue(String string) {
		double f = 0;
		try {
			f = Double.parseDouble(string);
		} catch (NumberFormatException e) {

		} catch (NullPointerException e) {

		}
		return f;
	}

	public static Date getDateValue(String dateStr) {
		if (dateStr == null || dateStr.length() < 10) {
			return null;
		}
		try {
			return displayDateFormat.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static int getIntValue(String string) {
		int i = 0;
		try {
			i = Integer.parseInt(string);
		} catch (NumberFormatException e) {

		} catch (NullPointerException e) {

		}
		return i;
	}

	public static String format(Date date) {
		if (date == null) {
			return StringPool.BLANK;
		} else {
			return displayDateFormat.format(date);
		}
	}
	
	public static String formatDateForQB(Date date) {
		if (date == null) {
			return StringPool.BLANK;
		} else {
			return qbDateFormat.format(date);
		}
	}

	public static Double formatPrecision(double d) {
		return FormatUtil.getDoubleValue(doubleFormatter.format(d));
	}
	public static String formatForPDF(Date date) {
		if (date == null) {
			return StringPool.BLANK;
		} else {
			return pdfDisplayDateFormat.format(date);
		}
	}

	public static String formatListAsCSV(List<? extends Object> invoiceNumbers) {
		String returnVal = invoiceNumbers.toString();
		returnVal = returnVal.replace("[", "");
		returnVal = returnVal.replace("]", "");
		return returnVal;
	}
	
	public static double parseDouble(String d) {
		if(StringUtils.isBlank(d)) {
			return 0;
		}
		Double retVal = new Double(0);
		try {
			retVal = Double.parseDouble(d);
		} catch (NumberFormatException e) {
			return 0;
		}
		return retVal;
	}

	public static String formatTime(Date datetime) {
		if(datetime == null) {
			return StringPool.BLANK;
		}
		final SimpleDateFormat timeFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		return timeFormat.format(datetime);
	}
}

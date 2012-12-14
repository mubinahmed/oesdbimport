package com.mbi.oes.db.utils;

import java.util.List;

import org.eclipse.swt.widgets.TableItem;

public class Validator {

    private static final int _CHAR_LOWER_CASE_BEGIN = 97;

    private static final int _CHAR_LOWER_CASE_END = 122;

    private static final int _CHAR_UPPER_CASE_BEGIN = 65;

    private static final int _CHAR_UPPER_CASE_END = 90;

    private static final int _DIGIT_BEGIN = 48;

    private static final int _DIGIT_END = 57;

    public static boolean isNotEmptyList(List list) {
	if (list != null && list.size() > 0)
	    return true;
	else
	    return false;
    }

    public static boolean isEmptyList(List list) {
	return !isNotEmptyList(list);
    }

    public static boolean isSingleValueList(List list) {
	return (list != null && list.size() == 1);
    }

    public static boolean isMultiValueList(List list) {
	return (list != null && list.size() > 1);
    }

    public static boolean hasData(String key, TableItem tableItem) {
	String data = (String) tableItem.getData(key);
	if (data != null && data.equalsIgnoreCase("true")) {
	    return true;
	}
	return false;
    }

    public static boolean isNotNull(String s) {
	return !isNull(s);
    }

    public static boolean isNull(String s) {
	if (s == null) {
	    return true;
	}

	int counter = 0;

	for (int i = 0; i < s.length(); i++) {
	    char c = s.charAt(i);

	    if (c == CharPool.SPACE) {
		continue;
	    } else if (counter > 3) {
		return false;
	    }

	    if (counter == 0) {
		if (c != CharPool.LOWER_CASE_N) {
		    return false;
		}
	    } else if (counter == 1) {
		if (c != CharPool.LOWER_CASE_U) {
		    return false;
		}
	    } else if ((counter == 2) || (counter == 3)) {
		if (c != CharPool.LOWER_CASE_L) {
		    return false;
		}
	    }

	    counter++;
	}

	if ((counter == 0) || (counter == 4)) {
	    return true;
	}

	return false;
    }

    public static boolean isChar(char c) {
	int x = c;

	if (((x >= _CHAR_LOWER_CASE_BEGIN) && (x <= _CHAR_LOWER_CASE_END))
		|| ((x >= _CHAR_UPPER_CASE_BEGIN) && (x <= _CHAR_UPPER_CASE_END))) {

	    return true;
	}

	return false;
    }

    public static boolean isDigit(String s) {
	if (isNull(s)) {
	    return false;
	}

	for (char c : s.toCharArray()) {
	    if (!isDigit(c)) {
		return false;
	    }
	}

	return true;
    }

    public static boolean isDigit(char c) {
	int x = c;

	if ((x >= _DIGIT_BEGIN) && (x <= _DIGIT_END)) {
	    return true;
	}

	return false;
    }

    public static boolean isNull(Object[] array) {
	if ((array == null) || (array.length == 0)) {
	    return true;
	} else {
	    return false;
	}
    }
}

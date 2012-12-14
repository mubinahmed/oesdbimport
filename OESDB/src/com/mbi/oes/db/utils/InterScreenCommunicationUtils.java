package com.mbi.oes.db.utils;

import java.util.HashMap;
import java.util.Map;

public class InterScreenCommunicationUtils {

	static Map<String, Object> data = new HashMap<String, Object>();
	
	public static void addMessage(String key, Object value) {
		data.put(key, value);
	}
	
	public static Object getMessage(String key){
		return data.get(key);
	}
	
	public static Object getMessage(String key, boolean resetValue){
		Object retVal = data.get(key);
		if(resetValue) {
			data.put(key, null);
		}
		return retVal;
	}
}

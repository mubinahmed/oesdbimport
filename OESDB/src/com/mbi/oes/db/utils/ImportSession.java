package com.mbi.oes.db.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class ImportSession{
	
	public static HashMap<String, Object> data = new HashMap<String, Object>();
	private static Map<String, String> tableMapping = new TreeMap<String, String>();
	private static HashMap<String, String> pkMapping = new HashMap<String, String>();
	
	static{
		tableMapping.put("Add/Update Customers", "com.oes.model.my_sql.entities.Customer");
		tableMapping.put("Add/Update Barcodes", "com.oes.model.my_sql.entities.Barcodes");
		tableMapping.put("Add/Update Open ARs", "com.oes.model.my_sql.entities.Aropen");
		tableMapping.put("Add/Update Products", "com.oes.model.my_sql.entities.Masteritem");
		tableMapping.put("Add/Update Zip Codes", "com.oes.model.my_sql.entities.Zipcodes");
		tableMapping.put("Add/Update Categories", "com.oes.model.my_sql.entities.Productcategories");
		tableMapping.put("Add/Update Subcategories", "com.oes.model.my_sql.entities.Productsubcategories");
		tableMapping.put("Add/Update Orders", "com.oes.model.my_sql.entities.Orders");
		tableMapping.put("Add/Update Order Details", "com.oes.model.my_sql.entities.Orderdetails");
		tableMapping.put("Add/Update Vendors", "com.oes.model.my_sql.entities.Vendor");
		tableMapping.put("Add/Update Users", "com.oes.model.my_sql.entities.User");
		tableMapping.put("Add/Update POs", "com.oes.model.my_sql.entities.Purchaseorder");
		tableMapping.put("Add/Update PO Details", "com.oes.model.my_sql.entities.Podetails");
		
		pkMapping.put("customer", "customerID");
		pkMapping.put("vendor", "vendorid");
		pkMapping.put("barcodes", "barcode");
		pkMapping.put("items", "ProductID");
		pkMapping.put("aropen", "InvoiceNumber");
		pkMapping.put("zipcodes", "ZipCode");
		pkMapping.put("productcategories", "category");
		pkMapping.put("purchaseorder", "ponumber");
		//pkMapping.put("podetails", "");
		pkMapping.put("orders", "OrderNumber");
		pkMapping.put("user", "LoginName");
		
	}
	
	public static String getPK(String key){
		return pkMapping.get(key.toLowerCase());
	}
	
	public static void setPK(String key, String value){
		pkMapping.put(key, value);
	}
	
	public static Object get(String key){
		return data.get(key);
	}
	
	public static void set(String key, Object value){
		data.put(key, value);
	}
	
	public static String[] getTableDescriptions(){
		java.util.Set<String> keys = tableMapping.keySet();
		HashSet<String> s = new HashSet<String>();
		s.addAll(keys);
		return s.toArray(new String[s.size()]);
	}
	public static String getTableName(String key){
		return tableMapping.get(key);
	}
}


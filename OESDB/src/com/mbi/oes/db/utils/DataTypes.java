package com.mbi.oes.db.utils;

import java.util.ArrayList;
import java.util.List;

public class DataTypes {

	private static List<String> dataTypes = new ArrayList<String>();
	/*public static final String STRING = "java.lang.String";
	public static final String INTEGER = "java.lang.Integer";
	public static final String LONG = "java.lang.Long";
	public static final String BYTE = "java.lang.Byte";
	public static final String FLOAT = "java.lang.Float";
	public static final String DOUBLE = "java.lang.Double";
	public static final String SHORT = "java.lang.Short";
	public static final String BOOLEAN = "java.lang.Boolean";
	public static final String CHARACTER = "java.lang.Character";
	public static final String INT = "int";
	public static final String LONG_P = "long";
	public static final String BYTE_P = "byte";
	public static final String FLOAT_P = "float";
	public static final String DOUBLE_P = "double";
	public static final String SHORT_P = "short";
	public static final String BOOL = "boolean";
	public static final String CHAR = "char";*/
	
	public static final int STRING = 0;
	public static final int BOOLEAN = 1;
	public static final int LONG = 2;
	public static final int FLOAT = 3;
	public static final int DATE = 4;
	public static final int INTEGER = 5;	
	public static final int BYTE = 6;	
	public static final int DOUBLE = 7;
	public static final int SHORT = 8;
	public static final int CHARACTER = 9;
	public static final int INT = 10;
	public static final int LONG_P = 11;
	public static final int BYTE_P = 12;
	public static final int FLOAT_P = 13;
	public static final int DOUBLE_P = 14;
	public static final int SHORT_P = 15;
	public static final int BOOL =16;
	public static final int CHAR = 17;
	
	static{
		dataTypes.add("java.lang.String");
		dataTypes.add("java.lang.Boolean");
		dataTypes.add("java.lang.Long");
		dataTypes.add("java.lang.Float");
		dataTypes.add("java.util.Date");
		 dataTypes.add( "java.lang.Integer");
		 dataTypes.add( "java.lang.Byte");
		 dataTypes.add( "java.lang.Double");
		 dataTypes.add( "java.lang.Short");
		 dataTypes.add( "java.lang.Character");
		 dataTypes.add( "int");
		 dataTypes.add( "long");
		 dataTypes.add( "byte");
		 dataTypes.add( "float");
		 dataTypes.add( "double");
		 dataTypes.add( "short");
		 dataTypes.add( "boolean");
		 dataTypes.add( "char");
	}
	
	public static int getDataType(String dataType){
		return dataTypes.indexOf(dataType);
	}
}

package com.app.converter.helpers;

/**
 * @author Nitin Rajan
 * @version 1.0
 * Helper class to realize XML outcome
 *
 */
public final class XMLBuilder {
	private static String NAME_KEY = "4422";
	private static String NAME_APPEND = " name=\""+NAME_KEY+"\"";
	
	private static String OBJ_OPEN_NAME = "<object"+NAME_APPEND+">";
	private static String OBJ_OPEN = "<object>";
	private static String OBJ_CLOSE = "</object>";
	
	private static String ARY_OPEN_NAME = "<array"+NAME_APPEND+">";
	private static String ARY_OPEN = "<array>";
	private static String ARY_CLOSE = "</array>";
	
	private static String BOOLEAN_OPEN_NAME = "<boolean"+NAME_APPEND+">";
	private static String BOOLEAN_OPEN = "<boolean>";
	private static String BOOLEAN_CLOSE = "</boolean>";
	
	private static String STRING_OPEN_NAME = "<string"+NAME_APPEND+">";
	private static String STRING_OPEN = "<string>";
	private static String STRING_CLOSE = "</string>";
	
	private static String NUMBER_OPEN_NAME = "<number"+NAME_APPEND+">";
	private static String NUMBER_OPEN = "<number>";
	private static String NUMBER_CLOSE = "</number>";
	
	private static String NULL_NAME = "<null"+NAME_APPEND+"/>";
	private static String NULL = "<null/>";
	
	public static String getStringXMLEq (String name, String value) {
		return new StringBuilder()
		.append(name.isEmpty() ? STRING_OPEN: STRING_OPEN_NAME.replaceFirst(NAME_KEY, name))
		.append(value)
		.append(STRING_CLOSE)
		.toString();
	}
	
	public static String getBooleanXMLEq (String name, String value) {
		return new StringBuilder()
		.append(name.isEmpty() ? BOOLEAN_OPEN: BOOLEAN_OPEN_NAME.replaceFirst(NAME_KEY, name))
		.append(value)
		.append(BOOLEAN_CLOSE)
		.toString();
	}
	
	public static String getNumberXMLEq (String name, String value) {
		return new StringBuilder()
		.append(name.isEmpty() ? NUMBER_OPEN: NUMBER_OPEN_NAME.replaceFirst(NAME_KEY, name))
		.append(value)
		.append(NUMBER_CLOSE)
		.toString();
	}
	
	public static String getArrayXMLEq (String name, String body) {
		return new StringBuilder()
		.append(name.isEmpty() ? ARY_OPEN: ARY_OPEN_NAME.replaceFirst(NAME_KEY, name))
		.append(body)
		.append(ARY_CLOSE)
		.toString();
	}
	
	public static String getObjectXMLEq (String name, String body) {
		return new StringBuilder()
		.append(name.isEmpty() ? OBJ_OPEN: OBJ_OPEN_NAME.replaceFirst(NAME_KEY, name))
		.append(body)
		.append(OBJ_CLOSE)
		.toString();
	}
	
	public static String getNullXMLEq (String name) {
		return new StringBuilder()
		.append(name.isEmpty() ? NULL: NULL_NAME.replaceFirst(NAME_KEY, name))
		.toString();
	}
}

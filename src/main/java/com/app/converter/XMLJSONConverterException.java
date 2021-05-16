package com.app.converter;

/**
 * @author Nitin Rajan
 * @version 1.0
 * Class that defines custom exception
 *
 */
public class XMLJSONConverterException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public static final String INVALID_INPUT_FILE = "Input is not a valid file. Please check";
	public static final String INVALID_JSON_FILE = "Not a valid JSON file. Please check";
	public static final String FILE_NO_CONTENTS = "No contents in the input file. Please check";

	public XMLJSONConverterException(String message) {
		super(message);
	}
}

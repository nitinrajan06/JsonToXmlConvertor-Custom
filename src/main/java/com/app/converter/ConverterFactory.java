package com.app.converter;

/**
 * @author Nitin Rajan
 * @version 1.0
 * Factory class for various converters
 *
 */
public class ConverterFactory {
	
	private XMLJSONConverterI xmlConverter;
	
	public XMLJSONConverterI getXmlConverter() {
		return xmlConverter == null ? new XMLJSONConverter() : xmlConverter;
	}

}

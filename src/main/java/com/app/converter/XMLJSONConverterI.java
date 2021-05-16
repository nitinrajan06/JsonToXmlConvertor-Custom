package com.app.converter;

import java.io.File;

/**
 * @author Nitin Rajan
 * @version 1.0
 * Interface for XML-JSON conversion
 *
 */
public interface XMLJSONConverterI {
	File convertJSONtoXML (File jsonInput, File xmlOutput) throws XMLJSONConverterException;
}

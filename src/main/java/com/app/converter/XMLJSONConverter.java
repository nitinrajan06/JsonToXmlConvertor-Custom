package com.app.converter;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.converter.helpers.XMLBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

/**
 * @author Nitin Rajan
 * @version 1.0 Implementation class for XMLJSONConverter Interface
 *
 */
public class XMLJSONConverter implements XMLJSONConverterI {

	Logger logger = LoggerFactory.getLogger(XMLJSONConverter.class.getName());
	private static String NO_NAME = "";

	protected XMLJSONConverter() {
	}

	public File convertJSONtoXML(File jsonInput, File xmlOutput) throws XMLJSONConverterException {

		if (null == jsonInput || !jsonInput.exists() || !jsonInput.isFile()) {
			throw new XMLJSONConverterException(XMLJSONConverterException.INVALID_INPUT_FILE);
		}

		// Fetches the file contents
		String contents = fetchFileContents(jsonInput);

		JsonElement baseJsonElement;

		try {
			baseJsonElement = JsonParser.parseString(contents);
		} catch (Exception e) {
			throw new XMLJSONConverterException(XMLJSONConverterException.INVALID_JSON_FILE);
		}

		String fileContent = "";

		if (baseJsonElement.isJsonArray()) {

			fileContent = processJsonArray((JsonArray) baseJsonElement, NO_NAME);

		} else if (baseJsonElement.isJsonObject()) {

			fileContent = processJsonObject((JsonObject) baseJsonElement, NO_NAME);
		}

		return writeToXMLFile(xmlOutput, fileContent);
	}

	/**
	 * Fetch the contents of a file
	 * 
	 * @param jsonInput
	 * @return
	 * @throws XMLJSONConverterException
	 */
	private String fetchFileContents(File jsonInput) throws XMLJSONConverterException {

		logger.info("Going to fetch file contents from : " + jsonInput.getAbsolutePath());
		StringBuilder builder = new StringBuilder();
		BufferedReader reader;

		try {

			reader = new BufferedReader(new FileReader(jsonInput));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			reader.close();

			if (builder.length() == 0) {
				throw new XMLJSONConverterException(XMLJSONConverterException.FILE_NO_CONTENTS);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return builder.toString();
	}

	/**
	 * Method to get XML equivalent of JSON array type
	 * 
	 * @param jsonArray
	 * @param name
	 * @return
	 * @throws XMLJSONConverterException
	 */
	private String processJsonArray(JsonArray jsonArray, String jsonAryName) throws XMLJSONConverterException {

		if (jsonArray.size() < 1) {
			logger.info("No data inside the JSON Array");
		}

		StringBuilder body = new StringBuilder();

		for (JsonElement jsonElement : jsonArray) {

			if (jsonElement.isJsonPrimitive()) {

				body.append(processPrimitiveTypes(jsonElement, NO_NAME));

			} else if (jsonElement.isJsonNull()) {

				body.append(getNullXMLEquivalent(NO_NAME));

			} else if (jsonElement.isJsonObject()) {

				body.append(processJsonObject((JsonObject) jsonElement, NO_NAME));

			} else if (jsonElement.isJsonArray()) {

				body.append(processJsonArray((JsonArray) jsonElement, NO_NAME));

			}
		}

		return XMLBuilder.getArrayXMLEq(jsonAryName, body.toString());
	}

	/**
	 * Method to get XML equivalent of JSON Object
	 * 
	 * @param jsonObject
	 * @param objName
	 * @return
	 * @throws XMLJSONConverterException
	 */
	private String processJsonObject(JsonObject jsonObject, String objName) throws XMLJSONConverterException {
		Set<Entry<String, JsonElement>> entrySet = jsonObject.entrySet();

		if (entrySet.isEmpty() == true) {
			logger.info("No data inside the JSON Object");
		}

		StringBuilder body = new StringBuilder();

		for (Entry<String, JsonElement> entry : entrySet) {

			JsonElement element = entry.getValue();

			if (element.isJsonPrimitive()) {

				body.append(processPrimitiveTypes(element, entry.getKey()));

			} else if (element.isJsonNull()) {

				body.append(getNullXMLEquivalent(entry.getKey()));

			} else if (element.isJsonArray()) {

				body.append(processJsonArray((JsonArray) element, entry.getKey()));

			} else if (element.isJsonObject()) {

				body.append(processJsonObject((JsonObject) element, entry.getKey()));
			}
		}

		return XMLBuilder.getObjectXMLEq(objName, body.toString());
	}

	/**
	 * Method to get XML equivalent of JSON primitive type
	 * 
	 * @param element
	 * @param name
	 * @return
	 */
	private String processPrimitiveTypes(JsonElement element, String primitiveTypeName) {

		JsonPrimitive jsonPrimitive = (JsonPrimitive) element;
		String result = "";

		if (jsonPrimitive.isBoolean()) {

			result = XMLBuilder.getBooleanXMLEq(primitiveTypeName, jsonPrimitive.getAsString());

		} else if (jsonPrimitive.isString()) {

			result = XMLBuilder.getStringXMLEq(primitiveTypeName, jsonPrimitive.getAsString());

		} else if (jsonPrimitive.isNumber()) {

			result = XMLBuilder.getNumberXMLEq(primitiveTypeName, jsonPrimitive.getAsString());
		}

		return result;
	}

	/**
	 * Method for handling null XML value
	 * 
	 * @param name
	 * @return
	 */
	private String getNullXMLEquivalent(String name) {
		return XMLBuilder.getNullXMLEq(name);
	}

	/**
	 * Method to write data to XML file
	 * 
	 * @param fileContent
	 * @return
	 */
	private File writeToXMLFile(File xmlFile, String fileContent) {

		/**
		 * If no xml_output file is provided, a default file is created in the execution
		 * path and the contents are written to it.
		 */
		/*if (null == xmlFile || !xmlFile.exists() || !xmlFile.isFile()) {
			xmlFile = new File("xml_output.xml");
		}*/

		logger.info("Going to write file contents to : " + xmlFile.getAbsolutePath());

		BufferedOutputStream writer;

		try {
			writer = new BufferedOutputStream(new FileOutputStream(xmlFile));
			writer.write(fileContent.getBytes());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return xmlFile;
	}
}

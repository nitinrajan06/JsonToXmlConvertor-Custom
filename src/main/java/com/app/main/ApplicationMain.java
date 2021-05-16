package com.app.main;

import java.io.File;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.converter.ConverterFactory;
import com.app.converter.XMLJSONConverterException;
import com.app.converter.XMLJSONConverterI;

/**
 * @author Nitin Rajan
 * @version 1.0 Application Main Class
 *
 */
public class ApplicationMain {

	public static void main(String[] args) {
		ApplicationMain appMain = new ApplicationMain();
		appMain.execute();
	}
	
	private void execute() {

		Scanner scanner = new Scanner(System.in);

		System.out.println("**********************************************");
		System.out.println("            JSON to XML Converter             ");
		System.out.println("**********************************************");
		System.out.println("Enter the file path for JSON input file :");
		String inputFilePath = scanner.nextLine();
		scanner.close();

		ConverterFactory converterFactory = new ConverterFactory();
		XMLJSONConverterI converter = converterFactory.getXmlConverter();

		File inputFile = new File(inputFilePath.trim());
		StringBuilder xmlPath = new StringBuilder().append(inputFile.getParent())
				.append(File.separator)
				.append(inputFile.getName().replaceFirst("json", "xml"));

		try {
			converter.convertJSONtoXML(inputFile, new File(xmlPath.toString()));
			System.out.println("\nPlease check the XML output in the following path : " + xmlPath);
			
		} catch (XMLJSONConverterException e) {
			System.out.println("ERROR : " + e.getMessage());
		}
	}
}

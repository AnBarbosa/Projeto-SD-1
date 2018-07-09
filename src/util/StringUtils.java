package util;

import java.util.Arrays;

public class StringUtils {

	/** Adaptado da solução dada por polygenelubricants em questão do 
	 * stack-overflow. Disponível em https://stackoverflow.com/questions/2559759/how-do-i-convert-camelcase-into-human-readable-names-in-java
	 *  
	 * @param camelCased
	 * @return string separada por traços e em letras minusculas. Ex: "SimpleXMLParser" -> simple-xml-parser 
	 * 
	 */
	public static String convertCamelCase(String camelCased) {
	
		String regex = String.format("%s|%s|%s",
		         "(?<=[A-Z])(?=[A-Z][a-z])",
		         "(?<=[^A-Z])(?=[A-Z])",
		         "(?<=[A-Za-z])(?=[^A-Za-z])");
		
		return camelCased.replaceAll(regex, "-").toLowerCase();
	}
	
	/*
	public static void main(String [] args) {
		String[] tests = {
		        "lowercase",        // [lowercase]
		        "Class",            // [Class]
		        "MyClass",          // [My Class]
		        "HTML",             // [HTML]
		        "PDFLoader",        // [PDF Loader]
		        "AString",          // [A String]
		        "SimpleXMLParser",  // [Simple XML Parser]
		        "GL11Version",      // [GL 11 Version]
		        "99Bottles",        // [99 Bottles]
		        "May5",             // [May 5]
		        "BFG9000",          // [BFG 9000]
		    };
		    for (String test : tests) {
		        System.out.println("[" + splitCamelCase(test) + "]");
		    }
	}
	*/
	
	public static String arrayToPrintableList(String [] array) {
		StringBuilder sb = new StringBuilder();
		for(String elemento : array) {
			sb.append(elemento+"\n");
		}
		return sb.toString();
	}
}

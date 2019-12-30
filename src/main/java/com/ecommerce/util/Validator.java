package com.ecommerce.util;

public class Validator {

	public static boolean isAlphaNumerical(String input) {

		if (input != null && input != "") {
			if (input.matches("[a-zA-Z0-9]*")) {
				return true;
			}
		}
		return false;
	}
	public static boolean isNumerical(String input) {
		if (input != null && input != "") {
			if (input.matches("[0-9]*")) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidEmail(String input) {
		if (input != null && input != "") {
			if (input.matches("^[a-zA-Z0-9._]*@[a-zA-Z0-9.-]*$")) {
				return true;
			}
		}
		return false;
	}
	public static boolean isImageFile(String input) {
		if (input != null && input != "") {
			input = input.toLowerCase();
			if (input.endsWith(".png") || input.endsWith(".jpg") || input.endsWith(".jpeg") || input.endsWith(".gif")) {
				return true;
			}
		}
		return false;
	}
	
	public static String removeSpaces(String input) {
		String filterInput = "";
		if (input != null && input != "") {
			filterInput = input.replace(" ", "");
		}
		return filterInput;
	}

	public static boolean isStringEmpty(String input) {
		if (input == null || input.equals("")) {
			return true;
		}
		return false;
	}
}

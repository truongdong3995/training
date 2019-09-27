package com.training.api.utils;

/**
 * Class common
 */
public class Common {
	
	/**
	 * Check the number is halfsize
	 *
	 * @param input string requires processing
	 * @return True input is number halfsize
	 * @return False input is not number halfsize
	 */
	public static boolean checkValidNumber(String input) {
		return replaceData(input).matches("^[0-9]+$");
	}
	
	/**
	 * Remove space and "-" from input
	 *
	 * @param input string requires processing
	 * @return string after processing
	 */
	public static String replaceData(String input) {
		if (input != null) {
			input = input.replace("\\s+", "");
			input = input.replace("-", "");
		}
		
		return input;
	}
	
	/**
	 * Ensures that an object reference passed as a parameter to the calling method is not null.
	 *
	 * @param reference
	 * @param errorMessage error message
	 * @param <T>
	 * @return
	 */
	public static <T> T checkNotNull(T reference, Object errorMessage) {
		if (reference == null) {
			throw new NullPointerException(String.valueOf(errorMessage));
		}
		return reference;
	}
}

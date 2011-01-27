package de.nofail.dzone;

public class StringHelper {
	public static boolean isOneBlank(String... strings) {
		for (String string : strings) {
			if (string == null || string.length() == 0) {
				return true;
			}
		}
		return false;
	}
}

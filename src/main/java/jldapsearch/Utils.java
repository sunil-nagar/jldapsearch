package jldapsearch;

public class Utils {

	public static String pad(String s, int len) {
		if (s == null)
			s = "";
		while (s.length() < len) // low performance
			s = s + " ";
		return s;
	}
	
	public static void emptyConfigurationException(String property, int value) throws ConfigurationException {
		if (value <= 0)
			throw new ConfigurationException(property + " must be greater than zero");
	}

	public static void emptyConfigurationException(String property, String value) throws ConfigurationException {
		if (value == null || value.trim().length() == 0)
			throw new ConfigurationException(property + " is empty");
	}

}

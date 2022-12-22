package jldapsearch;

public class ConfigurationException extends Exception {

	private static final long serialVersionUID = 5910430450985335954L;

	public ConfigurationException(String message) {
		super(message);
	}

	public ConfigurationException(String message, Throwable e) {
		super(message, e);
	}

	public ConfigurationException(Exception e) {
		super(e);
	}

}

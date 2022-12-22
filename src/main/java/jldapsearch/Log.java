package jldapsearch;

import java.util.Arrays;

public class Log {

	private static final String[] levelNames = { "DEBUG", "WARN", "INFO", "ERROR" };

	public static final int DEBUG = 0;
	public static final int WARN = 1;
	public static final int INFO = 2;
	public static final int ERROR = 3;

	private static int level = INFO;

	private String className;

	public Log(Class<?> _class) {
		this.className = _class.getName();
	}

	public void setLevel(int level) {
		if (level >= 0 && level <= 3)
			Log.level = level;
	}

	public void debug(String method, Object... args) {
		if (level <= DEBUG)
			System.out.println(format(method, args));
	}

	public void info(String method, Object... args) {
		if (level <= INFO)
			System.out.println(format(method, args));
	}

	public void warn(String method, Object... args) {
		if (level <= WARN)
			System.out.println(format(method, args));
	}

	public void error(String method, Object... args) {
		if (level <= ERROR)
			System.out.println(format(method, args));
	}

	public String format(String method, Object... args) {
		return levelNames[level] + " " + className + " " + toString(method, args);
	}

	public String toString(String method, Object... args) {
		return "/" + method + " " + Arrays.toString(args);
	}

}

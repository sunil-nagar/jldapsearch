package jldapsearch;

import java.util.Arrays;

public class Params {

	public static boolean verbose;
	public static String searchbase;
	public static String scope; // {base|one|sub|children}
	public static String alias; // -a {never|always|search|find}
	public static String timelimit;
	public static int sizelimit;
	public static String binddn;
	public static String passwd;
	public static String ldapuri;
	public static String filter;
	public static String[] attributes;

	private static void clear() {
		Params.verbose = false;
		Params.sizelimit = 10;
		Params.attributes = new String[] {};
	}

	public static void initialize(String[] args) throws ConfigurationException {
		clear();
		try {
			if (args == null || args.length == 0) {
				throw new ConfigurationException("No command found");
			}
			int i = 0;
			for (i = 0; i < args.length; i++) {
				String arg = args[i];
				if (arg.equals("-v")) { // Manually check flags
					String flag = arg;
					switch (flag) {
					case "-v":
						Params.verbose = true;
						break;
					}
				} else if (arg.startsWith("-")) { // We know we are looking at parameters
					String param = arg;
					String val = args[++i];
					switch (param) {
					case "-b":
						Params.searchbase = val;
						break;
					case "-s":
						Params.scope = val;
						break;
					case "-a":
						Params.alias = val;
						break;
					case "-l":
						Params.timelimit = val;
						break;
					case "-z":
						Params.sizelimit = toInt(val);
						break;
					case "-D":
						Params.binddn = val;
						break;
					case "-w":
						Params.passwd = val;
						break;
					case "-H":
						Params.ldapuri = val;
						break;
					}
				} else { // We are at the filter
					filter = arg;
					if ("" != filter) {
						filter = filter.replace("objectCategorx", "objectCategory");
					}
					if (i < args.length - 1)
						attributes = Arrays.copyOfRange(args, i + 1, args.length);
					break;
				}
			}
			if (Params.verbose)
				Log.verbose("initialize", _toString());
			if (Params.filter == null) {
				throw new ConfigurationException("Filter not found!");
			}
		} catch (Exception e) {
			throw new ConfigurationException("Error parsing arguments " + Arrays.toString(args), e);
		}
	}

	public static int toInt(String s) throws ConfigurationException {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			throw new ConfigurationException("Unable to convert to integer " + s, e);
		}
	}

	private static String _toString(String param, String val) {
		if (param == null || val == null)
			return "";
		return "  " + param + "=" + val + "\n";
	}

	private static String _toString(String param, String[] valArray) {
		String val = Arrays.toString(valArray);
		return _toString(param, val);
	}

	public static String _toString() {
		return "Params { \n" + _toString("verbose", "" + verbose) + _toString("searchbase", Params.searchbase)
				+ _toString("scope", Params.scope) + _toString("alias", Params.alias)
				+ _toString("timelimit", Params.timelimit) + _toString("sizelimit", "" + Params.sizelimit)
				+ _toString("binddn", Params.binddn) + _toString("passwd", Params.passwd)
				+ _toString("ldapuri", Params.ldapuri) + _toString("filter", Params.filter)
				+ _toString("attributes", Params.attributes) + "}";
	}

}

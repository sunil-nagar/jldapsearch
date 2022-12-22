package jldapsearch;

import java.util.Arrays;

public class Log {

	public static void verbose(Object... args) {
		if (Params.verbose)
			System.out.println(Arrays.toString(args));
	}
}

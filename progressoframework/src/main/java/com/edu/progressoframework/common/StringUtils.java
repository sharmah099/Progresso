package com.edu.progressoframework.common;

/**
 * A utility class that provides utility functions
 * around a String instance
 *
 */
public class StringUtils
{
	/**
	 * Checks if the specified string is null or empty
	 */
	public static boolean isNullOrEmpty(String str)
	{
		boolean retval = false;
		if (str == null || Constants.Empty.equals(str)) {
			retval = true;
		}
		
		return retval;
	}
}

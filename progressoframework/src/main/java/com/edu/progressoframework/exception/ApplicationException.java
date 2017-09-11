package com.edu.progressoframework.exception;

/**
 * A generic application exception. This can be used
 * to throw application specific exceptions.
 */
public class ApplicationException extends Exception
{
	/**
	 * Serializable class version number
	 */
	private static final long serialVersionUID = 1L;
	
	public ApplicationException(String message)
	{
		super(message);
	}
}

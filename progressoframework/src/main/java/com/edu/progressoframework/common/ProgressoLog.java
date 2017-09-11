package com.edu.progressoframework.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * This custom log class provides the log output
 */
public class ProgressoLog
{
	/**
	 * context
	 */
	private Context context;

	/**
	 * ProgressoLog singleton instance
	 */
	private static ProgressoLog progressoLog;

	/**
	 * Constructor
	 */
	public static ProgressoLog getInstance(Context context)
	{
		if (progressoLog == null) {
			//ensure that we have exclusive access when
			//creating the singleton instance
			synchronized (ProgressoLog.class) {
				if (progressoLog == null) {
					progressoLog = new ProgressoLog(context);
				}
			}
		}

		return progressoLog;
	}

	/**
	 * Constructor
	 */
	private ProgressoLog(Context context)
	{
		this.context = context;
	}

	/**
	 * Function to send an INFO log message.
	 */
	public void i(String tag, String string)
	{	
		if (isDebuggable()) {
			android.util.Log.i(tag, string);
		}
	}

	/**
	 * Function to send an ERROR log message.
	 */
	public void e(String tag, String string)
	{
		if (isDebuggable()) {
			android.util.Log.e(tag, string);
		}
	}

	/**
	 * Function to send an DEBUG log message.
	 */
	public void d(String tag, String string)
	{
		if (isDebuggable()) {
			android.util.Log.d(tag, string);
		}
	}

	/**
	 * Function to send an VERBOSE log message.
	 */
	public void v(String tag, String string)
	{
		if (isDebuggable()) {
			android.util.Log.v(tag, string);
		}
	}

	/**
	 * Function to send an WARN log message.
	 */
	public void w(String tag, String string)
	{
		if (isDebuggable()) {
			android.util.Log.w(tag, string);
		}
	}	

	/**
	 * Function returns whether device application is in 
	 * debug mode or not
	 */
	private boolean isDebuggable()
	{
		return (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
	}
}

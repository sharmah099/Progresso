package com.edu.progressoframework.app;

import android.app.Application;

/**
 * The base application class
 */
public abstract class BaseApplication extends Application
{
	/**
	 * Returns the application idle timeout
	 * @return
	 */
	public abstract boolean isApplicationLockable();
}

package com.edu.progressoframework.background;

/**
 * Defines the functions to be implemented by the classes
 * that need to execute tasks in background
 */
public interface BackgroundTaskListener
{
	/**
	 * Function called to be executed in the background
	 * @result	The results of execution
	 */
	public BackgroundTaskResult executeInBackground(Object param);
	
	/**
	 * Function called when the background task is completed
	 * @param result	The results of execution
	 */
	public void onBackgroundTaskCompleted(BackgroundTaskResult result);
	
	/**
	 * Function called when the background task is cancelled
	 */
	public void onBackgroundTaskCancelled(BackgroundTaskResult result);
}

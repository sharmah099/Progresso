package com.edu.progressoframework.background;


import com.edu.progressoframework.common.Constants;

/**
 * Encapsulates the results of background task execution
 */
public class BackgroundTaskResult
{
	public static int BCK_TASK_RESULT_OK	= 101;
	public static int BCK_TASK_RESULT_NOK	= 102;
	public static int BCK_TASK_RESULT_OTHER	= 103;

	/**
	 * Purpose for starting the background task
	 */
	private int task;

	/**
	 * Getter
	 */
	public int getTask()
	{
		return task;
	}

	/**
	 * Setter
	 */
	public void setTask(int task)
	{
		this.task = task;
	}
	
	/**
	 * The result of background task execution
	 */
	private int result;

	/**
	 * Getter
	 */
	public int getResult()
	{
		return result;
	}

	/**
	 * Setter
	 */	
	public void setResult(int result)
	{
		this.result = result;
	}

	/**
	 * Information, warning or error message if any to
	 * be displayed to the user
	 */
	private String message;

	/**
	 * Getter 
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * setter
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	/**
	 * Any resulting object that needs to be passed
	 * to the main thread
	 */
	private Object resultObj;
	
	/**
	 * Getter	
	 */
	public Object getResultObj()
	{
		return resultObj;
	}

	/**
	 * Setter
	 */
	public void setResultObj(Object resultObj)
	{
		this.resultObj = resultObj;
	}

	/**
	 * Constructor
	 * @param rslt
	 */
	public BackgroundTaskResult()
	{
		task = -1;
		result = BackgroundTaskResult.BCK_TASK_RESULT_OK;
		message = Constants.Empty;
		resultObj = null;
	}
}

package com.edu.progressoframework.background;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;

import com.edu.progressoframework.app.activity.BaseActivity;


/**
 * A fragment that does NOT have an UI and which is used
 * to manage the background task. The background task has
 * been encapsulated in a fragment (with setRetainInstance(true)
 * This ensures that the fragment is not destroyed with its
 * activity and hence the state is maintained. This is in line
 * Android's recommendation of not using the deprecated methods
 * of getLastNonConfigurationInstance & onRetainNonConfigurationInstance
 */
public class BackgroundTaskManager extends Fragment
{
	/**
	 * The background task that is used to 
	 * perform activities in a background
	 * thread. It is required to maintain
	 * the state of this task even when the
	 * activity is destroyed due to changes
	 * in configuration (like orientation)
	 */
	private BackgroundTask backgroundTask;
	
	/**
	 * The background task that is used to 
	 * perform activities in a background
	 * thread. This background task varies 
	 * from other one in the context that
	 * it runs parallel to previous task.
	 */
	private BackgroundTask backgroundTaskParallel;

	/**
	 * Function called when task manager fragment is
	 * created.
	 * @param savedInstanceState	The saved bundle
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		//the instance of this fragment is retained across
		//activity recreation (like orientation change)
		setRetainInstance(true);
	}

	/**
	 * Function called when the activity associated with
	 * this fragment is created.
	 * @param savedInstanceState	The saved bundle
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		if (backgroundTask != null) {
			//attach the activity to the background task
			backgroundTask.setActivity((BaseActivity)getActivity());
		}
		
		if (backgroundTaskParallel != null) {
			backgroundTaskParallel.setActivity((BaseActivity)getActivity());
		}
	}

	/**
	 * Function called when the fragment is no longer
	 * attached to any activity.
	 */
	@Override
	public void onDetach()
	{
		super.onDetach();
		if (backgroundTask != null) {
			//detach the activity from the background thread
			backgroundTask.setActivity(null);
		}
		
		if (backgroundTaskParallel != null) {
			//detach the activity from the background thread
			backgroundTaskParallel.setActivity(null);
		}
	}
	
	/**
	 * Function called to update the progress dialog message
	 */
	public void updateMessage(String msg)
	{
		if (backgroundTask != null) {
			backgroundTask.updateMessage(msg);
		}
	}
	
	/**
	 * Function called to execute the background task whilst
	 * shown the progress dialog
	 * @param param	The parameter to be passed to the function that is executed in the background
	 */
	public void executeTask(Object param)
	{
		executeTask(param, true);
	}
	
	/**
	 * Function called to execute the background task
	 * @param activity Activity attached to current fragment required to start background task
	 * @param param	The parameter to be passed to the function that is executed in the background
	 * @param showProgressDialog	To show or not to show the progress dialog
	 */
	public void executeTask(BaseActivity activity, Object param, boolean showProgressDialog)
	{
		if ((backgroundTask == null) ||
				(backgroundTask != null && backgroundTask.getStatus().equals(AsyncTask.Status.FINISHED))) {
			//there is no background task or the previous background task has completed
			backgroundTask = new BackgroundTask(activity, showProgressDialog);
			backgroundTask.execute(param);			
		}
	}
	
	/**
	 * Function called to execute the background task
	 * @param param	The parameter to be passed to the function that is executed in the background
	 * @param showProgressDialog	To show or not to show the progress dialog
	 */
	public void executeTask(Object param, boolean showProgressDialog)
	{
		executeTask((BaseActivity)getActivity(), param, showProgressDialog);
	}
	
	/**
	 * Function called to execute the background task
	 * @param param		The parameter to be passed to the function that is executed in the background
	 * @param showProgressDialog	To show or not to show the progress dialog
	 * @param msg	The progress dialog message
	 */
	public void executeTask(Object param, boolean showProgressDialog, String msg)
	{
		if ((backgroundTask == null) ||
				(backgroundTask != null && backgroundTask.getStatus().equals(AsyncTask.Status.FINISHED))) {
			//there is no background task or the previous background task has completed
			backgroundTask = new BackgroundTask((BaseActivity)getActivity(), msg);
			backgroundTask.execute(param);			
		}		
	}
	
	/**
	 * Function called to execute the background task from the
	 * specified fragment
	 * @param param	The parameter to be passed to the function that is executed in the background
	 */
	public void executeTask(Object param, String tag, String msg)
	{
		if ((backgroundTask == null) ||
				(backgroundTask != null && backgroundTask.getStatus().equals(AsyncTask.Status.FINISHED))) {
			//there is no background task or the previous background task has completed
			backgroundTask = new BackgroundTask((BaseActivity)getActivity(), tag, msg);
			backgroundTask.execute(param);			
		}		
	}
	
	/**
	 * Function called to execute the background task from the specified fragment. 
	 * This function does not check for any currently running background task.
	 * Also this parallel task is non-cancelable, will not update the message while
	 * task is running as per current implementation
	 */
	public void executeParallelTask(BaseActivity activity, Object param)
	{
		backgroundTaskParallel = new BackgroundTask(activity, false);
		backgroundTaskParallel.execute(param);			
	}
	
	/**
	 * Function called to execute the background task. This function does not
	 * check for any currently running background task.
	 * Also this parallel task is non cancelable, will not update the message while
	 * task is running as per current implementation
	 */
	public void executeParallelTask(Object param, String tag)
	{
		backgroundTaskParallel = new BackgroundTask((BaseActivity)getActivity(), tag, null);
		backgroundTaskParallel.execute(param);			
	}
	
	/**
	 * Function called to cancel the background task
	 */
	public void cancelTask()
	{
		if (backgroundTask != null && !backgroundTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
			backgroundTask.cancel(true);
		}
	}
	
	/**
	 * Returns true if the task has been cancelled
	 */
	public boolean isTaskCancelled()
	{
		boolean retval = true;
		
		if (backgroundTask != null) {
			retval = backgroundTask.isCancelled();
		}
	
		return retval;
	}
	
	/**
	 * Returns the status of the background task
	 */
	public AsyncTask.Status getTaskStatus()
	{
		AsyncTask.Status status = AsyncTask.Status.FINISHED;
		if (backgroundTask != null) {
			status = backgroundTask.getStatus();
		}
		
		return status;
	}
}

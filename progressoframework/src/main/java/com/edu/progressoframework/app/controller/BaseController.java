package com.edu.progressoframework.app.controller;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.MenuItem;
import android.view.View;

import com.edu.progressoframework.app.activity.BaseActivity;
import com.edu.progressoframework.common.ProgressoLog;
import com.edu.progressoframework.model.BaseModel;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.StringRes;

/**
 * The base class for all controllers of the application
 */
@EBean
public abstract class BaseController
{
    /**
     * The activity that is associated with this controller
     */
    @RootContext
    protected BaseActivity activity;

    /**
     * The model to be used by the controller. Note that
     * an instance of the model will be always created in
     * the activity/view and will be set into the controller.
     * This ensures that both, the view and the controller
     * uses the same instance.
     */
    protected BaseModel model;

    /**
     * The invalid activity resource string
     */
    @StringRes(resName="invalid_activity")
    protected String invalidActivity;

    /**
     * Function that is called to initialise
     * the model
     * @param mdl
     */
    public void initModel(BaseModel mdl)
    {
        model = mdl;

        //initialise the model only if it has not been restored
        //from the saved instance in onCreate() of activity.
        if (model != null && !model.isSavedInstance()) {
            model.initModel();
            performModelSpecificInit();
        }
    }

    /**
     * Performs model specific initialisations
     */
    public abstract void performModelSpecificInit();

    /**
     * This function starts a new activity. This function takes
     * into account that the AndroidAnnotations library generates
     * an _ suffixed activity.
     * 
     * @param activityClassToStart  The activity class to start
     */
    protected void startActivity(Class<?> activityClassToStart)
    {
        //annotate the activity with underscore
        String className = activityClassToStart.getName() +"_";

        try {
            //get the Class object associated with the class string
            Class<?> newClassToStart =  Class.forName(className);

            //start the activity
            innerStartActivity(new Intent(activity, newClassToStart));
        }
        catch (Exception ex) {
        	ProgressoLog.getInstance(activity.getApplicationContext()).e("iConverge:BaseActivity", invalidActivity + className);
        }
    }

    /**
     * This function starts a new activity. This function takes
     * into account that the AndroidAnnotations library generates
     * an _ suffixed activity.
     * 
     * @param activityClassToStart  The activity class to start
     * @param intent                The intent to pass to the activity
     */
    protected void startActivity(Class<?> activityClassToStart, Bundle extras)
    {
        //annotate the activity with underscore
        String className = activityClassToStart.getName() +"_";

        try {
            //get the Class object associated with the class string
            Class<?> newClassToStart =  Class.forName(className);

            //add the extras to to the intent
            Intent intent = new Intent(activity, newClassToStart);
            intent.putExtras(extras);

            //start the activity
            innerStartActivity(intent);
        }
        catch (Exception ex) {
        	ProgressoLog.getInstance(activity.getApplicationContext()).e("iConverge:BaseActivity", invalidActivity + className);
        }
    }

    /**
     * This function starts a new activity. This function takes
     * into account that the AndroidAnnotations library generates
     * an _ suffixed activity.
     * 
     * @param activityClassToStart  The activity class to start
     * @param intent                The intent to pass to the activity
     */
    protected void startActivity(Class<?> activityClassToStart, int flags, Bundle extras)
    {
        //annotate the activity with underscore
        String className = activityClassToStart.getName() +"_";

        try {
            //get the Class object associated with the class string
            Class<?> newClassToStart =  Class.forName(className);


            //add the extras and flags to to the intent
            Intent intent = new Intent(activity, newClassToStart);
            intent.putExtras(extras);
            intent.addFlags(flags);

            //start the activity
            innerStartActivity(intent);
        }
        catch (Exception ex) {
        	ProgressoLog.getInstance(activity.getApplicationContext()).e("iConverge:BaseActivity", invalidActivity + className);
        }
    }

    /**
     * This function starts a new activity. This function takes
     * into account that the AndroidAnnotations library generates
     * an _ suffixed activity.
     * 
     * @param activityClassToStart  The activity class to start
     * @param requestCode If > 0 this code will be returned in onActivityResult
     * when the activity finishes
     */
    protected void startActivityForResult(Class<?> activityClassToStart, int requestCode)
    {
        //annotate the activity with underscore
        String className = activityClassToStart.getName() +"_";

        try {
            //get the Class object associated with the class string
            Class<?> newClassToStart =  Class.forName(className);

            //start the activity
            innerStartActivityForResult(new Intent(activity, newClassToStart), requestCode);
        }
        catch (Exception ex) {
        	ProgressoLog.getInstance(activity.getApplicationContext()).e("iConverge:BaseActivity", invalidActivity + className);
        }
    }

    /**
     * This function starts a new activity. This function takes
     * into account that the AndroidAnnotations library generates
     * an _ suffixed activity.
     * 
     * @param activityClassToStart  The activity class to start
     * @param intent                The intent to pass to the activity
     */
    protected void startActivityForResult(Class<?> activityClassToStart, Bundle extras, int requestCode)
    {
        //annotate the activity with underscore
        String className = activityClassToStart.getName() +"_";

        try {
            //get the Class object associated with the class string
            Class<?> newClassToStart =  Class.forName(className);

            //add the extras to to the intent
            Intent intent = new Intent(activity, newClassToStart);
            intent.putExtras(extras);

            //start the activity
            innerStartActivityForResult(intent, requestCode);
        }
        catch (Exception ex) {
        	ProgressoLog.getInstance(activity.getApplicationContext()).e("iConverge:BaseActivity", invalidActivity + className);
        }
    }

    /**
     *  /**
     * This function starts a new activity. This function takes
     * into account that the AndroidAnnotations library generates
     * an _ suffixed activity.
     *
     * @param activityClassToStart
     * @param extras
     * @param requestCode
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void startActivityForResult(Class<?> activityClassToStart, int requestCode, Bundle extras)
    {
        //annotate the activity with underscore
        String className = activityClassToStart.getName() +"_";

        try {
            //get the Class object associated with the class string
            Class<?> newClassToStart =  Class.forName(className);

            //add the extras to to the intent
            Intent intent = new Intent(activity, newClassToStart);
            intent.putExtras(extras);

            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, null);
            //start the activity
            activity.startActivityForResult(intent, requestCode, transitionActivityOptions.toBundle());
        }
        catch (Exception ex) {
            ProgressoLog.getInstance(activity.getApplicationContext()).e("iConverge:BaseActivity", invalidActivity + className);
        }
    }

    /**
     *  /**
     * This function starts a new activity. This function takes
     * into account that the AndroidAnnotations library generates
     * an _ suffixed activity.
     *
     * @param activityClassToStart
     * @param extras
     * @param requestCode
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void startActivityForResult(Class<?> activityClassToStart, int requestCode, Bundle extras, View view , String tag)
    {
        //annotate the activity with underscore
        String className = activityClassToStart.getName() +"_";

        try {
            //get the Class object associated with the class string
            Class<?> newClassToStart =  Class.forName(className);

            //add the extras to to the intent
            Intent intent = new Intent(activity, newClassToStart);
            intent.putExtras(extras);

            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, tag);
            innerStartActivityForResult(intent, requestCode, transitionActivityOptions.toBundle());
        }
        catch (Exception ex) {
            ProgressoLog.getInstance(activity.getApplicationContext()).e("iConverge:BaseActivity", invalidActivity + className);
        }
    }

    /**
     * This method (or {@link innerStartActivityForResult})
     * should be called by any other method within this class
     * that wants to start an activity.
     * DO NOT call activity.start... manually as this will
     * lead to confusion when a fragment wants to start an activity.
     * 
     * This method forwards on startActivity calls by
     * settign the request code to -1 (as done by
     * Android anyway). See {@link innerStartActivityForResult}
     * for more details.
     */
    private void innerStartActivity(Intent intent)
    {
        innerStartActivityForResult(intent, -1);
    }

    /**
     * This method (or {@link innerStartActivity})
     * should be called by any other method within this class
     * that wants to start an activity.
     * DO NOT call activity.start... manually as this will
     * lead to confusion when a fragment wants to start an activity.
     * 
     * This inner method allows us to delegate responsibility for
     * starting an activity. By extracting this functionality,
     * other components (e.g. fragments) can be the ones to start
     * new activities, and can therefore be in charge of maintaining
     * their lifecycles and (more importantly) retreiving their result.
     */
    protected void innerStartActivityForResult(Intent intent, int requestCode)
    {
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * This method (or {@link innerStartActivity})
     * should be called by any other method within this class
     * that wants to start an activity.
     * DO NOT call activity.start... manually as this will
     * lead to confusion when a fragment wants to start an activity.
     *
     * This inner method allows us to delegate responsibility for
     * starting an activity. By extracting this functionality,
     * other components (e.g. fragments) can be the ones to start
     * new activities, and can therefore be in charge of maintaining
     * their lifecycles and (more importantly) retreiving their result.
     */
    protected void innerStartActivityForResult(Intent intent, int requestCode, Bundle bundle)
    {
        activity.startActivityForResult(intent, requestCode, bundle);
    }

    /**
     * Function called to finish the activity assoiciated with
     * this controller.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void finishActivityLollipopAbove()
    {
        activity.finishAfterTransition();
    }

    /**
     * Function called to finish the activity assoiciated with
     * this controller.
     */
    protected void finishActivity()
    {
        activity.finish();
    }

    /**
     * Function called when the back button is pressed.
     * @return {@code true} if the event has been handled and
     * {@code false} to use default implementation.
     */
    public boolean onBackPressed()
    {
        return false;
    }

    /**
     * Function called when an action bar item is pressed.
     * @return {@code true} if the event has been handled and
     * {@code false} to use default implementation.
     */
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return false;
    }

    /**
     * Function called when an activity finishes.
     * @return {@code true} if the event has been handled and
     * {@code false} to use default implementation.
     */
    public boolean onActivityResult(int requestCode, int resultCode,
            Intent data)
    {
        return false;
    }

    /**
     * Returns the string value associated with the resource id
     * @param resId		The resource id
     * @return			The string vlaue
     */
    protected String getString(int resId)
    {
        return activity.getString(resId);
    }
    
	/**
	 * Function that starts the background task with the progress dialog showing 
	 * @param param		Parameter to be passed to the background thread
	 */
	protected void startBackgroundTask(Object param)
	{
		startBackgroundTask(param, true);
	}

	/**
	 * Function that starts the background task. 
	 * @param param		Parameter to be passed to the background thread
	 * @param param		Do we need to show the progress dialog?
	 */
	protected void startBackgroundTask(Object param, boolean spd)
	{
		//start the background task
		if (activity.getTaskManager() != null) {
			activity.getTaskManager().executeTask(activity, param, spd);
		}
	}
	
	/**
	 * Function that starts a second background task. DO NOT USE THIS
	 * FUNCTION WITHOUT INTIMATING SUSHIL/ABHISHEK.
	 */
	protected void startParallelBackgroundTask(Object param)
	{
		//start the background task
		if (activity.getTaskManager() != null) {
			activity.getTaskManager().executeParallelTask(activity, param);
		}
	}
	
	/**
	 * Function that starts a second background task. DO NOT USE THIS
	 * FUNCTION WITHOUT INTIMATING SUSHIL/ABHISHEK.
	 */
	protected void startParallelBackgroundTask(Object param, String tag)
	{
		//start the background task
		if (activity.getTaskManager() != null) {
			activity.getTaskManager().executeParallelTask(param, tag);
		}
	}

	/**
	 * Function that starts the background task. 
	 * @param param		Parameter to be passed to the background thread
	 * @param spd		Do we need to show the progress dialog?
	 * @param msg		The progress dialog message
	 */
	protected void startBackgroundTask(Object param, String msg)
	{
		//start the background task
		if (activity.getTaskManager() != null) {
			activity.getTaskManager().executeTask(param, true, msg);
		}
	}
	
	/**
	 * Function that starts the background task from within a fragment
	 * of the activity.
	 * @param param	Parameters to be passed to the task
	 * @param tag	The fragment's tag
	 * @param msg	The message to be shown in the progress dialog. If null,
	 * 				progress dialog will not be shown
	 */
	protected void startBackgroundTask(Object param, String tag, String msg)
	{
		if (activity.getTaskManager() != null) {
			activity.getTaskManager().executeTask(param, tag, msg);
		}
	}
	
	/**
	 * Function called to cancel/stop the background task
	 */
	protected void cancelBackgroundTask()
	{
		if (activity.getTaskManager() != null) {
			activity.getTaskManager().cancelTask();
		}
	}
	
	/**
	 * Return true if the background task has been cancelled
	 * @return
	 */
	protected boolean isBackgroundTaskCancelled()
	{
		boolean retval = true;
		
		if (activity.getTaskManager() != null) {
			retval = activity.getTaskManager().isTaskCancelled();
		}
		
		return retval;
	}
	
	/**
	 * Returns the status of the background task
	 * @return
	 */
	protected AsyncTask.Status getBackgroundTaskStatus()
	{
		AsyncTask.Status status = AsyncTask.Status.FINISHED;
		
		if (activity.getTaskManager() != null) {
			status = activity.getTaskManager().getTaskStatus();
		}
		
		return status;
	}
	
	/**
	 * Updates the message in the progress dialog
	 * @param msg
	 */
	protected void updateTaskMessage(String msg)
	{
		//start the background task
		if (activity.getTaskManager() != null) {
			activity.getTaskManager().updateMessage(msg);
		}
	}
}
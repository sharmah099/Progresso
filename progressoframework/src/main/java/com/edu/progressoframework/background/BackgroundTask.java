package com.edu.progressoframework.background;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;

import com.edu.progressoframework.R;
import com.edu.progressoframework.app.activity.BaseActivity;
import com.edu.progressoframework.app.controller.BaseController;
import com.edu.progressoframework.app.fragment.BaseFragment;
import com.edu.progressoframework.common.StringUtils;


/**
 * Class that provides support to execute tasks in the background.
 * It is recommended that this class is NOT used in isolation for
 * the purposes of executing the background tasks. Special purpose
 * classes BackgroundTaskActivity and BackgroundTaskController
 * have been created for this purpose.
 */
public class BackgroundTask extends AsyncTask<Object, Void, BackgroundTaskResult>
{
    /**
     * Flag that indicates if the progress dialog
     * needs to be shown.
     */
    private boolean showProgressDialog	= true;

    /**
     * The progress dialog that is shown when the
     * background task has started.
     */
    private ProgressDialog dialog = null;

    /**
     * Indicates if the task has been completed
     */
    private boolean taskCompleted = false;

    /**
     * Flag indicating that task completion notification
     * needs to be sent upon attachment of the activity
     */
    private boolean updateOnActivityAttach = false;

    /**
     * The activity in which the background task
     * has been started
     */
    private BaseActivity	activity;

    /**
     * The fragment's tag associated with the activity
     */
    private String tag;
    
    /**
     * Progress dialog message
     */
    private String message;

    /**
     * Setter method for the activity. This method shall
     * be called to re-bind the background task to an
     * activity, in case the previous activity has been
     * destroyed.
     * @param act	The activity to which the task needs to be bound
     */
    public void setActivity(BaseActivity act)
    {
        this.activity = act;

        if (act == null) {
            //dismiss the dialog if there is no
            //activity associated with this task
            dismissProgressDialog();
        }
        else
        {
            //activity is being attached to the background thread.
            //Check if the task is already completed
            if (taskCompleted && getStatus().equals(AsyncTask.Status.FINISHED)
                    && updateOnActivityAttach) {
            	if (!isCancelled()) {
	                //yes, notify about completion of task
	                notifyTaskCompletion(null);
            	}
            	else {
            		notifyTaskCancelled(null);
            	}
            }
            else
            {
                //no, display the progress dialog indicating the
                //background task is still running.
                showProgressDialog();
            }
        }
    }

    /**
     * Constructor
     * @param spd	Indicates whether to display the progress dialog or not
     */
    public BackgroundTask(boolean spd)
    {
        activity = null;
        tag = null;
        showProgressDialog = spd;
        message = "Operation in progress";
    }

    /**
     * Overloaded constructor
     * @param act	The activity associated with this task
     * @param spd	Indicates whether to display the progress dialog or not
     */
    public BackgroundTask(BaseActivity act, boolean spd)
    {
        activity = act;
        tag = null;
        showProgressDialog = spd;
        message = "Operation in progress";
    }

    /**
     * Overloaded constructor
     * @param act	The activity associated with this task
     * @param tag	The fragment's tag - must be associated with the activity
     * @param spd	Indicates whether to display the progress dialog or not
     */
    public BackgroundTask(BaseActivity act, String tag, String msg)
    {
        activity = act;
        this.tag = tag;
        if (StringUtils.isNullOrEmpty(msg)) {
        	showProgressDialog = false;
        }
        else {
        	showProgressDialog = true;
        }
        message = msg;
    }

    /**
     * Overloaded constructor
     * @param act	The activity associated with this task
     * @param spd	Indicates whether to display the progess dialog or not
     * @param msg	The progress dialog's message
     */
    public BackgroundTask(BaseActivity act, String msg)
    {
        activity = act;
        this.tag = null;
        showProgressDialog = true;
        message = msg;
    }

    /**
     * Function called on the UI thread before
     * the background functionality is executed.
     */
    @Override
    protected void onPreExecute()
    {
        taskCompleted = false;

        //display the progress dialog
        showProgressDialog();
    }

    /**
     * Function called in background. The number of parameters
     * is always one.
     */
    @Override
    protected BackgroundTaskResult doInBackground(Object... params)
    {
        BackgroundTaskResult retval = null;
        int paramCount = params.length;
        Object param = null;

        if (paramCount != 0) {
            param = params[0];
        }

        // controller that implements the function to be executed in background
        BaseController controller = getController();
        if (controller != null && controller instanceof BackgroundTaskListener) {
            retval = ((BackgroundTaskListener)controller).executeInBackground(param);
        }

        return retval;
    }


    /**
     * Function called in the UI thread after the
     * background functionality is completed.
     */
    @Override
    protected void onPostExecute(BackgroundTaskResult result)
    {
        taskCompleted = true;
        dismissProgressDialog();
        notifyTaskCompletion(result);
    }

    /**
     * Function called when the task is cancelled
     */
	@Override
	protected void onCancelled(BackgroundTaskResult result)
	{
		taskCompleted = true;
		dismissProgressDialog();
		notifyTaskCancelled(result);
	}

	/**
     * Function called to update the UI with the
     * results of the background task after its
     * completion.
     */
    private void notifyTaskCompletion(BackgroundTaskResult result)
    {
        //Use the activity reference to update the UI only
        //if it is available.
        if (null != activity) {
            BaseController controller = getController();
            if (controller != null && controller instanceof BackgroundTaskListener) {
                ((BackgroundTaskListener)controller).onBackgroundTaskCompleted(result);
            }
            updateOnActivityAttach = false;
        }
        else {
            updateOnActivityAttach = true;
        }
    }

	/**
     * Function called to update the UI with the
     * results of the background task after its
     * cancellation.
     */
    private void notifyTaskCancelled(BackgroundTaskResult result)
    {
        //Use the activity reference to update the UI only
        //if it is available.
        if (null != activity) {
            BaseController controller = getController();
            if (controller != null && controller instanceof BackgroundTaskListener) {
                ((BackgroundTaskListener)controller).onBackgroundTaskCancelled(result);
            }
            updateOnActivityAttach = false;
        }
        else {
            updateOnActivityAttach = true;
        }
    }

    /**
     * Function called to show the progress dialog
     */
    private void showProgressDialog()
    {
        if (showProgressDialog && !getStatus().equals(AsyncTask.Status.FINISHED)) {
            //show the dialog if valid activity. If the OS is lollipop or higher then set the theme.
            //dialog = ProgressDialog.show(activity, "In progress..", message, true);
            if (Build.VERSION.SDK_INT >= 21) {
                dialog = new ProgressDialog(activity, R.style.progressoAlertDialogStyle);
                dialog.setTitle("In progress..");
                dialog.setMessage(message);
                dialog.setIndeterminate(true);
                dialog.show();
            }
            else  {
                dialog = ProgressDialog.show(activity, "In progress..", message, true);
            }

            dialog.setCancelable(false);
        } 
    }

    /**
     * Function called to dismiss the dialog
     */
    private void dismissProgressDialog()
    {
    	if (null != dialog && dialog.isShowing()) {
    		dialog.dismiss();
    	}
    }

    /**
     * Function that returns the controller which has the background
     * task functions implemented.
     */
    private BaseController getController()
    {
        BaseController controller = null;

        //check who has stared the background task
        if (tag == null) {
            //this is an instance of a normal activity starting the background
            //task, use its controller's function to execute the actual task
            //in background.
        	if (activity != null) {
        		controller = activity.getController();
        	}
        }
        else {
            //this is an instance of a activity's fragment starting the backgroundH
            //task, use the fragment's controller function to execute the
            //actual task in background
            if (activity != null) {
                BaseFragment fragment = (BaseFragment) activity.getFragmentManager().findFragmentByTag(tag);
                if (fragment != null) {
                    controller = fragment.getController();
                }
            }
        }

        return controller;
    }
    
	/**
	 * This method is used to update the message
	 * of the progress indicator
	 */
	public void updateMessage(final String msg)
	{
		if (showProgressDialog) {
			message = msg;
			if (null != dialog && dialog.isShowing())
			{
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						dialog.setMessage(msg);
					}
				}); 
			}
		}
	}
}
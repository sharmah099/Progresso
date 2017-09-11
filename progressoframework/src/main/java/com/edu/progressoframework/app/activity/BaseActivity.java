package com.edu.progressoframework.app.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.edu.progressoframework.app.controller.BaseController;
import com.edu.progressoframework.background.BackgroundTaskManager;
import com.edu.progressoframework.model.BaseModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.Observer;

/**
 * This base class for all activities of the application.
 * 
 */
@EActivity
public abstract class BaseActivity extends AppCompatActivity implements Observer
{   
	/**
	 * The background task manager. Responsible to
	 * execute the tasks in background. This will
	 * be initialised only if the activity requires
	 * something to be done in the background.
	 */
	public BackgroundTaskManager taskManager;
	
	/**
	 * Getter method
	 */
	public BackgroundTaskManager getTaskManager()
	{
		return taskManager;
	}


	/**
     * Function called to initialise the specific views.
     */
    public abstract void initViewSpecific();
    
    /**
     * Function called to update the activity widgets
     * with the data contained in the model
     */
    public abstract void update();
    
    /**
     * Function called to update the model with the
     * data contained in the activity widgets
     */
    public abstract void updateModel();
    
    /**
     * Returns the controller associated with this activity
     */
    public abstract BaseController getController();
    
    /**
     * Returns the model associated with this activity
     * @return
     */
    public abstract BaseModel getModel();
    
    
    /**
     * Function called when the activity is starting
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //create the model before creating the view
        createModel();
        super.onCreate(savedInstanceState);

        //check if the activity has a saved state, if yes restore it
        if (savedInstanceState != null) {
            String className = getClass().toString();
            BaseModel model = (BaseModel)savedInstanceState.getParcelable(className);
            if (model != null) {
                //copy the model got from the bundle to the activity's model
                getModel().initModel(model);
                getModel().setIsSavedInstance(model.isSavedInstance());
            }
        }
    }
    
    /**
     * Function called to initialise the task manager.
     * This function will need to be called if the activity
     * requires to execute a background task.
     */
    protected void initTaskManger()
    {
    	/*
    	if (!(getController() instanceof BackgroundTaskListener)) {
    		throw new RuntimeException("Controller: " + getController().getClass().getName() 
    				+ " should implement BackgroundTaskListener");
    	}
    	*/
    		
		//Check if there is a retained task manager from the previous instance
    	FragmentManager fragmentManager = getFragmentManager();
		taskManager = (BackgroundTaskManager)fragmentManager.findFragmentByTag("taskManager");
        if (taskManager == null) 
        {
            // We do not have a retained task manager 
        	// (or first time running), create it
        	taskManager = new BackgroundTaskManager();
        	fragmentManager.beginTransaction().add(taskManager, "taskManager").commit();
        }
    }

    /**
     * Function to create a model
     */
    protected abstract void createModel();
    
    /**
     * Function called to initialise the view with
     * the initial data when the activity is created
     */
    @AfterViews
    public void initView()
    {   
        //initialise the model, if the activity has one
        BaseModel model = getModel();       
        if (model != null)
        {
        	getController().initModel(model);
            model.addObserver(this);
        }

        //perform view specific initialisations
        initViewSpecific();  
        
        //update the view with data contained in the model
        update();   
    }

    /**
     * Function called when the activity is about to be killed.
     * This function is called to save the state of the activity
     * so that it can be restored later in case it is activated
     * during the current life time of the application.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
    	super.onSaveInstanceState(outState);

    	//get the contents of the controls into the mocel
    	updateModel();

    	//save the state of the activity - this data is contained
    	//within the model
    	String className = getClass().toString();
    	BaseModel model = getModel();
    	if (model != null) {
    		//set a flag in the model to indicate that we have a saved
    		//instance of the activity. This flag will be used for 
    		//detecting orientation changes.
    		model.setIsSavedInstance(true);
    		outState.putParcelable(className, model);
    	}
    }

    /**
     * Function called when the activity is resumed
     */
    @Override
    protected void onResume() 
    {
    	if (getModel() != null) {
    		//By this time we have processed anything related to 
    		//orientation changes. Hence reset the flag
            getModel().setIsSavedInstance(false);
    	}

    	super.onResume();
    }
    
    /**
     * Function called when the back button is pressed.
     * This should not be over riden by subclasses.
     */
    @Override
    public final void onBackPressed()
    {
        if (!getController().onBackPressed())
            super.onBackPressed();
    }
    
    /**
     * Function called when an action bar button is pressed.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (!getController().onOptionsItemSelected(item)) {
            return super.onOptionsItemSelected(item);
        }
        
        return true;
    }
    
    /**
     * Function called when an activity started by this Activity
     * finishes.
     */
    @Override
    protected final void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (!getController().onActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

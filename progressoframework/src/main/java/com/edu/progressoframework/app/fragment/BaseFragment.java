package com.edu.progressoframework.app.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.edu.progressoframework.app.controller.BaseController;
import com.edu.progressoframework.app.controller.BaseFragmentController;
import com.edu.progressoframework.model.BaseModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import java.util.Observer;

/**
 * The base class for all fragments of the application.
 */
@EFragment
public abstract class BaseFragment extends Fragment implements Observer
{	
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
	public abstract BaseFragmentController getController();
	
	/**
	 * Returns the model associated with this activity
	 * @return
	 */
	public abstract BaseModel getModel();
	
	/**
	 * Function to create a model
	 */
	protected abstract void createModel();
	
	/**
	 * Function called to create the fragment
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
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
	 * Function called when fragment resume
	 */
	@Override
	public void onResume() 
	{
		if (getModel() != null) {
    		//By this time we have processed anything related to 
    		//orientation changes. Hence reset the flag
            getModel().setIsSavedInstance(false);
    	}
		super.onResume();
	}
	
    /**
     * Function called to initialise the view with
     * the initial data when the activity is created
     */
    @AfterViews
    public void initView()
    {
    	// make the controller aware about the view
    	BaseController controller = getController();
    	if (controller != null) {
    		getController().setFragment(this);
    	}

    	//initialise the model, if the activity has one
    	BaseModel model = getModel();    	
    	if (model != null) {
        	getController().initModel(model);    
	    	model.addObserver(this);
    	}

    	//update the view with data contained in the model
    	update();	
    	
    	//perform view specific initialisations
    	initViewSpecific();
    }

	/**
     * Function called when an action bar button is pressed.
     * This should not be overriden by subclasses.
     */
    @Override
    public final boolean onOptionsItemSelected(MenuItem item)
    {
        if (!getController().onOptionsItemSelected(item))
            return super.onOptionsItemSelected(item);
        
        return true;
    }
    
        /**
     * Function called when an activity started by this Fragment
     * finishes.
     * This should not be overriden by subclasses.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (!getController().onActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    
    /**
     * Function called when the activity is about to be killed.
     * This function is called to save the state of the activity
     * so that it can be restored later in case it is activated
     * during the current life time of the application.
     */
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
      
        
        //get the contents of the controls into the model
        updateModel();
        
        //save the state of the fragment - this data is contained
        //within the model
        String className = getClass().toString();
        BaseModel model = getModel();

        if (model != null) {
        	//set a flag in the model to indicate that we have a saved
        	//instance of the fragment. This flag will be used for 
        	//detecting orientation changes.
        	model.setIsSavedInstance(true);
        	outState.putParcelable(className, model);
        }
    }
}

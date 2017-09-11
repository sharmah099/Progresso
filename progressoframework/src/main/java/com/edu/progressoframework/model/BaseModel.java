package com.edu.progressoframework.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Observable;


/**
 * The base class for all models of the application.
 */
public abstract class BaseModel extends Observable implements Parcelable
{
	/**
	 * Function called to initialise the model. Note that 
	 * implementation of this function should NOT use the 
	 * setters functions of the corresponding derived classes
	 * to set the values of data members of the model.
	 */
	public abstract void initModel();		
	
	/**
	 * A equivalent of a copy constructor
	 */
	public abstract void initModel(BaseModel model);
	
	/**
	 * Function that reads the contents of a parcel
	 * and initialises the member variables of this class
	 * @param in	The Parcel containing data
	 */
	public abstract void readFromParcel(Parcel in);

	/**
	 * Flag indicating if the model has been parceled
	 * into a bundle in onSaveInstanceState() of activity
	 */
	private boolean isSavedInstance;

	/**
	 * Getter
	 */
	public boolean isSavedInstance()
	{
		return isSavedInstance;
	}

	/**
	 * Setter
	 * @param isSavedInstance
	 */
	public void setIsSavedInstance(boolean isSavedInstance)
	{
		this.isSavedInstance = isSavedInstance;
	}
}
package com.edu.progressoframework.app.element;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * An element in the list view adapter
 */
@SuppressLint("ParcelCreator")
public class AdapterElement implements Parcelable
{
	/**
	 * Element id
	 */	
	private String id;

	/**
	 * Element text
	 */	
	private String text;

	/**
	 * Function called to get the id for element
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Function called to set the id for element
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * Function called to get the text for element
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * Function called to set the text for element
	 */
	public void setText(String text)
	{
		this.text = text;
	}
	
	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
	}	
}
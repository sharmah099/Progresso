package com.edu.progressoframework.app.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.edu.progressoframework.app.element.AdapterElement;

import java.util.ArrayList;

/**
 * Adapter that provides necessary inputs to render the items in the
 * list view dialog
 */
public class ListViewDialogAdapter extends ArrayAdapter<AdapterElement>
{	
	/**
	 * Constructor
	 * @param activity	    The activity associated with the adapter
	 * @param resource	    The list view item resource id
	 * @param adapterElement	The list of dictionaryElements
	 */
	public ListViewDialogAdapter(Context ctx, int resource, ArrayList<AdapterElement> adapterElement)
	{
		super(ctx, resource, adapterElement);
	}
	
	/**
     * Returns the view at the specified position
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
    	AdapterElement entity = getItem(position);
    	TextView textView;
		
    	//use an existing view if available
		View listItem = convertView;
		if (listItem == null) {
			//existing view not available, create a new one
			LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			listItem = vi.inflate(android.R.layout.simple_list_item_1, parent, false);
		}
		textView = (TextView) listItem;
		textView.setText(entity.getText());
		
		return listItem;
    }
}
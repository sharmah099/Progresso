package com.edu.progressoframework.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.edu.progressoframework.common.ProgressoLog;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Class that executes the create and upgrade scripts
 * by parsing the XML file
 */
public class ScriptExecutor
{
	/**
	 * Context
	 */
	private Context context;

	/**
	 * Constructor
	 */
	public ScriptExecutor(Context context)
	{
		this.context = context;	
	}
	
	/**
	 * Executes the scripts contained in the asset
	 * file whose name is passed to this function
	 * @param db		The database on which to execute the scripts
	 * @param fileName	The XML file containing the scripts
	 */
	public void executeScripts(SQLiteDatabase db, String fileName)
	{
		try {
			AssetManager manager = context.getResources().getAssets();
			InputStream input = manager.open(fileName);

			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setValidating(false);

			XmlPullParser parser = factory.newPullParser();
			parser.setInput(input, null);
			int eventType = parser.getEventType();
			
			//iterate over the XML document until we reach the end
			boolean startQueryEncountered = false;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String tagName = parser.getName();
				
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if (tagName.equalsIgnoreCase("query")) {
						startQueryEncountered = true;
					}
					break;
				case XmlPullParser.END_TAG:
					if (tagName.equalsIgnoreCase("query")) {
						startQueryEncountered = false;
					}
					break;
				case XmlPullParser.TEXT:
					if (startQueryEncountered) {
						String query = parser.getText();
						executeQuery(db, query);
					}
					break;
				}
				
				eventType = parser.next();
			}
		} 
		catch (IOException ioe) {
			//do not worry about the exception - the file may not exist
			ProgressoLog.getInstance(context).i("Progresso:ScriptExecutor", "Error executing: " + fileName + ". This file may not exist.");
		}
		catch (XmlPullParserException xppe) {
			//do not worry about the exception - the file may not exist
			ProgressoLog.getInstance(context).i("Progresso:ScriptExecutor", "Error executing: " + fileName + ". Possiblly invalid XML contents.");
		}
	}
	
	/**
	 * Function called to execute a query on the specified database
	 */
	private void executeQuery(SQLiteDatabase db, String query)
	{
		try {
			db.execSQL(query);
		}
		catch (SQLException sqle) {
			ProgressoLog.getInstance(context).w("Progresso:ScriptExecutor", "Error executing: " + query +
					". Possibly no change required for database.");
		}
	}
}

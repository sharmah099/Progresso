package com.edu.progressoframework.database;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic DAO to create and access a table holding id-value
 * pairings in a database.
 * 
 */
public abstract class IdDescriptionDAO extends BaseDAO<IdDescriptionEntity>
{
	/**
	 * The primary key column
	 */
    protected static final String ID 	= "_id" ;
    
    /**
     * The description column
     */
    protected static final String DESCRIPTION 	= "Description";
	
    /**
     * Constructor
     */
	public IdDescriptionDAO(BaseSQLiteOpenHelper dbHelper) 
	{
		super(dbHelper);
	}

	 /**
     * Returns the table creation script for the tables
     * accessed by this DAO.
     */
	@Override
	public List<String> getCreateCommands()
	{ 
		List<String> ret = new ArrayList<String>();
		ret.add("CREATE TABLE " + getTableName() + "(" + 
				ID + " INTEGER PRIMARY KEY, " +		//the primary key
				DESCRIPTION + " TEXT NOT NULL " +	 //the description
		");");
		
		return ret;
	}

	 /**
     * Returns the table drop scripts
     */
	@Override
	public List<String> getDeleteCommands()
	{
		List<String> ret = new ArrayList<String>();
		ret.add("DROP TABLE IF EXISTS " + getTableName() + ");");
		return ret;
	}

	/**
	 * Returns an entity using a cursor
	 */
	@Override
	protected IdDescriptionEntity getEntityFromCursor(Cursor cursor)
	{		
		int id = cursor.getInt(cursor.getColumnIndex(ID));
		String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
		return new IdDescriptionEntity(id, description);
	}

	/**
	 * Returns the content values.
	 */
	@Override
	protected ContentValues getContentValues(IdDescriptionEntity entity)
	{
		ContentValues contentValues = new ContentValues();
		contentValues.put(ID, entity.getId());
		contentValues.put(DESCRIPTION, entity.getDescription());
		return contentValues;
	}
}

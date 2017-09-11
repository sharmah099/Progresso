package com.edu.progressoframework.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic DAO to create and access a table holding key-value
 * pairings in a database.
 * 
 * Note: unlike the other DAOs, this class does not use its ID field.
 * 
 */
public abstract class KeyValueDAO extends BaseDAO<KeyValueEntity>
{
	/**
	 * The columns of the table
	 */
    protected static final String KEY 		= "Key";
    protected static final String VALUE 	= "Value";

    /**
     * Constructor
     * @param dbHelper
     */
    public KeyValueDAO(BaseSQLiteOpenHelper dbHelper)
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
        		//ID + " INTEGER PRIMARY KEY, " +		//the primary key
        		KEY + " TEXT NOT NULL UNIQUE, " +	//the key
                VALUE + " TEXT NOT NULL " +			//the value
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
     * Returns entity identified by the specified ID,
     * Note: unlike the other DAOs, this class does not 
     * use its ID field. Hence this function cannot be used
     */
    @Override
    public KeyValueEntity get(long id)
    {
        throw new RuntimeException("Use get(String) instead!");
    }

    /**
     * Returns the key-value pair for the specified key
     */
    public KeyValueEntity get(String key)
    {
        KeyValueEntity ret = null;
        List<KeyValueEntity> entities = getEntities(KEY + "='" + key + "'", null);

        if (entities.size() == 1) {
            ret = entities.get(0);
        }
        else if (entities.size() > 1) {
            throw new SQLException("More than one row returned.");
        }

        return ret;
    }

    /**
     * Returns the string value for the specified key
     */
    public String getString(String key)
    {
        String ret = null;
        KeyValueEntity entity = get(key);
        if (entity != null) {
            ret = entity.getValue();
        }

        return ret;
    }

    /**
     * Returns the boolean value for the specified key
     */
    public boolean getBoolean(String key)
    {
        KeyValueEntity entity = get(key);
        return (entity != null && entity.getValue().equals("true"));
    }

    /**
     * This function uses getInt(String) and returns the default
     * value provided if the method returns -1.
     */
    public int getInt(String key, int defaultValue)
    {
        int ret = getInt(key);
        if (ret == -1) {
            ret = defaultValue;
        }
        
        return ret;
    }

    /**
     * Returns an int value for the specified key
     */
    public int getInt(String key)
    {
        KeyValueEntity entity = get(key);
        int ret = -1;

        if (entity != null) {
            ret = Integer.parseInt(entity.getValue());
        }

        return ret;
    }

    /**
     * Returns an long value for the specified key
     */
    public long getLong(String key)
    {
    	KeyValueEntity entity = get(key);
    	long ret = -1;

    	if (entity != null) {
    		ret = Long.parseLong(entity.getValue());
    	}

    	return ret;
    }
    
    /**
     * Updates an existing record in the database
     * @param entity    The entity to be updated
     * @return the number of rows updated
     */
    @Override
    public int update(KeyValueEntity entity)
    {
        return dbHelper.getDatabase().update(getTableName(),
                getContentValues(entity), KEY + "=" + entity.getKey(), null);
    }

    /**
     * Function to delete an entity
     * @param key    The key of the entity to be deleted
     * @return the number of rows deleted
     */
    public int delete(String key)
    {
        return dbHelper.getDatabase().delete(getTableName(), KEY + "='" + key + "'", null);
    }

    /**
     * Returns an entity using a cursor
     */
    @Override
    protected KeyValueEntity getEntityFromCursor(Cursor cursor)
    {
        String key = cursor.getString(cursor.getColumnIndex(KEY));
        String value = cursor.getString(cursor.getColumnIndex(VALUE));

        return new KeyValueEntity(key, value);
    }

    /**
     * Returns the content values.
     */
    @Override
    protected ContentValues getContentValues(KeyValueEntity entity)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY, entity.getKey());
        contentValues.put(VALUE, entity.getValue());
        return contentValues;
    }
}
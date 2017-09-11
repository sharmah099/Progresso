package com.edu.progressoframework.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The base data access class
 */
public abstract class BaseDAO<T extends BaseEntity>
{
    public static final String ID = "_id";

    /**
     * The database adapter
     */
    protected BaseSQLiteOpenHelper dbHelper;

    /**
     * Constructor
     * @param dba	The database adapter
     */
    public BaseDAO(BaseSQLiteOpenHelper dbHelper)
    {
        this.dbHelper = dbHelper;
    }

    /**
     * @return The name of the database table this DAO deals with.
     */
    public abstract String getTableName();

    /**
     * @return The strings that define how to create the
     * tables used to hold this class's entity types.
     * If the table uses any subtables they should also
     * be created here.
     */
    public abstract List<String> getCreateCommands();

    /**
     * @return The strings that define how to delete the
     * tables used to hold this class's entity types.
     * If the table uses any subtables they should also
     * be deleted here.
     */
    public abstract List<String> getDeleteCommands();

    /**
     * @param cursor The cursor pointing to the content values
     * to be converted into an entity
     * @return The newly created entity
     */
    protected abstract T getEntityFromCursor(Cursor cursor);

    /**
     * @param entity The entity for which we want to create
     * content values.
     * @return The content values representing this entity.
     */
    protected abstract ContentValues getContentValues(T entity);

    /**
     * Returns entity with the specified id
     * @param id	The entity id
     * @return		The entity
     */
    public T get(long id)
    {
        T ret = null;
        List<T> entities = getEntities(ID + "=" + id, null);

        if (entities.size() == 1) {
            ret = entities.get(0);
        }
        else if (entities.size() > 1) {
            throw new SQLException("More than one row returned.");
        }

        return ret;
    }

    /**
     * Returns all the entities that match the specified criteria
     * @return	All entities
     */
    public ArrayList<T> getEntities(String whereClause, String orderBy)
    {
        Cursor cursor = null;
        ArrayList<T> ret = new ArrayList<T>();
        try
        {
            cursor = dbHelper.getDatabase().query(getTableName(), null, whereClause, null, null, null, orderBy);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ret.add(getEntityFromCursor(cursor));
                    cursor.moveToNext();
                }
                while (!cursor.isAfterLast());
            }

            return ret;
        }
        finally
        {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * Inserts a record into the database
     * @param entity	The entity to be inserted
     * @return the row ID of the newly inserted row,
     * or -1 if an error occurred.
     */
    public long insert(T entity)
    {
        return dbHelper.getDatabase().insert(getTableName(), null,
                getContentValues(entity));
    }

    /**
     * Updates an existing record from the database
     * @param entity	The entity to be updated
     * @return the number of rows updated
     */
    public int update(T entity)
    {
        return dbHelper.getDatabase().update(getTableName(),
                getContentValues(entity), ID + "=" + entity.getId(), null);
    }

    /**
     * Function to delete an entity
     * @param id	The id of the entity to be deleted
     * @return the number of rows deleted
     */
    public int delete(int id)
    {
        return dbHelper.getDatabase().delete(getTableName(), ID + "=" + id, null);
    }

    /**
     * This function wraps the common use case of inserting
     * a new record if it doesn't already exist, or updating
     * the current record if it already exists.
     * @param entity the entity to change
     * @return the number of rows changed
     */
    public long insertOrUpdate(T entity)
    {
        return dbHelper.getDatabase().insertWithOnConflict(getTableName(), null,
                getContentValues(entity), SQLiteDatabase.CONFLICT_REPLACE);
    }

    /**
     * Function that returns the number of rows in the table
     * that matches the search criteria.
     * @param whereClause 	The where clause
     * @return
     */
    public int getCount(String whereClause)
    {
    	String sqlQuery = "SELECT COUNT(*) FROM " + getTableName();
    	if (!TextUtils.isEmpty(whereClause)) {
    		sqlQuery +=  (" WHERE " + whereClause);
    	}
    	
    	int count = 0;
        Cursor cursor = dbHelper.getDatabase().rawQuery(sqlQuery, null);
    	if (cursor != null && cursor.moveToFirst()) {
    		count = cursor.getInt(0);
        	cursor.close();
        	return count;
    	}
    	else {
    		throw new SQLException("Could not get the count for table: " + getTableName());
    	}    	
    }
    
    /**
     * Function that returns the next id for the specified DAO
     */
    public long getNextId()
    {
        int i = -1;
        Cursor cursor = dbHelper.getDatabase().rawQuery("SELECT MAX("+ ID + ") FROM " + getTableName(), null);
        if (cursor != null && cursor.moveToFirst()) {
            i = cursor.getInt(0);
            i++;
            cursor.close();
            return i;
        }
        else {
            throw new SQLException("Could not get the next id for table: " + getTableName());
        }
    }

    /**
     * Function to only return the number of entries this
     * where clause would result in. This should be used in
     * cases where the entire result set is not required,
     * as this query will optimise by not retrieving all
     * columns.
     */
    public int getResultCount(String whereClause)
    {
        Cursor cursor = null;
        try
        {
            cursor = dbHelper.getDatabase().query(getTableName(), new String[]{ID}, whereClause, null, null, null, null);
            return cursor.getCount();
        }
        finally
        {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
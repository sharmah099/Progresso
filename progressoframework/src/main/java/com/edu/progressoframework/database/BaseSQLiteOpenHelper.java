package com.edu.progressoframework.database;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteException;
import net.sqlcipher.database.SQLiteOpenHelper;

import com.edu.progressoframework.common.ProgressoLog;


/**
 * The base class that allows the user to access the database
 */
public abstract class BaseSQLiteOpenHelper extends SQLiteOpenHelper
{
	/**
	 * The context
	 */
	protected Context context;
	
    /**
     * The instance of the SQLite database
     */
    protected SQLiteDatabase database;

    /**
     * Constructor
     * @param context	The context
     * @param name		The database name
     * @param version	The database version
     */
    public BaseSQLiteOpenHelper(Context context, String name, int version)
    {
        super(context, name, null, version , new SQLCipherV3Helper(context));
    	this.context = context;
    }

    /**
     * This function will remove all data from
     * the tables in the database.
     * Note: the tables themselves will not be removed.
     */
    public void clearTables()
    {
        SQLiteDatabase db = getDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            String tableName = cursor.getString(cursor.getColumnIndex("name"));
            if (!tableName.equals("android_metadata"))
            {
                // deletes all rows in the table.
                db.delete(tableName, null, null);
            }
            cursor.moveToNext();
        }

        if (cursor != null)
        {
            cursor.close();
        }
    }

    /**
     * This function will print the entire database to the log.
     */
    public void printDatabase()
    {
        SQLiteDatabase db = getDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            String tableName = cursor.getString(cursor.getColumnIndex("name"));
            if (!tableName.equals("android_metadata"))
            {
            	ProgressoLog.getInstance(context).i("Progresso", tableName + ":");
                Cursor innerCursor = database.rawQuery("SELECT * FROM " + tableName, null);
                ProgressoLog.getInstance(context).i("Progresso", DatabaseUtils.dumpCursorToString(innerCursor));
                innerCursor.close();
            }
            cursor.moveToNext();
        }

        if (cursor != null)
        {
            cursor.close();
        }
    }

    /**
     * Opens the database
     * @param encryptionKey	The key used to encrypt the database
     * @throws {@link SQLiteIncorrectKeyException} if the wrong
     * encryption key is used.
     */
    public void open(String encryptionKey)
    {
        try
        {
            database = getWritableDatabase(encryptionKey);
        }
        catch (SQLiteException exception)
        {
            if (exception.getMessage().equals("file is encrypted or is not a database"))
            {
                // wrong password!
                throw new SQLiteIncorrectKeyException();
            }
            else
            {
                // something else has happened, let it continue to propogate up.
                throw exception;
            }
        }
    }

    /**
     * Closes the database
     */
    @Override
    public void close()
    {
        database.close();
    }

    /**
     * Returns the database instance so that the users
     * of this call can operate and access the database
     */
    public SQLiteDatabase getDatabase()
    {
        throwIfNotOpen();
        return database;
    }

    /**
     * Change the pragma key associated with the database
     * @param newKey	The new pragma key
     */
    public void rekey(String currentKey, String newKey)
    {
        throwIfNotOpen();
        database.execSQL("PRAGMA key = '" + currentKey + "'");
        database.execSQL("PRAGMA rekey = '" + newKey + "'");
    }

    /**
     * This function is to be used internally to throw and exception
     * if the user tries to access the database without opening
     * it first.
     */
    private void throwIfNotOpen()
    {
        if (database == null || !database.isOpen()) {
            throw new SQLiteException("Database has not been opened yet!");
        }
    }

}
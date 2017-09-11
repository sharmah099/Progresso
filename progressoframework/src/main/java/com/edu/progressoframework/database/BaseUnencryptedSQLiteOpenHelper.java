package com.edu.progressoframework.database;

import android.content.Context;

/**
 * The base class that allows the user to create and access an unencrypted database
 */
public abstract class BaseUnencryptedSQLiteOpenHelper extends BaseSQLiteOpenHelper
{
    public BaseUnencryptedSQLiteOpenHelper(Context context, String name, int version)
    {
        super(context, name, version);
    }

    /**
     * @deprecated This database is not encrypted so there is no key.
     * Use the {@link #open} method with no arguments instead.
     */
    @Override
    @Deprecated
    public void open(String encryptionKey)
    {
        throw new DatabaseNotEncryptedException("This database is not encrypted. Use the open() method instead.");
    }

    /**
     * This function should be used to open the un-encrypted database.
     */
    public void open()
    {
        super.open("");
    }

    /**
     * @deprecated This database is not encrypted so there is no key.
     */
    @Override
    @Deprecated
    public void rekey(String currentKey, String newKey)
    {
        throw new DatabaseNotEncryptedException("This database is not encrypted. The rekey(String) method should not be called on it.");
    }

}
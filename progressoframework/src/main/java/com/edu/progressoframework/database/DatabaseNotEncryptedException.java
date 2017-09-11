package com.edu.progressoframework.database;

public class DatabaseNotEncryptedException extends UnsupportedOperationException
{
    private static final long serialVersionUID = 3980412607576916732L;

    public DatabaseNotEncryptedException(String message)
    {
        super(message);
    }

}

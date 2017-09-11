package com.edu.progressoframework.database;

/**
 * An entity that represents a key-value pair
 */
public class KeyValueEntity extends BaseEntity
{
    private static final long serialVersionUID = 3830429898722744766L;

    /**
     * The key
     */
    private String key;
    
    /**
     * The value
     */
    private String value;

    /**
     * Constructor
     */
    public KeyValueEntity(String key, String value)
    {
        this.key = key;
        this.value = value;
    }

    /**
     * Overloaded constructor
     */
    public KeyValueEntity(String key, boolean value)
    {
        this(key, value ? "true" : "false");
    }

    /**
     * Overloaded constructor
     */
    public KeyValueEntity(String key, int value)
    {
        this(key, String.valueOf(value));
    }
    
    /**
     * Overloaded constructor
     */
    public KeyValueEntity(String key, long value)
    {
        this(key, String.valueOf(value));
    }

    @Override
    public long getId()
    {
        return 0;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}

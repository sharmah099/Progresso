package com.edu.progressoframework.database;

import java.io.Serializable;

/**
 * The base class for all entities
 */
public abstract class BaseEntity implements Serializable
{      
    private static final long serialVersionUID = -4762471490872310722L;

    public abstract long getId();
}
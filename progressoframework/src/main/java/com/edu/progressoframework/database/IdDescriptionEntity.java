package com.edu.progressoframework.database;

public class IdDescriptionEntity extends BaseEntity 
{	
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The primary key id
	 */
	private int id;

	/**
	 * The value
	 */
	private String description;

	/**
	 * Constructor
	 */
	public IdDescriptionEntity(int id, String desc)
	{
		this.id = id;
		this.description = desc;
	}

	/**
	 * Getter
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Setter
	 */
	public void setDescription(String value)
	{
		this.description = value;
	}

	/**
	 * Setter
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * Getter
	 */
	@Override
	public long getId() 
	{
		return id;
	}
}
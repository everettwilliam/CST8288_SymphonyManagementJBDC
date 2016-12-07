/*
 *  @(#)CoreDAO.java
 *
 */

package	sql;

import java.util.Collection;



/**
 * Core data access object interface.
 * @param MD	Model for this DAO object.
 * @param PK	Primary key for this DAO object.
 * @author Reg
 */
public interface CoreDAO<MD, PK>	{

	public final static String DRIVER_NAME		= "com.mysql.jdbc.Driver";
	public final static String URL				= "jdbc:mysql://127.0.0.1/symphony?verifyServerCertificate=false&useSSL=true";
	public final static String USER				= "eberhard";
	public final static String PASSWORD			= "spitfire";

	/**
	 *	Insert state for an entity into the datastore.
	 *	@param	model	The persistence model for the object.
	 *	@throws	DAOSysException
	 */
	public void dbInsert(MD model) throws DAOSysException;

	/**
	 *	Insert state for an entity into the datastore.
	 *	@param	model	The persistence model for the object.
	 *	@param	insertStm	Statement to retrieve the entity data from the datastore.
	 *	@throws	DAOSysException
	 */
	public void dbInsert(MD model, String insertStm) throws DAOSysException;

	/**
	 *	Find an entity by its primary key.
	 *	@param	primarykey	The primary key for the entity.
	 *	@return	The persistence model for the entity.
	 *	@throws	DAOSysException
	 * @throws sql.NoSuchEntityException 
	 */
	public MD dbSelectByPrimaryKey(PK primarykey)
		throws DAOSysException, NoSuchEntityException;

//	/**
//	 *	Find an entity by its primary key.
//	 *	@param	primarykey	The primary key for the entity.
//	 *	@param	model	The persistence model to be populated by the select.
//	 *	@return	The persistence model for the entity.
//	 *	@throws	DAOSysException
//	 *	@throws	NoSuchEntityException
//	 */
//	public MD dbSelectByPrimaryKey(PK primarykey, MD model)
//		throws DAOSysException, NoSuchEntityException;

//	/**
//	 *	Find an entity by its primary key.
//	 *	@param	primarykey	The primary key for the entity.
//	 *	@param	selectStm	Statement to retrieve the entity from the datastore.
//	 *	@param	model	The persistence model to be populated by the select.
//	 *	@return	The persistence model for the entity.
//	 *	@throws	DAOSysException
//	 *	@throws	NoSuchEntityException
//	 */
//	public MD dbSelectByPrimaryKey(PK primarykey, String selectStm, MD model)
//		throws DAOSysException, NoSuchEntityException;

	/**
	 *	Find an entity by its primary key.
	 *	@param	primarykey	The primary key for the entity.
	 *	@param	selectStm	Statement to retrieve the entity from the datastore.
	 *	@return	Persistent model for this object.
	 * @throws	DAOSysException
	 *	@throws	NoSuchEntityException
	 */
	public MD dbSelectByPrimaryKey(PK primarykey, String selectStm)
		throws DAOSysException, NoSuchEntityException;


	
	/**
	 *	Find all entity objects.
	 *	@return	Collection of primary keys for all entity objects.
	 *	@throws	DAOSysException
	 */
	public Collection<PK> dbSelectAll()	throws DAOSysException;

	/**
	 *	Find all entity objects.
	 *	@param	stm	Statement to retrieve the entity from the datastore.
	 *	@return	Collection of primary keys for all entity objects.
	 *	@throws	DAOSysException
	 */
	public Collection<PK> dbSelectAll(String stm)	throws DAOSysException;

	
	/**
	 * Update state for an entity in the datastore.
	 *	@param	data	Persistence model for the entity.
	 *	@throws	DAOSysException
	 */
	public void dbUpdate(MD data)	throws DAOSysException;

	/**
	 * Update state for an entity in the datastore.
	 *	@param	model Persistence model for the entity.
	 *	@param	updateStm	Statement to update the entity in the datastore.
	 *	@throws	DAOSysException
	 */
	public void dbUpdate(MD model, String updateStm)	throws DAOSysException;

	
	/**
	 *	Remove the state of an entity from the datastore.
	 *	@param	primarykey	The primary key for the object.
	 *	@return	The number of entities removed.
	 *	@throws	DAOSysException
	 */
	public int dbRemove(PK primarykey)		throws DAOSysException;

	/**
	 *	Remove the state of an entity from the datastore.
	 *	@param	primarykey	The primary key for the object.
	 *	@param	deleteStm	Statement to remove the entity from the datastore.
	 *	@return	The number of entities removed.
	 *	@throws	DAOSysException
	 */
	public int dbRemove(PK primarykey, String deleteStm)	throws DAOSysException;

	
	/**
	 *	Count the total number of entities.
	 *	@return	The total number of entities.
	 *	@throws	DAOSysException
	 */
	public int dbCountTotalEntities()	throws DAOSysException;

	
	/**
	 *	Load the entity data from the datastore.
	 *	@param	primarykey	The primary key object for this entity.
	 *	@return	The persistence model for this object.
	 *	@throws	DAOSysException
	 *	@throws	NoSuchEntityException
	 */
	public Object dbLoad(PK primarykey)	throws DAOSysException, NoSuchEntityException;

	/**
	 *	Load the entity data from the datastore.
	 *	@param	primarykey	The primary key object for this entity.
	 *	@param	stm	Statement to load the entity from the datastore.
	 *	@return	The persistence model for this object.
	 *	@throws	DAOSysException
	 *	@throws	NoSuchEntityException
	 */
	public Object dbLoad(PK primarykey, String stm)
			throws DAOSysException, NoSuchEntityException;

	
	/**
	 *	Store the entity data in the datastore.
	 *	@param	data	Persistence model for the entity.
	 *	@throws	DAOSysException
	 */
	public void dbStore(MD data)		throws DAOSysException;

	/**
	 *	Store the entity data in the datastore.
	 *	@param	data	Persistence model for the entity.
	 *	@param	stm	Statement to store the entity in the datastore.
	 *	@throws	DAOSysException
	 */
	public void dbStore(MD data, String stm)		throws DAOSysException;

}	/*	End of Interface:	CoreDAO.java				*/

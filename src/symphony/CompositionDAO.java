/*
 * CompositionDAO.java
 */
package symphony;

import java.util.*;
import java.sql.*;

import sql.CoreDAO;
import sql.CoreDAOImpl;
import sql.DAOSysException;
import sql.NoSuchEntityException;
import symphony.Composer;

/**
 *	Data access object for a Composition entity.
 * @author Reg
 */
public class CompositionDAO extends CoreDAOImpl<CompositionModel, CompositionPK> {

	/**
	 * Creates a new instance of CompositionDAO
	 */
	public CompositionDAO() {
		this(CoreDAO.DRIVER_NAME, CoreDAO.URL, CoreDAO.USER, CoreDAO.PASSWORD);
	}

	/**
	 *	Parameterized constructor.  When extending this class the
	 *	derived class must invoke one of this classes constructors
	 *	for proper initialization.
	 *	@param	drivername 
	 * @param	url 
	 * @param	user		Database user name.
	 *	@param	password	Database password for access.
	 */
	public CompositionDAO(String drivername,
		String url,
		String user,
		String password) {
		super(drivername, url, user, password);
	}

	/* ACCESSORS	-----------------------------------------------	*/

	/* MUTATORS	--------------------------------------------------	*/

	/* BEHAVIOR	--------------------------------------------------------	*/
	/**
	* Called by create() to insert entity state for a new entity.
	*	@param	model	Persistence data model for the entity.
	*	@throws	DAOSysException
	*/
	public void dbInsert(CompositionModel model) throws DAOSysException {
		dbInsert(model, INSERT_STATEMENT);
	}

	/**
	* Called by create() to insert entity state for a new entity.
	*	@param	model	Persistence data model for the entity.
	*	@throws	DAOSysException
	*/
	public void dbInsert(CompositionModel model, String insertStm) throws DAOSysException {
		PreparedStatement preparedStm = null;
		Connection connection = null;
		if (insertStm == null) {
			insertStm = INSERT_STATEMENT;
		}
		try {
				connection = connectToDB();
				preparedStm = connection.prepareStatement(insertStm);
				preparedStm.setString(1, model.getComposerPrimarykey().getName());
				preparedStm.setString(2, model.getCompositionName());
				int count = preparedStm.executeUpdate();
		} catch (SQLException sex) {
				throw new DAOSysException(sex.getMessage());
		} finally {
			try {
				releaseAll(null, preparedStm, connection);
			} catch (Exception ex) {
				System.err.println("Error releasing resources <" + ex.toString());
			}
		 }
	}

	/**
	 * Called by findByPrimaryKey() to retrieve entity data by the primary key.
	 *	@param	primarykey The primary key for the entity.
	 *	@throws	DAOSysException
	 *	@throws	NoSuchEntityException
	 */
	public CompositionModel dbSelectByPrimaryKey(CompositionPK primarykey)
	throws DAOSysException, NoSuchEntityException {
		 return dbSelectByPrimaryKey(primarykey, SELECT_STATEMENT);
	}

	/**
	 * Called by findByPrimaryKey() to retrieve entity data by the primary key.
	 *	@param	primarykey The primary key for the entity.
	 *	@param	selectStm	Statement to retrieved the entity data from the data store.
	 * @return	The persistence model for the entity.
	 *	@throws	DAOSysException
	 * @throws	NoSuchEntityException
	 */
	public CompositionModel dbSelectByPrimaryKey(CompositionPK primarykey, String selectStm)
	throws DAOSysException, NoSuchEntityException {
		CompositionPK pk = (CompositionPK) primarykey;
		Connection connection = null;
		PreparedStatement preparedStm = null;
		ResultSet rs = null;
		CompositionModel model = null;
		boolean result = false;
		if (selectStm == null) {
				selectStm = SELECT_STATEMENT;
		}

		try {
				connection = connectToDB();
				preparedStm = connection.prepareStatement(selectStm);
				preparedStm.setString(1, pk.getComposerName());
				preparedStm.setString(2, pk.getCompositionName());
				rs = preparedStm.executeQuery();

				result = rs.next();
				if (result) {
					model = new CompositionModel();
					model.setPrimarykey(new CompositionPK(rs.getString(1), rs.getString(2)));
					model.setComposerPrimarykey(new ComposerPK(rs.getString(1)));
				} else {
					throw new NoSuchEntityException("Composition ID for <" + primarykey
						+ "> not found in the database.");
				}

		} catch (SQLException sex) {
				throw new DAOSysException(
				 "dbSelectByPrimaryKey() SQL Exception\n" + sex.getMessage());

		} finally {
				try {
					releaseAll(rs, preparedStm, connection);
				} catch (Exception ex) {
					System.err.println("Error releasing resources <" + ex.toString());
				}
		 }

		 return model;

	}

	/**
	 * Select compositions by composer primary key.
	 *	@param composerPK 
	 *	@return A list of composition primary keys for one particular composer
	 * @throws	DAOSysException
	 * @throws	NoSuchEntityException
	 */
	public Collection<CompositionPK> dbSelectByComposer(ComposerPK composerPK)
		throws DAOSysException, NoSuchEntityException {
			return dbSelectByComposer(composerPK, SELECT_BY_COMPOSER_STATEMENT);
	}

	/**
	 * Select compositions by composer primary key.
	 *	@param composerPK 
	 *	@return A list of composition primary keys for one particular composer
	 * @throws	DAOSysException
	 * @throws	NoSuchEntityException
	 */
	public Collection<CompositionPK> dbSelectByComposer(ComposerPK composerPK, String selectStm)
		throws DAOSysException, NoSuchEntityException {
		Connection connection = null;
		PreparedStatement preparedStm = null;
		ResultSet rs = null;
		ArrayList<CompositionPK> list = null;
		if (selectStm == null) {
			selectStm = SELECT_BY_COMPOSER_STATEMENT;
		}

		try {
			connection = connectToDB();
			preparedStm = connection.prepareStatement(selectStm);
			preparedStm.setString(1, composerPK.getName());
			rs = preparedStm.executeQuery();

			list = new ArrayList<CompositionPK>();
			while (rs.next()) {
				//TODO: Don't know how the fuck to do this...
				list.add(new CompositionPK(rs.getString(1), rs.getString(2)));
			}

		} catch (SQLException sex) {
			throw new DAOSysException(
				 "dbSelectByComposer() SQL Exception\n" + sex.getMessage());
		} finally {
			try {
				releaseAll(rs, preparedStm, connection);
			} catch (Exception ex) {
				System.err.println("Error releasing resources <" + ex.toString());
			}
		}

		return list;

	}

	/**
	 * Called by findAll() to find all entities in the data store.
	 *	@return	A collection of primary keys representing all of the entities.
	 *	@throws	DAOSysException
	 */
	public Collection<CompositionPK> dbSelectAll() throws DAOSysException {
		return dbSelectAll(SELECT_DISTINCT_STATEMENT);
	}

	/**
	 * Called by findAll() to find all entities.
	 *	@param selectStm 
	 * @return	A collection of primary keys representing all of the entities.
	 *	@throws	DAOSysException
	 */
	public Collection<CompositionPK> dbSelectAll(String selectStm) throws DAOSysException {
		Connection connection = null;
		PreparedStatement preparedStm = null;
		ResultSet rs = null;
		ArrayList<CompositionPK> list = null;
		if (selectStm == null) {
			selectStm = SELECT_DISTINCT_STATEMENT;
		}

		try {
			connection = connectToDB();
			preparedStm = connection.prepareStatement(selectStm);
			rs = preparedStm.executeQuery();

			list = new ArrayList<CompositionPK>();
			while (rs.next()) {
				
				list.add(new CompositionPK(rs.getString(1), rs.getString(2)));
			}
		} catch (SQLException sex) {
				throw new DAOSysException(
				 "dbSelectAll() SQL Exception\n" + sex.getMessage());
		} finally {
			try {
				releaseAll(rs, preparedStm, connection);
			} catch (Exception ex) {
				System.err.println("Error releasing resources <" + ex.toString());
			}
		}

		return list;
	}

	/**
	 * Called by update() to update state for an entity in the datastore.
	 *	@param	model	Persistence model for the entity.
	 *	@throws	DAOSysException
	 */
	public void dbUpdate(CompositionModel model) throws DAOSysException {
		dbUpdate(model, UPDATE_STATEMENT);
	}

	/**
	* Called by update() to update state for an entity in the database.
	*	@param	model	Persistence model for the entity.
	*	@param	updateStm	Data store update statement.
	*	@throws	DAOSysException
	*/
	public void dbUpdate(CompositionModel model, String updateStm)
	throws DAOSysException {
		Connection connection = null;
		PreparedStatement preparedStm = null;
		if (updateStm == null) {
			updateStm = UPDATE_STATEMENT;
		}

		try {
			connection = connectToDB();
			preparedStm = connection.prepareStatement(updateStm);

			/*	Grab values from persistent fields to store in datastore	*/
			preparedStm.setString(1, model.getComposerPrimarykey().getName());
			preparedStm.setString(2, model.getCompositionName());

			int rowCount = preparedStm.executeUpdate();
			if (rowCount == 0) {
				throw new DAOSysException(
					"Failed to store state for Composition <" + model.getCompositionName() + ">");
			}

		} catch (SQLException sex) {
				throw new DAOSysException(
				 "dbUpdate() SQL Exception <" + sex.getMessage() + ">");

		} finally {
			try {
				releaseAll(null, preparedStm, connection);
			} catch (Exception ex) {
				System.err.println("Error releasing resources <" + ex.toString());
			}
		}
	}

	/**
	 * Called by remove() to remove the state for an entity from the data store.
	 *	@param	primarykey	The primary key of the entity to be removed.
	 *	@throws	DAOSysException
	 */
	public int dbRemove(CompositionPK primarykey) throws DAOSysException {
		return dbRemove(primarykey, DELETE_STATEMENT);
	}

	/**
	 * Called by remove() to remove the state for a Composition entity from the database.
	 *	@param	primarykey	The primary key of the Composition entity
	 *	to be removed from the data store.
	 *	@throws	DAOSysException
	 */
	public int dbRemove(CompositionPK primarykey, String deleteStm)
	throws DAOSysException {
		Connection connection = null;
		PreparedStatement preparedStm = null;
		int result = 0;
		if (deleteStm == null) {
			deleteStm = DELETE_STATEMENT;
		}

		try {
			connection = connectToDB();
			preparedStm = connection.prepareStatement(deleteStm);
			preparedStm.setString(1, primarykey.getCompositionName());
			result = preparedStm.executeUpdate();

			if (result == 0) {
				throw new SQLException(
						"Failed to remove Composition <" + primarykey.toString() + ">.");
			}

		} catch (SQLException sex) {
			throw new DAOSysException(
				 "dbRemoveComposition() SQL Exception\n" + sex.getMessage());
		} finally {
			try {
				releaseAll(null, preparedStm, connection);
			} catch (SQLException sqlex) {
				throw new DAOSysException(sqlex.toString());
			}
		}

		 return result;
	}

	/**
	* Called by getTotalCompositions(). A select query to count the number of records in the
	* database (the number of Compositions).
	*/
	public int dbCountTotalEntities()
	throws DAOSysException {
		String selectStm = SELECT_DISTINCT_STATEMENT;
		Connection connection = null;
		PreparedStatement preparedStm = null;
		ResultSet rs = null;
		int count = 0;

		try {
			connection = connectToDB();
			/*	Request a resultset that is scrollable to easily count rows	*/
			preparedStm = connection.prepareStatement( selectStm,
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			rs = preparedStm.executeQuery();

			/*	Go to the last row and get its row number							*/
			rs.last();
			count = rs.getRow();

		} catch (SQLException sex) {
			throw new DAOSysException(
			 "dbCountTotalCompositions() SQL Exception\n" + sex.getMessage());

		} finally {
			try {
				releaseAll(rs, preparedStm, connection);
			} catch (SQLException sqlex) {
				throw new DAOSysException(sqlex.toString());
			}
	}

		 return count;
	}
	
	/* ATTRIBUTES	-----------------------------------------------	*/
	private final static String DELETE_STATEMENT =
			"DELETE FROM" 
			+ " Composition" 
			+ " WHERE compositionname = ?";
	
	private final static String UPDATE_STATEMENT =
			"UPDATE Composition SET compositionName = ?" 
			+ " composer = ? where compositionName = ?";
	
	private final static String SELECT_DISTINCT_STATEMENT =
			"SELECT DISTINCT composer, compositionName" 
			+ " FROM " + "Composition";
	
	private final static String SELECT_BY_COMPOSER_STATEMENT =
			"SELECT composer, compositionname FROM Composition"
			+ " WHERE composer = ? ";
	
	private final static String SELECT_STATEMENT =
			"SELECT composer, compositionName"
			+ " FROM Composition" 
			+ " WHERE composer = ? AND compositionName = ? ";
	
	private final static String INSERT_STATEMENT =
			"INSERT INTO Composition" 
			+ " VALUES " + "( ?, ? )";

	private final static boolean _debug = false;

}	/*	End of Class:	CompositionDAO.java				*/

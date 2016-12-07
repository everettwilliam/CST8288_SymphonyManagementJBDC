/* **************************************************************
 * Algonquin College - School of Advanced Technology
 * CST 8288 - Object Oriented Programming with Design Patterns
 * Project 2 - Symphony JBDC
 * 
 * Author: EVERETT HOLDEN
 * Student #: 040812130
 * Network login name: hold0052
 * Lab instructor: CAROLYN A. MACISAAC
 * Section: 014
 * Due date: 2016.12.08
 *
 *  -- Class definition 				   
 * Purpose -- 
 * **************************************************************/
package symphony;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import sql.CoreDAO;
import sql.CoreDAOImpl;
import sql.DAOSysException;
import sql.NoSuchEntityException;

/**
 * Description of this class.
 * @author    EVERETT HOLDEN
 * @version   1.0.0 2016.12.01
 */
public class MovementDAO extends CoreDAOImpl<MovementModel, MovementPK>	{

	/* CONSTRUCTORS	-----------------------------------------------------	*/
	public MovementDAO(){
		this(CoreDAO.DRIVER_NAME, CoreDAO.URL, CoreDAO.USER, CoreDAO.PASSWORD);		
	}

	public MovementDAO(String drivername, String url, String user, String password){
		super(drivername, url, user, password);
	}


	/* ACCESSORS	-----------------------------------------------------	*/



	/* MODIFIERS	-----------------------------------------------------	*/



	/* NORMAL BEHAVIOR --------------------------------------------------	*/

	public void dbInsert(MovementModel model) throws DAOSysException{
		dbInsert(model, MovementDAO.INSERT_STM);
	}

	public void dbInsert(MovementModel model, String insertStm) throws DAOSysException{
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try{
			connection = connectToDB();
			preparedStatement = connection.prepareStatement(insertStm);
			preparedStatement.setInt(1, model.getMovementNumber());
			preparedStatement.setString(2, model.getMovementName());
			preparedStatement.executeUpdate();

		}catch(SQLException e){
			throw new DAOSysException("Error adding Movement <" + model.getMovementName() + model.getMovementNumber() +"> " + e.getMessage());
		}finally{
			try{
				releaseAll(null, preparedStatement, connection);
			}catch(Exception e){
				System.err.println("Error releasing resources <" + e.toString());
			}			
		}
	}
	
	public Collection<MovementPK> dbSelectByComposition(CompositionPK compositionPk) throws DAOSysException, NoSuchEntityException {
				return dbSelectByComposition(compositionPk, SELECT_BY_COMPOSITION_STATEMENT);
		}

	public Collection<MovementPK> dbSelectByComposition(CompositionPK compositionpk, String selectStm) throws DAOSysException, NoSuchEntityException {
		
		Connection connection = null;
		PreparedStatement preparedStm = null;
		ResultSet rs = null;
		ArrayList<MovementPK> list = null;
		if (selectStm == null) {
			selectStm = SELECT_BY_COMPOSITION_STATEMENT;
		}

		try {
			connection = connectToDB();
			preparedStm = connection.prepareStatement(selectStm);
			preparedStm.setString(1, compositionpk.getCompositionName());
			preparedStm.setString(2, compositionpk.getComposerName());
			rs = preparedStm.executeQuery();

			list = new ArrayList<MovementPK>();
			while (rs.next()) {
				list.add(new MovementPK(rs.getInt(1), rs.getString(2)));
			}

		} catch (SQLException e) {
			throw new DAOSysException(
					"dbSelectByCustomer() SQL Exception\n" + e.getMessage());
		} finally {
			try {
				releaseAll(rs, preparedStm, connection);
			} catch (Exception e) {
				System.err.println("Error releasing resources <" + e.toString());
			}
		}

		return list;
	}

		@Override
		public MovementModel dbSelectByPrimaryKey(MovementPK primarykey) throws DAOSysException, NoSuchEntityException {

			return dbSelectByPrimaryKey(primarykey, MovementDAO.SELECT_STM);
		}

		@Override
		public MovementModel dbSelectByPrimaryKey(MovementPK primarykey, String selectStm)
				throws DAOSysException, NoSuchEntityException {
			if(_debug){
				System.out.println("CDAO:dbSelectByPrimaryKey(key, stm, model)");
			}
			MovementPK pk = (MovementPK) primarykey;
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			boolean result = false;
			MovementModel model = new MovementModel();
			try{
				connection = connectToDB();
				preparedStatement = connection.prepareStatement(selectStm);
				preparedStatement.setInt(1, pk.getMovementNumber());
				preparedStatement.setString(2, pk.getMovementName());
				resultSet = preparedStatement.executeQuery();
				result = resultSet.next();
				if(result){
					model.setPrimarykey(new MovementPK(resultSet.getInt(1), resultSet.getString(2)));
				}else{
					throw new NoSuchEntityException("Movement <" + primarykey + "> not found in the database.");
				}

			}catch(SQLException e){
				throw new DAOSysException("dbSelectByPrimaryKey() SQL Exception\n" + e.getMessage());
			}finally{
				try{
					releaseAll(resultSet, preparedStatement, connection);
				}catch(Exception e){
					System.err.println("Error releasing resources <" + e.toString());
				}
			}
			return model;
		}

		public Collection<MovementPK> dbSelectAll() throws DAOSysException{
			return dbSelectAll(MovementDAO.SELECT_ALL_STM);		
		}

		public Collection<MovementPK> dbSelectAll(String selectStm) throws DAOSysException{

			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			ArrayList<MovementPK> list = null;

			try{
				connection = connectToDB();
				preparedStatement = connection.prepareStatement(selectStm);
				resultSet = preparedStatement.executeQuery();

				list = new ArrayList<MovementPK>();
				while (resultSet.next()){
					list.add(new MovementPK(resultSet.getInt(1),resultSet.getString(2)));
				}

			}catch (SQLException e){
				throw new DAOSysException("dbSelectAll() SQL Exception\n" + e.getMessage());
			}finally{

				try{
					releaseAll(resultSet, preparedStatement, connection);
				}catch (Exception e){
					System.err.println("Error releasing resources <" + e.toString());
				}
			}
			return list;		
		}

		public void dbUpdate(MovementModel data, String updateStm) throws DAOSysException{
			MovementModel model = data;
			Connection connection = null;
			PreparedStatement preparedStatement = null;

			try{

				connection = connectToDB();
				preparedStatement = connection.prepareStatement(updateStm);
				preparedStatement.setInt(1, model.getMovementNumber());
				preparedStatement.setString(2, model.getMovementName());

				int rowCount = preparedStatement.executeUpdate();
				if (rowCount == 0)	{
					throw new DAOSysException("Failed to store state for Customer <" + model.getMovementNumber() + model.getMovementName() + ">");
				}
			}catch(SQLException e){

			}finally{
				try{
					releaseAll(null, preparedStatement, connection);
				}catch(Exception e){
					System.err.println("Error releasing resources <" + e.toString());			}
			}
		}

		@Override
		public void dbUpdate(MovementModel data) throws DAOSysException {
			dbUpdate(data, MovementDAO.UPDATE_STM);

		}

		@Override
		public int dbRemove(MovementPK primarykey) throws DAOSysException {		
			return dbRemove(primarykey, MovementDAO.DELETE_STM);
		}

		public int dbRemove(MovementPK primarykey, String deleteStm) throws DAOSysException{

			MovementPK pk = primarykey;
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			int result = 0;

			try{

				connection = connectToDB();
				preparedStatement = connection.prepareStatement(deleteStm);
				preparedStatement.setInt(1, pk.getMovementNumber());
				preparedStatement.setString(2, pk.getMovementName());
				result = preparedStatement.executeUpdate();

				if(result == 0)	{
					throw new SQLException("Failed to remove Movement <"+ pk.toString() + ">.");
				}

			}catch(SQLException e)	{
				throw new DAOSysException(
						"dbRemove() SQL Exception <" + pk.toString() + "> " + e.getMessage());

			}finally{
				try{
					releaseAll(null, preparedStatement, connection);
				}catch (SQLException e){
					throw new DAOSysException(e.toString());
				}
			}
			return result;
		}


		public int dbCountTotalEntities() throws DAOSysException{

			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			int count = 0;

			try{
				connection = connectToDB();
				preparedStatement = connection.prepareStatement(MovementDAO.SELECT_DISTINCT_STM, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				resultSet = preparedStatement.executeQuery();
				resultSet.last();
				count = resultSet.getRow();

			}catch(SQLException e){
				throw new DAOSysException("dbCountTotalCustomers() SQL Exception\n" + e.getMessage());
			}finally{
				try{
					releaseAll(resultSet, preparedStatement, connection);
				}catch(SQLException e){
					throw new DAOSysException(e.toString());
				}			
			}
			return count;
		}

		/* HELPER METHODS	--------------------------------------------------	*/


		/* ATTRIBUTES	-----------------------------------------------------	*/											
		private final static boolean _debug = false;

		private final static String SELECT_DISTINCT_STM =
				"SELECT DISTINCT number FROM " + "Movemnt";

		private final static String DELETE_STM =
				"DELETE FROM " + "Movement"
						+ " WHERE movementNumber = ? AND movmentName = ?";

		private final static String UPDATE_STM =
				"UPDATE " + "Movement"
						+ " SET "
						+ "movementNumber = ? "
						+ "movementName = ?";

		private final static String SELECT_ALL_STM =
				"SELECT DISTINCT movementName, movementNumber " + "FROM " + "Movement";	
		
		private final static String SELECT_BY_COMPOSITION_STATEMENT =
				"SELECT movementNumber, movementName FROM movements"
					+ " WHERE compositionName = ?" 
					+ " AND composer = ?";

		private final static String SELECT_STM = "SELECT "
				+ " movementNumbner, "
				+ " movementName "
				+ " FROM MOVMENT "
				+ " WHERE movementName = ? AND movementNumber = ?";

		private final static String INSERT_STM = "INSERT INTO "
				+ "Movment"
				+ " VALUES "
				+ "( ?, ? )";	

	}	/*	End of CLASS:	MovementDAO.java			*/
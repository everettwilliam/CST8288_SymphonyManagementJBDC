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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import sql.CreateException;
import sql.DAOFactory;
import sql.DAOSysException;
import sql.FinderException;
import sql.NoSuchEntityException;

/**
 * Description of this class.
 * @author    EVERETT HOLDEN
 * @version   1.0.0 2016.12.01
 */
public class Movement {


	public static Movement create(Integer movementNumber, String movementName, Composition composition) throws CreateException{

		if (isDebugging()){
			System.out.println("Boat.create:" + movementNumber + " " + movementName);
		}
		if (composition == null){
			throw new CreateException ("Invalid Customer <" + composition + ">");
		}

		MovementModel model = new MovementModel(movementNumber, movementName, composition.getPrimaryKey());
		MovementDAO dao = null;
		try	{
			dao = (MovementDAO) DAOFactory.getDAO(className);
			dao.dbInsert(model);

		} catch (Exception ex)	{
			System.out.println("B: Error inserting boat <" + movementNumber + " " + movementName + "> " + ex);
			ex.printStackTrace();
			throw new CreateException(ex.getMessage());
		}

		Movement b = new Movement(model, composition);
		if (isDebugging()) System.out.println("Boat.create: ---------- complete for boat:" + movementNumber);
		return	b;
	}

	public static Movement findByPrimaryKey(MovementPK primarykey) throws FinderException, NoSuchEntityException{
		return Movement.findByPrimaryKey(primarykey, null);
	}

	public static Movement findByPrimaryKey(MovementPK primarykey, Composition composition) throws FinderException, NoSuchEntityException{
		if (isDebugging()){
			System.out.println("Movement.findByPrimarykey(" + primarykey + ", " + composition.getPrimaryKey() + ")");
		}

		MovementModel model = null;
		Movement movement = null;
		MovementDAO dao = null;
		try	{
			dao = (MovementDAO) DAOFactory.getDAO(className);
			model = (MovementModel) dao.dbSelectByPrimaryKey(primarykey);
			if (composition == null)	{
				movement = new Movement(model);
			} else	{
				movement = new Movement(model, composition);
			}

		} catch (Exception e)	{
			if (isDebugging())	{
				System.out.println("Movement.findByPrimarykey(" + primarykey
						+ ", " + composition.getPrimaryKey() + ")"
						+ "\n\t" + e.toString()
						);
				e.printStackTrace(System.out);
			}
			throw new FinderException(e.getMessage());
		}

		return movement;
	}

	public Collection<Movement> findAll() throws FinderException, CreateException{
		
		ArrayList<Movement> listOfMovements = new ArrayList<Movement>();
		MovementDAO dao = null;
	
		try	{
			dao = (MovementDAO) DAOFactory.getDAO(className);
			Collection<MovementPK> c = dao.dbSelectAll();
			Iterator<MovementPK> itr = c.iterator();
			while (itr.hasNext())	{
				MovementPK mpk = itr.next();
				try	{
					listOfMovements.add(Movement.findByPrimaryKey(mpk));
				} catch (Exception ex)	{
					System.err.println("Composition: Error processing list <" + ex.toString());
				}
			}

		} catch (Exception sqlex)	{
			throw new FinderException(sqlex.getMessage());
		}

		return listOfMovements;
	}

	public static Collection<Movement> findByComposition(Composition composition) throws FinderException{
		if (isDebugging()) System.out.println("--------------------"
				+ "Boat.findByCustomer <" + composition.getPrimaryKey() + ">");

		ArrayList<Movement> listOfMovements = new ArrayList<Movement>();
		MovementDAO dao = null;

		try	{
			dao = (MovementDAO) DAOFactory.getDAO(className);
			Collection<MovementPK> boats = dao.dbSelectByComposition(composition.getPrimaryKey());

			Iterator<MovementPK> itr = boats.iterator();
			if (isDebugging()) System.out.println("Boats for Customer <" + composition.getPrimaryKey() + ">");

			while (itr.hasNext())	{
				MovementPK mpk = new MovementPK(( itr.next()).getMovementNumber(), ( itr.next()).getMovementName());
				if (isDebugging()) System.out.println("Boat.findByCustomer(" + composition.getPrimaryKey() + "):"
						+ "processing list-boat:" + mpk);

				try	{
					listOfMovements.add(Movement.findByPrimaryKey(mpk, composition));
				} catch (Exception ex)	{
					System.out.println("Boat: Error processing list <" + ex.toString() + ">");
					ex.printStackTrace(System.out);
				}

				if (isDebugging()) System.out.println("Boat.findByCustomer(" + composition.getPrimaryKey() + "):"
						+ "Finished for boat:" + mpk);

			}

		} catch (Exception ex)	{
			ex.printStackTrace(System.out);
			throw new FinderException(ex.getMessage());
		}

		if (isDebugging()) System.out.println("--------------------"
				+ "Boat.findByCustomer complete <" + composition.getPrimaryKey() + ">");

		return listOfMovements;
	}

	public int removeByPrimarykey(MovementPK primarykey){
		return 0;
	}


	/* CONSTRUCTORS	-----------------------------------------------------	*/

	public Movement(Integer movementNumber, String movementName, Composition composition){
		this(new MovementModel(movementNumber, movementName, composition.getPrimaryKey()));
	}

	public Movement(MovementModel model){
		if(isDebugging()){
			System.out.println("B: Boat(" + model.getMovementName() + ")");
		}
		setModel(model);
		assignToComposition(model.getCompositionPrimaryKey());
	}	

	public Movement(MovementModel model, Composition composition){
		if(isDebugging()){
			System.out.println("B: Boat(" + model.getMovementName() + ")");
		}
		setModel(model);
		assignToComposition(composition);
	}


	/* ACCESSORS	-----------------------------------------------------	*/

	public MovementModel getModel(){
		return model;
	}

	public MovementPK getPrimarykey(){
		return model.getPrimarykey();
	}

	public Integer getMovementNumber(){
		return model.getMovementNumber();
	}

	public String getMovementName(){
		return model.getMovementName();
	}
	
	public Composition getComposition(){
		return composition;
	}

	/* MODIFIERS	-----------------------------------------------------	*/

	public void setModel(MovementModel model){
		this.model = model;

	}
	
	public void setMovementNumber(Integer movementNumber){
		getModel().setMovementNumber(movementNumber);
	}
	
	public void setMovementName(String movementName){
	getModel().setMovementName(movementName);
	}

	public void setComposition(Composition composition){
		this.composition = composition;
	}

	/* NORMAL BEHAVIOR --------------------------------------------------	*/

	public void assignToComposition(CompositionPK compositionpk)		{ 
		if (isDebugging()){
			System.out.println("B:assignToComposition:" + compositionpk +")");
		}		
		try{
			Composition c = Composition.findByPrimarykey(compositionpk);
			assignToComposition(compositionpk);
		}catch(Exception e){

		}
	}

	public void assignToComposition(Composition composition)		{ 
		if (isDebugging()){
			System.out.println("B:assignToCustomer:" + composition.getCompositionName() + " B:" + getMovementNumber());
		}
		setComposition(composition);
		composition.addMovement(this);
		update();
	}

	public boolean equals(Object obj){
		return true;
	}

	public int hasCode(){
		return 0;
	}

	public void update(){
		if (isDebugging()){
			System.out.println("B:update()");
		}
		try	{
			store();
		} catch (Exception ex)	{
			System.out.println("C: Error in update(), <" + ex.toString() + ">");
		}
	}

	public String toString(){
		return this.toString(", ");
	}

	public String toString(String sep){
		return "movementNumber=" + getMovementNumber()
		+ sep + "movementName=" + getMovementName()
		+ sep + "composition=" + (getComposition() == null ? null : getComposition().getCompositionName());
	}

	public Movement remove() throws NoSuchEntityException, DAOSysException, SQLException{
		if(isDebugging()){
			System.out.println("B:remove()");
		}
		getComposition().removeMovement(this);
		Movement m = null;
		if(removeByPrimarykey(getPrimarykey()) > 0){
			m = this;
		}
		
		return m;
	}

	public void load() throws DAOSysException{

		MovementDAO dao = null;
		try	{
			dao = (MovementDAO) DAOFactory.getDAO(className);
			setModel((MovementModel)dao.dbLoad(getPrimarykey()));

		} catch (Exception ex)	{
			throw new DAOSysException(ex.getMessage());
		}
	}

	public void store() throws DAOSysException{
		MovementDAO dao = null;
		try{

			dao = (MovementDAO) DAOFactory.getDAO(className);
			dao.dbStore(getModel());

		}catch(Exception e){
			throw new DAOSysException(e.getMessage());
		}
	}


	/* HELPER METHODS	--------------------------------------------------	*/

	public static boolean isDebugging()	{ 
		return _debug;		
	}

	/* ATTRIBUTES	-----------------------------------------------------	*/											

	private final static boolean _debug = false;

	private static String className = "symphone.Movement";

	private MovementModel model;

	private Composition composition;
}	/*	End of CLASS:	Movement.java			*/
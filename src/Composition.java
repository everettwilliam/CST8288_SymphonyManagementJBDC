/*
 *  @(#)Composition.java
 *
 *
 */

package symphony;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.sql.SQLException;
import sql.DAOSysException;
import sql.CreateException;
import sql.DAOFactory;
import sql.NoSuchEntityException;
import sql.FinderException;

/**
 * Composition class
 * with Composer reference variable and methods 
 * adding Slip reference variable
 */
public class Composition		{
	/* STATIC PRE-OBJECT BEHAVIOR	-----------------------------------	*/
	/* CREATORS	-----------------------------------------------------	*/
	/**
	 *	Create an instance of a new composition.
	 *	@return	An instance of a composition entity.
	 *	@param	composer			The composer
	 *	@param	compositionName		The name of the composition.
	 */
	public static Composition create(Composer composer, String compositionName) throws CreateException {
		if (isDebugging()) {
			System.out.println("Composition.create:" + compositionName);
		}
		if (composer == null) {
			throw new CreateException ("Invalid Composer <" + composer + ">");
		}

		CompositionModel model = new CompositionModel(composer.getName(), compositionName);
		CompositionDAO dao = null;
		try	{
			dao = (CompositionDAO) DAOFactory.getDAO(className);
			dao.dbInsert(model);

		} catch (Exception ex)	{
			System.out.println("Error inserting composition <" + compositionName + "> " + ex);
			ex.printStackTrace();
			throw new CreateException(ex.getMessage());
		}

		Composition composition = new Composition(composer, compositionName);
		if (isDebugging()) {
			System.out.println("Composition.create: ---------- complete for composition:" + compositionName);
		}
		return	composition;
	}

	
	/* FINDERS	-----------------------------------------------------	*/
	/*	Finder methods are used to search for a particular instance
	 *	or a collection of instances, therefore finders either return
	 *	and instance to the entity, or a collection of instances.
	 */
	/**
	 *	Find a composition by primary key.
	 *	@return	An instance of a composition entity.
	 *	@param	primarykey	The primary key for entity to find.
	 *	@throws	ObjectNotFoundException
	 */
	public static Composition findByPrimarykey(CompositionPK primarykey) throws FinderException, NoSuchEntityException {
		return Composition.findByPrimarykey(primarykey, null);
	}

	/**
	 *	Find a composition by primary key.
	 *	@return	An instance of a composition entity.
	 *	@param	primarykey	The primary key for entity to find.
	 *	@param	composer	The composer who owns the composition.
	 *	@throws	ObjectNotFoundException
	 */
	public static Composition findByPrimarykey(CompositionPK primarykey, Composer composer) throws FinderException, NoSuchEntityException {
		if (isDebugging()) {
			System.out.println("Composition.findByPrimarykey(" + primarykey + ", " + composer.getPrimaryKey() + ")");
		}

		CompositionModel model = null;
		Composition composition = null;
		CompositionDAO dao = null;
		try	{
			dao = (CompositionDAO) DAOFactory.getDAO(className);
			model = (CompositionModel) dao.dbSelectByPrimaryKey(primarykey);
			if (composer == null)	{
				composition = new Composition(model);
			} else	{
				composition = new Composition(model, composer);
			}
		} catch (Exception ex) {
			if (isDebugging()) {
				System.out.println("Composition.findByPrimarykey(" + primarykey
						  + ", " + composer.getPrimaryKey() + ")"
						  + "\n\t" + ex.toString());
				ex.printStackTrace(System.out);
			}
			throw new FinderException(ex.getMessage());
		}

		return composition;
	}

	/**
	 * Find all composition entities for a particular composer.
	 * @return	A collection of composition instances.
	 * @throws	FinderException
	 * @throws	CreateException
	 */
	public static Collection<Composition> findByComposer(Composer composer) throws FinderException {
		if (isDebugging()) {
			System.out.println("--------------------"
			+ "Composition.findByCustomer <" + composer.getPrimaryKey() + ">");
		}

		ArrayList<Composition> listOfCompositions = new ArrayList<Composition>();
		CompositionDAO dao = null;
	
		try	{
			dao = (CompositionDAO) DAOFactory.getDAO(className);
			Collection<CompositionPK> compositions = dao.dbSelectByComposer(composer.getPrimaryKey());

			Iterator<CompositionPK> itr = compositions.iterator();
			if (isDebugging()) System.out.println("Compositions for Customer <" + composer.getPrimaryKey() + ">");

			while (itr.hasNext())	{
				CompositionPK cpk = itr.next();
				CompositionPK compositionpk = new CompositionPK(cpk.getComposerName(), cpk.getCompositionName());
				if (isDebugging())System.out.println("Composition.findByCustomer(" + composer.getPrimaryKey() + "):"
											+ "processing list-composition:" + compositionpk);
	
				try	{
					listOfCompositions.add(Composition.findByPrimarykey(compositionpk));
				} catch (Exception ex)	{
					System.out.println("Composition: Error processing list <" + ex.toString() + ">");
					ex.printStackTrace(System.out);
				}

				if (isDebugging()) System.out.println("Composition.findByCustomer(" + composer.getPrimaryKey() + "):"
											+ "Finished for composition:" + compositionpk);

			}

		} catch (Exception ex)	{
			ex.printStackTrace(System.out);
			throw new FinderException(ex.getMessage());
		}

		if (isDebugging()) {
			System.out.println("--------------------"
			+ "Composition.findByCustomer complete <" + composer.getPrimaryKey() + ">");
		}

		return listOfCompositions;
	}

	
	/**
	 *	Find all composition entities.
	 *	@return	A collection of composition instances.
	 *	@throws	FinderException
	 * @throws	CreateException
	 */
	public static Collection<Composition> findAll() throws FinderException, CreateException	{
		ArrayList<Composition> listOfCompositions = new ArrayList<Composition>();
		CompositionDAO dao = null;
	
		try	{
			dao = (CompositionDAO) DAOFactory.getDAO(className);
			Collection<CompositionPK> c = dao.dbSelectAll();
			Iterator<CompositionPK> itr = c.iterator();
			
			while (itr.hasNext()){
				
				CompositionPK compositionPK = itr.next();
				
				try	{
					listOfCompositions.add(Composition.findByPrimarykey(compositionPK));
				} catch (Exception ex)	{
					System.err.println("Composition: Error processing list <" + ex.toString());
				}
			}

		} catch (Exception sqlex)	{
			throw new FinderException(sqlex.getMessage());
		}

		return listOfCompositions;
	}

	/* REMOVERS	-----------------------------------------------------	*/
	/**
	 *	Remove a composition entity by its primary key.
	 *	@param	primarykey	The primary key for the entity.
	 *	@throws	NoSuchEntityException
	 */
	public static int removeByPrimarykey(CompositionPK primarykey) throws NoSuchEntityException, DAOSysException, SQLException {
//		System.out.println("\tB: removeBypk():" + primarykey);
		CompositionDAO dao = (CompositionDAO) DAOFactory.getDAO(className);
		CompositionModel m = (CompositionModel) dao.dbSelectByPrimaryKey(primarykey);
		Composition composition = new Composition(m);
		composition.getComposer().removeComposition(composition);
//		System.out.println("\t\tRemoved from customer:" + b);

		return dao.dbRemove(primarykey);
	}

	
	/* CONSTRUCTORS	-----------------------------------------------	*/
	/**
	 * Parameterized constructor.
	 * 
	 * @param composer
	 * @param compositionName
	 */
   public Composition(Composer composer, String compositionName) {
		this(new CompositionModel(composer.getName(), compositionName));
   }

	/**
	 * Parameterized constructor.
	 * @param model 
	 */
   public Composition(CompositionModel model) 	{
		if (isDebugging()) {
			System.out.println("Composition(" + model.getCompositionName() + ")");
		}
		setModel(model);

		/*	association between composition and composer done here	*/
		assignToComposer(model.getComposerPrimarykey());
   }
	
	
	/**
	 * Parameterized constructor.
	 * @param model
	 * @param composer	 * 
	 */
   public Composition(CompositionModel model, Composer composer) {
		if (isDebugging()) {
			System.out.println("Composition(" + model.getCompositionName() + ", " + composer.getName() + ")");
		}
		setModel(model);

		/*	association between composition and composer done here	*/
		assignToComposer(composer);
   }


	/* ACCESSORS	--------------------------------------------------	*/
	public CompositionModel getModel() { 
		return model;
	}
	
	public CompositionPK getPrimaryKey() { 
		return getModel().getPrimarykey();
	}
	
	public Composer getComposer() { 
		return composer;
	}
	
	public String getCompositionName() { 
		return compositionName;
	}
	
	public ArrayList<Movement> getListOfMovements(){
		return listOfMovements;
	}
	
	private ArrayList<Movement> getMovements(){
		ArrayList<Movement> list = new ArrayList<Movement>();
		try	{
			list = (ArrayList<Movement>) Movement.findByComposition(this);
		} catch (Exception ex)	{
		}
		return list;
	}

	/* MODIFIERS	--------------------------------------------------	*/
	public void setModel(CompositionModel model) { 
		this.model = model;
	}

	protected void setComposer(Composer composer)			{
		this.composer = composer;
		getModel().setComposerPrimarykey(composer.getPrimaryKey());
//		update();
	}

	protected void setCompositionName(String compositionName)							{
		this.compositionName = compositionName;
//		if (compositionName != null) {
//			getModel().setCompositionPrimarykey(compositionName.getPrimarykey());
//		}
//		update();
	}

	
	/* BEHAVIOR	-----------------------------------------------------	*/
	/**
	 *	Custom method to assign a Boat to a Customer
	 *	@param	composer	The customer that owns this composition.
	 */
	public void assignToComposer(ComposerPK pk)		{ 
		if (isDebugging()) {
			System.out.println("Composition:assignToComposer(" + pk + ")");
		}
		try	{
			Composer composer = Composer.findByPrimarykey(pk);
			assignToComposer(composer);
		} catch (Exception ex)	{
			ex.printStackTrace();
		}
	}
	
	/**
	 *	Custom method to assign a Boat to a Customer
	 *	@param	composer	The customer that owns this composition.
	 */
	public void assignToComposer(Composer composer)		{ 
		if (isDebugging()) {
			System.out.println("Composition:assignToComposer:" + composer.getName() + " Composition:" + getCompositionName());
		}
		setComposer(composer);
		composer.addComposition(this);
		update();
	}
	
	public void addMovement(Movement movement)	{
		if (!getListOfMovements().contains(movement))	{
			getListOfMovements().add(movement);
		}
	}

	/**
	 *	Remove a boat from this customer
	 * 
	 * @param boat 
	 */
	public void removeMovement(Movement movement)	{
		getMovements().remove(movement);
	}

	/**
	 *	Implemenation of the "object" equals method.  Boat objects are equal
	 *	if their primary key's are equal.
	 *	@return	True if the fields of this primary key object equal the
	 *	contents of the fields from the passed primary key object, otherwise
	 *	false, they are not equal.
	 */
	public boolean equals(Object obj)	{
		return	obj instanceof Composition
			&&	(getCompositionName().equals(((Composition) obj).getCompositionName())
			);
	}

	/**
	 *	Implementation of the "object"hashCode()" method.
	 * Whenever it is invoked on the same object more than once during
	 * an execution of a Java application, the hashCode method
	 * must consistently return the same integer, provided no information
	 * used in equals comparisons on the object is modified.
	 *	@return	A hash code value for the object.
	 */
	public int hashCode() {
		return	getCompositionName().concat(composer.getName()).hashCode();
	}
	
	/**
	 *	Flush cached attribute values to the datastore.
	 *	Catch and report any errors.
	 */
	public void update()	{
		if (isDebugging()) {
			System.out.println("Composition:update()");
		}
		try	{
			store();
		} catch (Exception ex)	{
			System.out.println("Composition: Error in update(), <" + ex.toString() + ">");
		}
	}
	
	/**
	 *	Remove a composition by primary key.
	 *	@param	primarykey	The primary key for the customer to find.
	 *	@return	The composition object removed, else null if not found.
	 *	@throws	ObjectNotFoundException
	 */
	public Composition remove()	throws NoSuchEntityException, DAOSysException, SQLException	{
		if (isDebugging()) System.out.println("Composition:remove()");
		getComposer().removeComposition(this);
		Composition composition = null;
		if (removeByPrimarykey(getPrimaryKey()) > 0)	{
			composition = this;
		}

		return composition;
	}
	
	/**
	 *	Convert this composition object to a meaningful string.
	 *	@return	This object as a string.
	 */
	public String toString()		{
		return this.toString(getCompositionName());
	}

	/**
	 *	Convert this composition object to a meaningful string.
	 *	@return	This object as a string.
	 */
	public String toString(String sep)		{
		return "composer=" + getComposer().getName()
			+ sep + "composition name=" + getCompositionName();
	}
	
	/* HELPERS	-----------------------------------------------------	*/
	/**
	 * Invoke this method to refresh the cached attribute values
	 * from the database.
	 */
	private void load() throws DAOSysException		{
		if (isDebugging()) {
			System.out.println("Composition:load()");
		}
		CompositionDAO dao = null;
		try	{
			dao = (CompositionDAO) DAOFactory.getDAO(className);
			setModel((CompositionModel)dao.dbLoad(getPrimaryKey()));

		} catch (Exception ex)	{
			throw new DAOSysException(ex.getMessage());
		}
	}

	/**
	 * Invoke this method to save the cached attribute values to the datastore.
	 */
	private void store() throws DAOSysException {
		if (isDebugging())	{
			System.out.println("Composition:store():" + toString());	
		}

		CompositionDAO dao = null;
		try	{
			dao = (CompositionDAO) DAOFactory.getDAO(className);
			dao.dbStore(getModel());

		} catch (Exception ex)	{
			throw new DAOSysException(ex.getMessage());
		}
	}
	
	public static boolean isDebugging()	{ 
		return _debug;			
	}
	
	/* ATTRIBUTES	--------------------------------------------------	*/
	private static final boolean _debug = true;
	
	/** Persistence model for a composition object.									*/
	private CompositionModel model;

	/** Class name for static method purposes.								*/
	private static String className = "symphony.Composition";
	
	/** The name for this composition */
	private String compositionName;

	/* REFERENCE ATTRIBUTES	-----------------------------------------	*/
	/** Reference to the customer object for this composition.					*/
	private Composer composer;
	
	private ArrayList<Movement> listOfMovements;

}	/*	End of CLASS:	Composition.java			*/
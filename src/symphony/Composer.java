/*
 *  @(#)Composer.java
 *
 *
 */

package symphony;

import sql.*;

import java.util.*;

import sql.CreateException;
import sql.FinderException;
import sql.NoSuchEntityException;

/**
 * Composer class
 * Demonstrates the use of the provided DAO framework.
 * includes basic composer attributes plus
 * Composition reference attribute and accessors
 */
public class Composer		{
	/* STATIC PRE-OBJECT BEHAVIOR	-----------------------------------	*/
	/*	Basic Creators, finders, and removers									*/
	/* CREATORS	-----------------------------------------------------	*/
	/**
	 *	Create an instance of a new composer.
	 *	@return	An instance of a composer entity.
	 *	@throws sql.CreateException 
	 * 	@param	number	The composer number.
	 *	@param	name	The name of the composer.
	 */
	public static Composer create(String name) throws CreateException {
		if (_debug) {
			System.out.println("C:create:" + name);
		}

		ComposerModel model = new ComposerModel(name);
		ComposerDAO dao = null;
		try	{
			dao = (ComposerDAO) DAOFactory.getDAO(className);
			dao.dbInsert(model);
			
			/* Initially this composer has no compositions */

		} catch (Exception sqlex)	{
			throw new CreateException(sqlex.getMessage());
		}

		return	new Composer(model);
	}
	
	/* FINDERS	-----------------------------------------------------	*/
	/* Finder methods are used to search for a particular instance
	 * or a collection of instances, therefore finders either return
	 * and instance to the entity, or a collection of instances.
	 */
	/**
	 * Find a composer by primary key.
	 * @return	An instance of a composer entity.
	 * @throws  sql.FinderException 
	 * @throws  sql.NoSuchEntityException 
	 * @param	primarykey	The primary key for the composer to find.
	 */
	public static Composer findByPrimarykey(ComposerPK primarykey) throws FinderException, NoSuchEntityException {
		if (_debug) {
			System.out.println("C:findByPrimarykey(" + primarykey + ")");
		}

		ComposerModel model = null;
		Composer composer = null;
		ComposerDAO dao = null;
		
		try	{
			dao = (ComposerDAO) DAOFactory.getDAO(className);
			model = (ComposerModel) dao.dbSelectByPrimaryKey(primarykey);
			composer = new Composer(model);

			/* Add composition references for this composer. */
			composer.setListOfCompositions( ((ArrayList<Composition>) Composition.findByComposer(composer)) );

	
		} catch (Exception sqlex)	{
			throw new FinderException(sqlex.getMessage());
		}

		return composer;
	}

	/**
	 * Find all composer entities.
	 * @return	A collection of composer instances.
	 * @throws	FinderException
	 * @throws	CreateException
	 */
	public static Collection<Composer> findAll() throws FinderException, CreateException {
		ArrayList<Composer> listOfComposers = new ArrayList<Composer>();
		ComposerDAO dao = null;
	
		try	{
			dao = (ComposerDAO) DAOFactory.getDAO(className);
			Collection<ComposerPK> c = dao.dbSelectAll();
			Iterator<ComposerPK> itr = c.iterator();
			while (itr.hasNext())	{
				ComposerPK cpk = itr.next();
				try	{
					Composer composer = Composer.findByPrimarykey(cpk);

					/* Add composition references for this composer. */
					composer.setListOfCompositions(((ArrayList<Composition>) Composition.findByComposer(composer)));
					
					/* Add this composer to the list.						*/
					listOfComposers.add(composer);

				} catch (Exception ex)	{
					System.err.println("Composer: Error processing list <" + ex.toString());
				}
			}

		} catch (Exception sqlex)	{
			throw new CreateException(sqlex.getMessage());
		}
		
		return listOfComposers;
	}
	
	
	/* REMOVERS	-----------------------------------------------------	*/
	/**
	 * Remove a composer by primary key.
	 * @param	primarykey	The primary key for the composer to find.
	 * @throws	NoSuchEntiryException
	 * @throws	DAOSysException
	 */
	private static int removeByPrimarykey(ComposerPK primarykey) throws	DAOSysException, NoSuchEntityException {
		int rc = 0;
		ComposerDAO dao = null;
		
		/*	remove compositions first */
		
		dao = (ComposerDAO) DAOFactory.getDAO(className);
		rc = dao.dbRemove(primarykey);
		
		return rc;
	}

	
	/* CONSTRUCTORS	-----------------------------------------------	*/
	/**
	 *	Default constructor
	 */
	private Composer() { 
		super();		
	}

	/**
	 * Parameterized constructor.
	 * 
	 * @param number
	 * @param name
	 */
	private Composer(String number, String name) {
		this(new ComposerModel(name));
	}

	/**
	 *	Parameterized constructor.
	 *
	 *	@param	model	The persistence model for a composer object.
	 */
	private Composer(ComposerModel model)	{
		setModel(model);

		/*	initially no compositions, but we do have empty collections	*/
		setListOfCompositions(new ArrayList<Composition>());
	}


	/* ACCESSORS	--------------------------------------------------	*/
	public ComposerModel getModel() { 
		return model;
	}
	
	public ComposerPK getPrimaryKey() { 
		return getModel().getPrimarykey();
	}
	
	public String getNumber() { 
		return getModel().getPrimarykey().getName(); 	
	}
 	
	public String getName() { 
		return getModel().getName();
	}
	
	public ArrayList<Composition> getListOfCompositions() { 
		return listOfCompositions;
	}

	private ArrayList<Composition> getCompositions()	{
		ArrayList<Composition> list = new ArrayList<Composition>();
		try	{
			list = (ArrayList<Composition>) Composition.findByComposer(this);
		} catch (Exception ex)	{
			ex.printStackTrace();
		}
		return list;
	}
	
	/* MODIFIERS	--------------------------------------------------	*/
	private void setModel(ComposerModel model) { 
		this.model = model;
	}

	private void setPrimarykey(ComposerPK pk) { 
		getModel().setPrimarykey(pk);
	}
	
	public void setName(String name) {
		getModel().setName(name);
		update();
	}

	private void setListOfCompositions(ArrayList<Composition> compositions) { 
		listOfCompositions = compositions;
	}


	/* BEHAVIOR	-----------------------------------------------------	*/
	/**
	 * Implementation of the "object" equals method.  
	 * Composer objects are equal if their primary key's are equal.
	 * 
	 * @return	True if the fields of this primary key object equal the
	 * 			contents of the fields from the passed primary key object, 
	 * 			otherwise false, they are not equal.
	 */
	public boolean equals(Object obj) {
		return	obj instanceof Composer
			&&	(getNumber().equals(((Composer) obj).getNumber())
			);
	}

	/**
	 * Implementation of the "object"hashCode()" method.
	 * Whenever it is invoked on the same object more than once during
	 * an execution of a Java application, the hashCode method
	 * must consistently return the same integer, provided no information
	 * used in equals comparisons on the object is modified.
	 * 
	 * @return	A hash code value for the object.
	 */
	public int hashCode() {
		return	getNumber().concat(getName()).hashCode();
	}

	/**
	 *	Flush cached attribute values to the datastore.
	 *	Catch and report any errors.
	 */
	public void update()	{
		if (_debug) {
			System.out.println("C:update()");
		}
		try	{
			store();
		} catch (Exception ex)	{
			System.out.println("C: Error in update(), <" + ex.toString() + ">");
		}
	}

	public String toString() { 
		return this.toString(", ");
	}
	
	public String toString(String sep)	{
		return "number=" + getNumber()
				+ sep + "name=" + getName()
				+ sep + "compositions=" + getCompositions();
	}

	/**
	 *	Get an iterator to the list of compositions for this composer.
	 * 
	 * @return 
	 */
	public Iterator<Composition> compositions() { 
		return getCompositions().iterator();
	}

	/**
	 *	Add a composition to this composer.
	 * 
	 * @param composition 
	 */
	public void addComposition(Composition composition)	{
		if (!getListOfCompositions().contains(composition))	{
			getListOfCompositions().add(composition);
		}
	}

	/**
	 *	Remove a composition from this composer
	 * 
	 * @param composition 
	 */
	public void removeComposition(Composition composition)	{
		getCompositions().remove(composition);
	}	

	/**
	 * Remove a composer from the data store (by primary key).
	 * 
	 * @return 
	 * @throws sql.NoSuchEntityException
	 * @throws sql.DAOSysException 
	 */
	public Composer remove() throws NoSuchEntityException, DAOSysException {
		Composer c = null;
		if (removeByPrimarykey(getPrimaryKey()) > 0)	{
			c = this;
		}

		return c;
	}

	/**
	 * Invoke this method to refresh the cached attribute values
	 * from the database.
	 */
	private void load() throws DAOSysException		{
		if (_debug) {
			System.out.println("C:load()");
		}
		ComposerDAO dao = null;
		try	{
			dao = (ComposerDAO) DAOFactory.getDAO(className);
			setModel((ComposerModel)dao.dbLoad(getPrimaryKey()));

		} catch (Exception ex)	{
			throw new DAOSysException(ex.getMessage());
		}
	}


	/**
	 * Invoke this method to save the cached attribute values to the datastore.
	 */
	private void store()	throws DAOSysException		{
		if (_debug) {
			System.out.println("C:store()");
		}
		ComposerDAO dao = null;
		try	{
			dao = (ComposerDAO) DAOFactory.getDAO(className);
			dao.dbStore(getModel());
		} catch (Exception ex)	{
			throw new DAOSysException(ex.getMessage());
		}
	}

	/* ATTRIBUTES	--------------------------------------------------	*/
	private static final boolean _debug = false;

	/** Class name for static method purposes.								*/
	private static String className = "symphony.Composer";
	
	/** Persistence model for a composer object.								*/
	private ComposerModel model;

	
	/* REFERENCE ATTRIBUTES	-----------------------------------------	*/
 	/** Compositions for this composer.	*/
	private ArrayList<Composition> listOfCompositions;

}	/*	End of CLASS:	Composer.java				*/
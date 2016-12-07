/*
 *  @(#)CorePersistenceModel.java
 *
 */

package	sql;

/**
 * CoreModel is a class that encapsulates the persistent data structure
 * of an object for communication between the CoreObject and the CoreDAO object.
 *	Although its contents is sparse represents a useful design for implementation
 * with most persistent objects.
 * @param PK Primary key class
 * @author    R. Dyer
 */
public class CorePersistenceModel<PK> {
	/**
	 *	Default constructor.
	 */
	public CorePersistenceModel()	{}

	/**
	 *	Parameterized constructor.
	 *	@param	primarykey	The primary key value (obect) for this object.
	 */
	public CorePersistenceModel(PK primarykey)	{ setPrimarykey(primarykey);	}

	/**
	 *	Convert the primary key to a string.
	 *	@return	Primary key as a string.
	 */
	public String toString()	{ return	getPrimarykey().toString();		}

	public PK getPrimarykey()	{ return primarykey;		}
	public void setPrimarykey(PK primarykey)	{ this.primarykey = primarykey;		}


	/*	Core ATTRIBUTES ----------------------------------------------	*/
	/** The primary key.																*/
	private PK primarykey;

}	/*	End of Class:	CorePersistenceModel.java						*/
/*
 *  @(#)ComposerPK.java
 */

package symphony;

/**
 * ComposerPK is the primary key class for a Composer entity.
 * 
 * @author    Victoria Sawyer
 */
public class ComposerPK implements java.io.Serializable	{
	/**
	 *	Default constructor.
	 */
	public ComposerPK()	{}

	/**
	 *	Constructor to build a primary key from an Number.
	 *
	 *	@param	name	The composer number.
	 */
	public ComposerPK(String name) { 
		this.name = name;
	}

	/**
	 *	Constructor to build a primary key from a another ComposerPK argument.
	 *
	 *	@param	primarykey	A ComposerPK object.
	 */
	public ComposerPK(ComposerPK primarykey) { 
		name = primarykey.getName();
	}

	/* ACCESSORS	--------------------------------------------------	*/
	/**
	 *	Get the composer name.
	 *
	 *	@return	The composer name.
	 */
	public String getName() { 
		return name;		
	}

	/* BEHAVIOR	-----------------------------------------------------	*/
	/**
	 *	Convert this primary key object into a meaningful string.
	 *
	 *	@return	This object as a string.
	 */
	public String toString() { 
		return	name;		
	}

	/**
	 *	Implemenation of the "object" equals method.
	 *
	 *	@return	True if the fields of this primary key object equal the
	 *	contents of the fields from the passed primary key object, otherwise
	 *	false, they are not equal.
	 */
	public boolean equals(Object obj)	{
		return	obj instanceof ComposerPK
			&&	getName().equals(((ComposerPK) obj).getName()
					);
	}

	/**
	 * Implementation of the "object"hashCode()" method.
	 * Whenever it is invoked on the same object more than once during
	 * an execution of a Java application, the hashCode method
	 * must consistently return the same integer, provided no information
	 * used in equals comparisons on the object is modified.
	 * 
	 *	@return	A hash code value for the object.
	 */
	public int hashCode() {
		return	getName().hashCode();
	}

	/*	Composer Entity PRIMARY KEY FIELDS ------------------------------	*/
	/** Composer name. */
	private String name;

}	/*	End of Class:	ComposerPK.java				*/
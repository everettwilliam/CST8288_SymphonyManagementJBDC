/*
 *  @(#)CompositionPK.java
 */

package symphony;

import sql.FinderException;
import sql.NoSuchEntityException;
/**
 * CompositionPK is the primary key class for a Composition entity.
 * @author    R. Dyer
 */
public class CompositionPK implements java.io.Serializable	{
	/**
	 *	Default constructor.
	 */
	public CompositionPK() {}

	/**
	 *	Constructor to build a primary key from an RegistrationNo.
	 *	@param	compositionName	The customer registrationNo.
	 */
	public CompositionPK(String composerName, String compositionName)	{ 
		this.compositionName = compositionName;	
		this.composerName = composerName;
		
	}

	/**
	 *	Constructor to build a primary key from a another CompositionPK argument.
	 *	@param	primarykey	A CompositionPK object.
	 */
	public CompositionPK(CompositionPK primarykey)	{ 
		compositionName = primarykey.getCompositionName();		
	}


	/* ACCESSORS	--------------------------------------------------	*/
	/**
	 * Get the composition name.
	 * @return composition name
	 */
	public String getCompositionName()	{ 
		return compositionName;		
	}
	/**
	 * Get the composer name.
	 * @return composer name
	 */
	public String getComposerName()	{ 
		return composerName;		
	}


	/* BEHAVIOR	-----------------------------------------------------	*/
	/**
	 *	Convert this primary key object into a meaningful string.
	 *	@return	This object as a string.
	 */
	public String toString() { 
		return	compositionName;	
	}


	/**
	 *	Implemenation of the "object" equals method.
	 *	@return	True if the fields of this primary key object equal the
	 *	contents of the fields from the passed primary key object, otherwise
	 *	false, they are not equal.
	 */
	public boolean equals(Object obj)	{
		return	obj instanceof CompositionPK
			&&	getCompositionName().equals(((CompositionPK) obj).getCompositionName());
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
		return	getCompositionName().hashCode();
	}



	/*	Composition Entity PRIMARY KEY FIELDS ------------------------------	*/
	/** Composition name.																	*/
	private String compositionName;
	
	private Composer composer;
	
	private String composerName;

}	/*	End of Class:	CompositionPK.java				*/
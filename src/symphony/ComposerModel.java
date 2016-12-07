/*
 * ComposerModel.java
 */

package symphony;

import sql.CorePersistenceModel;

/**
 * ComposerModel represents the persistence model for a composer object.
 * @author Victoria Sawyer
 */
public class ComposerModel extends CorePersistenceModel<ComposerPK>	{
	/**
	 * Creates a new instance of ComposerModel
	 */
	public ComposerModel() { 
		super();
	}
	
	/**
	 * Creates a new instance of ComposerModel
	 * 
	 * @param number
	 */
	public ComposerModel(String name) {
		this(new ComposerPK(name));
	}

	/**
	 * Creates a new instance of ComposerModel
	 * 
	 * @param primarykey
	 */
	public ComposerModel(ComposerPK primarykey) {
		super();
		setPrimarykey(primarykey);
	}
	
	
	/* ACCESSORS	--------------------------------------------------	*/	
 	public String getName() { 
 		return name;
 	}

	/* MODIFIERS	--------------------------------------------------	*/
	public void setName(String name) {
		this.name = name;					
	}
	
	/* ATTRIBUTES	--------------------------------------------------	*/
	/** Name of this composer. */
 	private String name;

}

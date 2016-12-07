/*
 * CompositionModel.java
 */

package symphony;

import sql.CorePersistenceModel;
import sql.FinderException;
import sql.NoSuchEntityException;

/**
 * CompositionModel represents the persistence model for a composer object.
 *	The attributes defined in this class will be persisted to the
 *	data store.
 * @author Reg
 */
public class CompositionModel extends CorePersistenceModel<CompositionPK> {
	/**
	 * Creates a new instance of CompositionModel
	 */
	public CompositionModel() { 
		super();
	}

	/**
	 * Parameterized constructor.
	 * @param composerName
	 * @param compositionName
	 */
	public CompositionModel(String composerName, String compositionName) {
		this(new CompositionPK(composerName, compositionName));
	}

	/**
	 * Creates a new instance of CompositionModel
	 * @param primarykey
	 */
	public CompositionModel(CompositionPK primarykey) {
		super(primarykey);
		//TODO
//		setComposerPrimarykey(composerpk);
	}
	
	
	/* ACCESSORS	--------------------------------------------------	*/
	public CompositionPK getPrimarykey() { 
		return (CompositionPK) super.getPrimarykey();
	}
	
	public ComposerPK getComposerPrimarykey() { 
		return composerPrimarykey;
	}
	
	public String getCompositionName() {
		return getPrimarykey().getCompositionName();
	}

	/* MODIFIERS	--------------------------------------------------	*/
	public void setPrimarykey(CompositionPK pk) { 
		super.setPrimarykey(pk);					
	}

	public void setComposerPrimarykey(ComposerPK pk) { 
		composerPrimarykey = pk;  				
	}
	
	/* ATTRIBUTES	--------------------------------------------------	*/

	/* REFERENCE ATTRIBUTES	-----------------------------------------	*/
	/** The primary key for a composer											*/
	private ComposerPK composerPrimarykey;

}	/*	End of CLASS:	CompositionModel.java				*/

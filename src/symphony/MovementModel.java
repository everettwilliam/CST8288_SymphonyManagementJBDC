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

import sql.CorePersistenceModel;

/**
 * Description of this class.
 * @author    EVERETT HOLDEN
 * @version   1.0.0 2016.12.01
 */
public class MovementModel extends CorePersistenceModel<MovementPK>	{

	/* CONSTRUCTORS	-----------------------------------------------------	*/
	
	public MovementModel(){
		super();
	}
	
	public MovementModel(Integer movementNumber, String movementName, CompositionPK compositionPK){
		this(new MovementPK(movementNumber,movementName), compositionPK);
	}
	
	public MovementModel(MovementPK primarykey, CompositionPK compositionPk){
		setMovementNumber(primarykey.getMovementNumber());
		setMovementName(primarykey.getMovementName());
		setCompositionPrimaryKey(compositionPk);		
	}
	


	/* ACCESSORS	-----------------------------------------------------	*/
	public Integer getMovementNumber(){
		return movementNumber;
	}
	
	public String getMovementName(){
		return movementName;
	}
	
	public CompositionPK getCompositionPrimaryKey(){
		return compositionPk;
	}


	/* MODIFIERS	-----------------------------------------------------	*/
	public void setMovementNumber(Integer movementNumber){
		this.movementNumber = movementNumber;
	}
	
	public void setMovementName(String movementName){
		this.movementName = movementName;
	}

	public void setCompositionPrimaryKey(CompositionPK compositionPk){
		this.compositionPk = compositionPk;
	}


	/* NORMAL BEHAVIOR --------------------------------------------------	*/
	



	/* HELPER METHODS	--------------------------------------------------	*/




	/* ATTRIBUTES	-----------------------------------------------------	*/											
	private Integer movementNumber;
	
	private String movementName;
	
	private CompositionPK compositionPk;


}	/*	End of CLASS:	MovementModel.java			*/
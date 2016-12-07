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

/**
 * Description of this class.
 * @author    EVERETT HOLDEN
 * @version   1.0.0 2016.12.01
 */
public class MovementPK	{

	/* CONSTRUCTORS	-----------------------------------------------------	*/
	public MovementPK(){
		
	}

	public MovementPK(Integer movementNumber, String movementName){
		
	}
	
	public MovementPK(MovementPK primarykey){
		this.movementName = primarykey.getMovementName();
		this.movementNumber = primarykey.getMovementNumber();
		
	}

	/* ACCESSORS	-----------------------------------------------------	*/
	public Integer getMovementNumber(){
		return movementNumber;
	}
	
	public String getMovementName(){
		return movementName;
	}


	/* MODIFIERS	-----------------------------------------------------	*/
	


	/* NORMAL BEHAVIOR --------------------------------------------------	*/
	



	/* HELPER METHODS	--------------------------------------------------	*/

	@Override
	public String toString(){
		return	movementName.toString() + movementNumber.toString(); 		
	}
	
	@Override
	public int hashCode() {
		return	getMovementName().toString().hashCode() 
			  + getMovementNumber().toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj)	{
		return	obj instanceof MovementPK
			&&	getMovementName().equals(((MovementPK) obj).getMovementName())
			&&  getMovementNumber().equals(((MovementPK) obj).getMovementNumber());
	}



	/* ATTRIBUTES	-----------------------------------------------------	*/											
	private Integer movementNumber;
	
	private String movementName;


}	/*	End of CLASS:	MovementPK.java			*/
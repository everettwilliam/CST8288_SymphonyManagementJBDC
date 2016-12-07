/*
 * CreateException.java
 *
 */

package sql;

/**
 *
 * @author Reg
 */
public class CreateException extends java.sql.SQLException		{
	/** Creates a new instance of CreateException */
	public CreateException()				{ super(DEFAULT_MESSAGE);			}
	public CreateException(String msg)	{ super(msg);							}

	private final static String DEFAULT_MESSAGE = "Unable to Create Object";
}

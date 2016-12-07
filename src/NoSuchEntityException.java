/*
 * NoSuchEntityException.java
 */

package sql;

/**
 *
 * @author Reg
 */
public class NoSuchEntityException extends Exception	{
	public NoSuchEntityException()					{ super(DEFAULT_MESSAGE);			}
	public NoSuchEntityException(String msg)		{ super(msg);							}

	private final static String DEFAULT_MESSAGE = "No Such Entity";
}

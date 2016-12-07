//package symphony;
/*
 * @(#)IndyListSV.java
 *
 * Copyright (c) 1998 Karl Moss. All Rights Reserved.
 * You may study, use, modify, and distribute this software for any
 * purpose provided that this copyright notice appears in all copies.
 * This software is provided WITHOUT WARRANTY either expressed or
 * implied.
 */
import symphony.ComposerDAO;
import symphony.ComposerPK;
import symphony.Composition;
import symphony.CompositionDAO;
import symphony.CompositionPK;
import symphony.MovementDAO;
import symphony.MovementPK;

import javax.servlet.*;
import javax.servlet.http.*;

import sql.DAOSysException;
import sql.FinderException;
import sql.NoSuchEntityException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

/**
 * <p>This is a simple servlet that will use JDBC to gather all
 * of the Indy 500 winner  information from a database and format it
 * into an HTML table. This servlet uses HttpSessions to keep
 * track of the position within the ResultSet so that the
 * table can be split into several different pages, each with
 * a 'Next n rows' link.
 */
public class SymphonyComposition extends HttpServlet	{
	
	/*public void init(ServletConfig cfg) throws ServletException {
		super.init(cfg);
	}*/

	public void destroy()		{
		super.destroy();
	}
	
	public Collection<ComposerPK> getComposersPK() throws DAOSysException {
		return composerDao.dbSelectAll();
	}
	
	public Collection<CompositionPK> getCompositionsPK() throws DAOSysException {
		return compositionDao.dbSelectAll();
	}
	
	public Collection<MovementPK> getMovementsByComposition(CompositionPK compositionPk) throws DAOSysException, NoSuchEntityException {
		return movementDao.dbSelectByComposition(compositionPk);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = new PrintWriter(resp.getOutputStream());
		
		Collection<ComposerPK> composerPkList = null;
		Collection<CompositionPK> compositionPkList = null;
		Collection<MovementPK> movementPkList = null;
		
		try {
			composerPkList = getComposersPK();
		} catch (DAOSysException e) {
			e.printStackTrace();
		}
		
		try {
			compositionPkList = getCompositionsPK();
		} catch (DAOSysException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Compositions</title>");
		out.println("</head>");
		out.println("<body>");
		
		/*Iterator<ComposerPK> iterator = composerPkList.iterator();
		while (iterator.hasNext()) {
			out.println("<p>");
			out.println(iterator.next().getName());
			out.println("</p>");
		}*/
		
		Iterator<CompositionPK> compositionIter = compositionPkList.iterator();
		while (compositionIter.hasNext()) {
			CompositionPK composition = compositionIter.next();
			out.println("<h2>");
			out.println(composition.getComposerName());
			out.println("</h2>");
			out.println("<p>");
			out.println(composition.getCompositionName());

			try {
				movementPkList = getMovementsByComposition(composition);
				Iterator<MovementPK> movementIter = movementPkList.iterator();
				while (movementIter.hasNext()) {
					out.println("<br>");
					out.println(movementIter.next().getMovementName());
				}
			} catch (DAOSysException e) {
				out.println("Error: DAOSysException");
				e.printStackTrace();
			} catch (NoSuchEntityException e) {
				out.println("Error: NoSuchEntityException");
				e.printStackTrace();
			}
			
			out.println("</p>");
			
		}
		
		
		
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}
	
	ComposerDAO composerDao = new ComposerDAO();
	CompositionDAO compositionDao = new CompositionDAO();
	MovementDAO movementDao = new MovementDAO();

	/**
	 * <p>Initialize the servlet. This is called once when the
	 * servlet is loaded. It is guaranteed to complete before any
	 * requests are made to the servlet
	 * @param cfg Servlet configuration information
	 */
	/*public void init(ServletConfig cfg)
			throws ServletException		{
		super.init(cfg);
	}*/

	/**
	* <p>Destroy the servlet. This is called once when the servlet
	* is unloaded.
	*/
	/*public void destroy()		{
		super.destroy();
	}*/

	/**
	 * <p>Performs the HTTP POST operation
	 * @param req The request from the client
	 * @param resp The response from the servlet
	 */
	/*public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, java.io.IOException		{
		//	Same as get																	 
		doGet(req, resp);
	}*/

	/**
	 * <p>Performs the HTTP GET operation
	 * @param req The request from the client
	 * @param resp The response from the servlet
	 */
	/*public void doGet(HttpServletRequest req, HttpServletResponse resp)
		 throws ServletException, java.io.IOException	{
	 	//	Get the last year shown on the page that
	 	//	called us. Remember that we are sorting
	 	//	the years in descending order.
	 	  
		String lastComposition = req.getParameter("lastComposition");
		int numComp = 0;
		
		if (lastComposition == null) {
			lastComposition = "0";
		}

		numComp = Integer.parseInt(lastComposition);

		//	Get the URI, Set the content type, and create a PrintWriter		 
		String uri = req.getRequestURI();
		resp.setContentType("text/html");
		java.io.PrintWriter out = new java.io.PrintWriter(resp.getOutputStream());

		//	Print the HTML header														 
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Composers</title>");
		out.println("</head>");
		out.println("<h2><center>");
		out.println("Composers");
		out.println("</center></h2>");
		out.println("<br>");

		//	Create any addition properties necessary for connecting
		//	to the database, such as user and password
		  

		try	{
			Collection<Composition> list = Composition.findAll();
			formatTable(list,  out,  uri);
			
		} catch (FinderException fe)	{
			fe.printStackTrace(out);
		} catch (Exception ex)	{
			ex.printStackTrace(out);
	}	
		//	Wrap up																		 
		out.println("</html>");
		out.flush();
		out.close();
	}*/


	/**
	 * <p>Given a list of Composers, format them into an HTML table
	 * @param list	Collection of Compositions
	 * @param out PrintWriter to use to output the table
	 * @param uri Requesting URI
	 * @return The number of rows in the ResultSet
	 */
	/*private int formatTable(Collection<Composition> list,java.io.PrintWriter out,String uri) throws Exception {

		int rowsPerPage = 10;
		int rowCount = 0;

		//	Keep track of the last year found												 
		String lastComposition = "";

		//	Create the table																		 
		out.println("<center><table border>");

		//	Start the table row																	 
		out.println("<tr>");

		//	Create each table header. Note that the column index is 1-based	 
		out.println("<th>" + "Composition Name" + "</th>");	
		out.println("<th>" + "Composition Number" + "</th>");
		out.println("</tr>");
		

	  for (Composition composition : list)	{
			// Start a table row																	
			out.println("<tr>");
			out.println("<td>" + composition.getCompositionName() + "</td>");
			out.println("<td>" + composition.getComposer() + "</td>");			
			out.println("</tr>");
	  
			lastComposition = composition.getCompositionName().toString();
			rowCount++;
	  }

		rowCount = rowCount < 10 ? 0 : rowCount;

		//	End the table																			
		out.println("</table></center>");

	//	if (more)	{
			//	Create a 'Next' button															
			out.println("<form method=POST action=\"" + uri + "\">");
			out.println("<center>");
			out.println("<input type=submit value=\"Next " + rowsPerPage + " rows\">");
			out.println("</center>");

			// Page was filled. Put in the last year that we saw						
			out.println("<input type=hidden name=lastComposition value=" + lastComposition + ">");
			out.println("</form>");
//		}

		return rowCount;
	}*/

}
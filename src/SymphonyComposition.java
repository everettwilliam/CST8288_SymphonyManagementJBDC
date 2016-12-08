<<<<<<< HEAD
package servlet;
=======
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
import symphony.Composer;
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
>>>>>>> origin/master

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

<<<<<<< HEAD
import sql.FinderException;
import symphony.Composition;

/**
 * Servlet implementation class HelloWorld
 */
@WebServlet("/SymphonyComposition")
public class SymphonyComposition extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Initialize the servlet. This is called once when the
	 * servlet is loaded. It is guaranteed to complete before any
	 * requests are made to the servlet
	 * @param cfg Servlet configuration information
	 */
	public void init(ServletConfig cfg) throws ServletException {
		super.init(cfg);
	}

	/**
	 * <p>Destroy the servlet. This is called once when the servlet
	 * is unloaded.
	 */
	public void destroy(){
		super.destroy();
	}

	/**
	 * <p>Performs the HTTP POST operation
	 * @param req The request from the client
	 * @param resp The response from the servlet
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {
		/*	Same as get																	*/
		doGet(req, resp);
	}

	/**
	 * <p>Performs the HTTP GET operation
	 * @param req The request from the client
	 * @param resp The response from the servlet
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {
		/*	Get the last year shown on the page that
		 *	called us. Remember that we are sorting
		 *	the years in descending order.
		 */
		String lastComposition = req.getParameter("lastComposition");
		int numComp = 0;

		if (lastComposition == null) {
			lastComposition = "0";
		}

		numComp = Integer.parseInt(lastComposition);

		/*	Get the URI, Set the content type, and create a PrintWriter		*/
		String uri = req.getRequestURI();
		resp.setContentType("text/html");
		java.io.PrintWriter out = new java.io.PrintWriter(resp.getOutputStream());

		/*	Print the HTML header														*/
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Symphony Compositions</title>");
		out.println("</head>");
		out.println("<h2><center>Symphony Compositions</center></h2>");		
		out.println("<br>");

		/*	Create any addition properties necessary for connecting
		 *	to the database, such as user and password
		 */

		try	{
			Collection<Composition> list = Composition.findAll();
			formatTable(list,  out,  uri);

		} catch (FinderException fe)	{
			fe.printStackTrace(out);
		} catch (Exception ex)	{
			ex.printStackTrace(out);
		}	
		/*	Wrap up																		*/
=======
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
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		String offsetStr = req.getParameter("offset");
		
		if (offsetStr == null) {
			offsetStr = "0";
		}
		
		offset = Integer.parseInt(offsetStr);
				
		resp.setContentType("text/html");
		PrintWriter out = new PrintWriter(resp.getOutputStream());
		
		/*Collection<ComposerPK> composerPkList = null;
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
		}*/
		
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Compositions</title>");
		out.println("</head>");
		out.println("<body>");
		
		/*Iterator<CompositionPK> compositionIter = compositionPkList.iterator();
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
			
		}*/
		
		Collection<ComposerPK> composerList = null;
		try {
			composerList = composerDao.dbSelectNextSet(rowsPerPage, offset);
			formatTable(composerList, out, uri);
		} catch (DAOSysException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.println("</body>");
>>>>>>> origin/master
		out.println("</html>");
		out.flush();
		out.close();
	}
<<<<<<< HEAD


	/**
	 * <p>Given a list of Composers, format them into an HTML table
	 * @param list	Collection of Compositions
	 * @param out PrintWriter to use to output the table
	 * @param uri Requesting URI
	 * @return The number of rows in the ResultSet
	 */
	private int formatTable(Collection<Composition> list, java.io.PrintWriter out, String uri) throws Exception {

		int rowsPerPage = 10;
		int rowCount = 0;

		/*	Keep track of the last year found												*/
		String lastComposition = "";
		String lastMovement = "";

		/*	Create the table																		*/
		out.println("<center>");
		for(Composition composition : list){
			out.println("<h2>Composer: " + composition.getComposer() + "</h2>");
		}		
		out.println("<table border>");

		/*	Start the table row																	*/
		out.println("<tr>");

		/*	Create each table header. Note that the column index is 1-based	*/
		out.println("<th>" + "Composition" + "</th>");	
		out.println("<th>" + "Movement" + "</th>");
		out.println("</tr>");


		for (Composition composition : list)	{
			/* Start a table row																	*/
			out.println("<tr>");
			out.println("<td>" + composition.getCompositionName() + "</td>");					
			out.println("</tr>");

			lastComposition = composition.getCompositionName().toString();
			rowCount++;
		}

		rowCount = rowCount < 10 ? 0 : rowCount;

		/*	End the table																			*/
		out.println("</table></center>");

		//	if (more)	{
		/*	Create a 'Next' button															*/
		out.println("<form method=POST action=\"" + uri + "\">");
		out.println("<center>");
		out.println("<input type=submit value=\"Next " + rowsPerPage + " rows\">");
		out.println("<input type=submit value=\"Next Composer\">");
		out.println("</center>");

		/* Page was filled. Put in the last year that we saw						*/
		out.println("<input type=hidden name=lastComposition value=" + lastComposition + ">");
		out.println("<input type=hidden name=lastMovement value=" + lastMovement + ">");
		out.println("</form>");
		//			}

		return rowCount;
	}
=======
	
	private int formatTable(Collection<ComposerPK> composerList, PrintWriter out, String uri) {
		int rowCount = 0;
		
		for (ComposerPK composerpk : composerList) {
			out.println("<p>");
			out.println(composerpk.getName());
			out.println("<p>");
			
			rowCount++;
			offset++;
		}
		
		offset = rowCount < rowsPerPage ? 0 : offset;

		out.println(rowCount);
		out.println(offset);
		
		out.println("<form method=POST action=\"" + uri + "\">");
		out.println("<center>");
		out.println("<input type=submit value=\"Next " + rowsPerPage + " rows\">");
		out.println("</center>");

		/* Page was filled. Put in the last year that we saw						*/
		out.println("<input type=hidden name=offset value=" + offset + ">");
		out.println("</form>");
		
		return rowCount;		
	}
	
	private int rowsPerPage = 10;
	private int offset;
	private ComposerDAO composerDao = new ComposerDAO();
	private CompositionDAO compositionDao = new CompositionDAO();
	private MovementDAO movementDao = new MovementDAO();
>>>>>>> origin/master

}

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
		out.println("</html>");
		out.flush();
		out.close();
	}
	
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

}
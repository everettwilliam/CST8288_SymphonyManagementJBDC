package servlet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		out.println("</html>");
		out.flush();
		out.close();
	}


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

}

package server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.ac.ncl.csc3422.kennelbooking.Kennel;
import uk.ac.ncl.csc3422.kennelbooking.KennelFactory;
import uk.ac.ncl.csc3422.kennelbooking.KennelReport;

/**
 *
 * @author Milton
 */
public class IndexServlet extends HttpServlet {

	private Kennel kennel = KennelFactory.initialiseKennel();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("username", request.getParameter("username"));
		
		String[] reportLines = getLines(KennelReport.generateReport(kennel));
		for (int i = 0; i < reportLines.length; i++) {
			request.setAttribute("report_line" + (i + 1), reportLines[i]);
		}
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
	
	private String[] getLines(String report) {
		String[] lines = new String[8];
		lines[0] = report.substring(0, 92);
		lines[1] = report.substring(93, 140);
		lines[2] = report.substring(141, 188);
		lines[3] = report.substring(189, 236);
		lines[4] = report.substring(237, 285);
		lines[5] = report.substring(286, 334);
		lines[6] = report.substring(335, 383);
		lines[7] = report.substring(384, 431);
		return lines;
	}
}
package server;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.ac.ncl.csc3422.kennelbooking.DogSize;
import uk.ac.ncl.csc3422.kennelbooking.Kennel;
import uk.ac.ncl.csc3422.kennelbooking.KennelFactory;
import uk.ac.ncl.csc3422.kennelbooking.KennelReport;

/**
 *
 * @author Milton
 */
public class IndexServlet extends HttpServlet {

	private final Kennel kennel = KennelFactory.initialiseKennel();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("username", request.getParameter("username"));
		request.setAttribute("report", KennelReport.generateReport(kennel));

		// When booking button is clicked, book a pen
		String dogName = request.getParameter("dogname");
		if (request.getParameter("dogsize") != null && dogName != null) {
			if (!request.getParameter("dogsize").isEmpty() && !dogName.isEmpty()) {
				DogSize selectedDogSize = convert(request.getParameter("dogsize"));
				kennel.bookPen(selectedDogSize, dogName);
				request.setAttribute("report", KennelReport.generateReport(kennel));
				request.setAttribute("bookingconfirmation", "A " + request.getParameter("dogsize") + " kennel is booked.");
			}
		}

		String checkoutDogName = request.getParameter("checkoutdogname");
		if (checkoutDogName != null) {
			if (!checkoutDogName.isEmpty()) {
				boolean checkedOut = kennel.checkout(checkoutDogName);

				if (checkedOut) {
					request.setAttribute("checkoutconfirmation", checkoutDogName + " is checked out.");
				} else {
					request.setAttribute("checkoutconfirmation", checkoutDogName + " is not checked out.");
				}
				request.setAttribute("report", KennelReport.generateReport(kennel));
			}
		}

		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	private DogSize convert(String dogSize) {
		if (dogSize.equalsIgnoreCase("small")) {
			return DogSize.SMALL;
		} else if (dogSize.equalsIgnoreCase("medium")) {
			return DogSize.MEDIUM;
		} else {
			return DogSize.GIANT;
		}
	}
}

package server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.ac.ncl.csc3422.kennelbooking.DogSize;
import uk.ac.ncl.csc3422.kennelbooking.Kennel;
import uk.ac.ncl.csc3422.kennelbooking.KennelFactory;
import uk.ac.ncl.csc3422.kennelbooking.Pen;

/**
 *
 * @author Milton
 */
public class IndexServlet extends HttpServlet {

	private final Kennel kennel = KennelFactory.initialiseKennel();
	private String userName = "";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("username") != null && !request.getParameter("username").isEmpty()) {
			userName = request.getParameter("username");
		}
		request.setAttribute("username", userName);
		request.setAttribute("report", getReadableReport());

		// When booking button is clicked, book a pen
		String dogName = request.getParameter("dogname");
		if (request.getParameter("dogsize") != null && dogName != null) {
			if (!request.getParameter("dogsize").isEmpty() && !dogName.isEmpty()) {
				DogSize selectedDogSize = convert(request.getParameter("dogsize"));
				kennel.bookPen(selectedDogSize, dogName);
				request.setAttribute("report", getReadableReport());
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
				request.setAttribute("report", getReadableReport());
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

	/**
	 * This method returns a readable report that splits into various lines
	 * using HTML injection.
	 *
	 * @return A report that splits into various lines using HTML injection
	 */
	private String getReadableReport() {
		String report = "Welcome to the Dog guest House kennels! This kennel has 7 pens and a maximium capacity of 12.<br>";
		for (Pen p : kennel.getPens()) {
			String indicator = "";
			if (!p.getDogName().isEmpty()) {
				indicator = "not ";
			}

			report += "Pen " + p.getPenNumber() + " is " + indicator + "vacant and has room for " + p.getCapacity() + " x small dog.<br>";
		}
		return report;
	}
}

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

		// Book a pen if it the book button is clicked
		if (!isNullOrEmpty(request.getParameter("book"))) {
			if (!isNullOrEmpty(request.getParameter("dogsize")) && !isNullOrEmpty(dogName)) {
				DogSize selectedDogSize = convert(request.getParameter("dogsize"));
				kennel.bookPen(selectedDogSize, dogName);
				request.setAttribute("report", getReadableReport());
				request.setAttribute("bookingconfirmation", "A " + request.getParameter("dogsize") + " kennel is booked.");
			}
		}

		// Check out a dog if the check out button is clicked
		if (!isNullOrEmpty(request.getParameter("checkout"))) {
			String checkoutDogName = request.getParameter("checkoutdogname");
			if (!isNullOrEmpty(checkoutDogName)) {
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
			if (!p.isVacant()) {
				indicator = "not ";
			}

			String size;
			if (p.getPenNumber() >= 1 && p.getPenNumber() <= 3) {
				size = "small";
			} else if (p.getPenNumber() > 3 && p.getPenNumber() <= 6) {
				size = "medium";
			} else {
				size = "giant";
			}

			report += "Pen " + p.getPenNumber() + " is " + indicator + "vacant and has room for " + p.getCapacity() + " x " + size + " dog.<br>";
		}
		return report;
	}

	/**
	 * This method returns true if the given string is neither null nor empty.
	 *
	 * @param s A given string
	 * @return true if the given string is neither null nor empty
	 */
	private boolean isNullOrEmpty(String s) {
		return s == null || s.isEmpty();
	}
}
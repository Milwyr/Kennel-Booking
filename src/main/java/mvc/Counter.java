package mvc;

import java.io.Serializable;

/**
 *
 * @author Milton
 */
public class Counter implements Serializable {

	private int counter = 0;
	private String last;

	public int getCount() {
		counter++;
		return counter;
	}

	public int doubleCount() {
		counter = counter * 2;
		return counter;
	}

	public void setLast(String uri) {
		last = uri;
	}

	public String getLast() {
		return last;
	}
}
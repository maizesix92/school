package sc;

import java.util.concurrent.atomic.AtomicInteger;

//is this class thread-safe?
public class Range {
	private final AtomicInteger lower = new AtomicInteger(0);
	private final AtomicInteger upper = new AtomicInteger(0);
	//invariant: lower <= upper

	public synchronized void setLower(int i) {
		if (i > upper.get()) {
			throw new IllegalArgumentException ("Can't set lower to " + i + " > upper");
		}
		lower.set(i);

	}

	public synchronized void setUpper(int i) {
		if (i < lower.get()) {
			throw new IllegalArgumentException ("Can't set upper to " + i + " < lower");
		}
		upper.set(i);

	}

	public synchronized boolean isInRange(int i) {
		// To get the most updated value at any time, synchronized is needed
		return (i >= lower.get()) && i <= upper.get();
	}
}
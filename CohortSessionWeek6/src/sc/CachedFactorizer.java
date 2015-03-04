package sc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class FactorizerUser {
	public static void main (String[] args) {
		CachedFactorizer factorizer = new CachedFactorizer ();
		Thread tr1 = new Thread (new MyRunnable(factorizer));
		Thread tr2 = new Thread (new MyRunnable(factorizer));
		tr1.start();
		tr2.start();
		
		try {
			tr1.join();
			tr2.join();
		}
		catch (Exception e) {
			
		}
	}
}

class MyRunnable implements Runnable {
	private CachedFactorizer factorizer;
	
	public MyRunnable (CachedFactorizer factorizer) {
		this.factorizer = factorizer; 
	}
	
	public void run () {
		Random random = new Random ();
		factorizer.factor(random.nextInt(100));
	}
}

public class CachedFactorizer {
	// Not necessary to use the same lock
	// lastFactor and lastNumber must be modified at the same instance
	private int lastNumber;
	private List<Integer> lastFactors;
	// use a lock to guard the 2 vars if want to maintain correct cacheHitRatio
	private long hits;	// how many times service has been called
	private long cacheHits;	// how many times a number hits
	
	public long getHits () {
		return hits;
	}
	
	public double getCacheHitRatio () {
		return (double) cacheHits/ (double) hits;
	}
	
	public List<Integer> service (int input) {
		List<Integer> factors = null;
		++hits;
		
		if (input == lastNumber) {
			++cacheHits;
			factors = new ArrayList<Integer>(lastFactors);
		}
		
		if (factors == null) {
			factors = factor(input);
			lastNumber = input;
			lastFactors = new ArrayList<Integer>(factors);
		}
		
		return factors;
	}
	
	public List<Integer> factor(int n) {		
		List<Integer> factors = new ArrayList<Integer>();
		for (int i = 2; i <= n; i++) {
			while (n % i == 0) {
		        factors.add(i);
		        n /= i;
		    }
		}
		
		return factors;
	}
}
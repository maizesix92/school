package sc;

import java.util.concurrent.atomic.AtomicInteger;

public class FirstFixWithAtomicInteger {
	public static AtomicInteger atomicInteger;

	public static void main(String args[]){
		atomicInteger = new AtomicInteger(0);
		int numberofThreads = 20000;
		B[] threads = new B[numberofThreads];

		for (int i = 0; i < numberofThreads; i++) {
			threads[i] = new B();
			threads[i].start();
		}

		try {
			for (int i = 0; i < numberofThreads; i++) {
				threads[i].join();
			}
		} catch (InterruptedException e) {
			System.out.println("some thread is not finished");
		}

		System.out.print("The result is ... ");
		System.out.print("wait for it ... ");
		System.out.println(atomicInteger.get());		
	}
}

class B extends Thread {
	public void run () {
		FirstFixWithAtomicInteger.atomicInteger.incrementAndGet();
	}	
}

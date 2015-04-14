package sc;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import junit.framework.TestCase;

import org.junit.Test;




//this class is thread safe
public class BoundedBufferTest extends TestCase {	
	private static final long LOCKUP_DETECT_TIMEOUT = 1000;

		@Test
		public void testIsEmptyWhenConstructued () {
			BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
			assertTrue(bb.isEmpty());
			assertFalse(bb.isFull());
		}
	
		@Test
		public void testIsFullAfterPuts () throws InterruptedException {
			BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
			for (int i = 0; i < 10; i++) {
				bb.put(i);
			}
	
			assertTrue(bb.isFull());
			assertFalse(bb.isEmpty());
		}
	
		@Test
		public void testTakeBlocksWhenEmpty () {
			final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
			Thread taker = new Thread() {
				public void run() {
					try {
						int unused = bb.take();
						fail();
					} catch (InterruptedException success) {} //if interrupted, the exception is caught here
				}
			};
	
			try {
				taker.start();
				Thread.sleep(LOCKUP_DETECT_TIMEOUT);
				taker.interrupt();
				taker.join(LOCKUP_DETECT_TIMEOUT);
				assertFalse(taker.isAlive()); //the taker should not be alive for some time
			} catch (Exception unexpected) {
				fail(); //it fails for other exceptions
			}
		}
		
		/**Tests if the bounded buffer will be empty after taking out the elements that were put in
		 * 
		 */
		@Test
		public void testIfEmptyAfterTake(){
			final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
			for (int i = 0; i < 10; i++) {
				try {
					bb.put(i);
				} catch (InterruptedException e) {
					fail();
				}
			}
			for (int i = 0; i < 10; i++) {
				try {
					bb.take();
				} catch (InterruptedException e) {
					fail();
				}				
			}
			assertTrue(bb.isEmpty());
			assertFalse(bb.isFull());
			
		}

	/**This method tests if the BoundedBuffer object is thread-safe
	 * 
	 */
	@Test
	public void testThreadSafetyTakePut(){
		final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		int NUM_OF_GIVER = 20;
		int NUM_OF_TAKER = 10;
		ArrayList<Thread> lisOfGiver = new ArrayList<>();
		ArrayList<Thread> lisOfTaker = new ArrayList<>();
		for(int i = 0; i < NUM_OF_GIVER; i++){
			lisOfGiver.add(new Giver(bb, i));
		}
		for(int i = 0; i < NUM_OF_TAKER; i++){
			lisOfTaker.add(new Taker(bb));
		}
		for (Thread thread : lisOfGiver) {
			thread.start();
		}
		for (Thread thread : lisOfTaker) {
			thread.start();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			fail();
		}
		for (Thread thread : lisOfTaker) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				fail();
			}
		}
		for (Thread thread : lisOfGiver) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				fail();
			}
		}
		// If NUM_OF_GIVERS == 20, NUM_OF_TAKERS == 20 
//		assertFalse(bb.isFull());
//		assertTrue(bb.isEmpty());
		////
		// If NUM_OF_GIVERS == 20, NUM_OF_TAKERS == 10 
		assertTrue(bb.isFull());
	}

}

class Taker extends Thread{
	BoundedBuffer<Integer> bb;
	public Taker(BoundedBuffer<Integer> bb) {
		this.bb = bb;
	}

	@Override
	public void run() {
		try {
			bb.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Giver extends Thread{
	BoundedBuffer<Integer> bb;
	Integer x;
	public Giver(BoundedBuffer<Integer> bb, Integer x) {
		this.bb = bb;
		this.x = x;
	}
	
	@Override
	public void run() {
		try {
			bb.put(x);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
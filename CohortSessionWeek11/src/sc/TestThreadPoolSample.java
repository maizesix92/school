package sc;

import java.util.concurrent.*;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TestThreadPoolSample extends TestCase {

	@Test
	public void testPoolExpansion() throws InterruptedException {
		int max_pool_size = 13;
		ExecutorService exec = Executors.newFixedThreadPool(max_pool_size);


		//todo: insert your code here to complete the test case
		//hint: you can use the following code to get the number of active threads in a thread pool
		int numThreads = 0;
		for (int i = 0; i < max_pool_size + 15; i++){
			exec.execute(new Runnable() {

				@Override
				public void run() {
					
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Do task");
				}
			});
			
			
		}

		if (exec instanceof ThreadPoolExecutor) {
			numThreads = ((ThreadPoolExecutor) exec).getActiveCount();
			System.out.println(numThreads);
			assertTrue(max_pool_size >= numThreads);
		}
		exec.shutdown();
	}
}

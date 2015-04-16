package sc;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.TestCase;

import org.junit.Test;

public class CasCounterTest extends TestCase {
	
	private static int increments = 500000;

	@Test
	public void testPerformanceCASCounter () {
		// Use thread pool to minimize thread creation time
		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(10);
		ArrayList<Future<Integer>> lisOfAc = new ArrayList<>();
		ArrayList<Future<Integer>> lisOfLc = new ArrayList<>();
		AtomicCounter ac = new AtomicCounter();
		LockCounter lc = new LockCounter();
		
		long timeIn = System.currentTimeMillis();
		for (int i = 0; i < increments; i++){
			AcThread a = new AcThread(ac);
			exec.submit(a);
		}
		for (Future<Integer> acThread : lisOfAc) {
			try {
				acThread.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		System.out.println("AtomicCounter time: " + (System.currentTimeMillis() - timeIn));
		System.out.println("Final value: " + ac.getValue());
		timeIn = System.currentTimeMillis();
		for (int i = 0; i < increments; i++){
			LcThread a = new LcThread(lc);
			exec.submit(a);
		}
		for (Future<Integer> lcThread : lisOfLc) {
			try {
				lcThread.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		System.out.println("LockCounter time: " + (System.currentTimeMillis() - timeIn));
		System.out.println("Final value: " + lc.getValue());
	}
} 

class AcThread implements Callable<Integer>{

	AtomicCounter ac;

	public AcThread(AtomicCounter ac) {
		this.ac = ac;
	}

	@Override
	public Integer call() throws Exception {
		return ac.increment();
	}	
}

class LcThread implements Callable<Integer>{

	LockCounter lc;

	public LcThread(LockCounter lc) {
		this.lc = lc;
	}

	@Override
	public Integer call() throws Exception {
		return lc.increment();
	}	
}

class AtomicCounter {
	private AtomicInteger value = new AtomicInteger();

	public int getValue() {
		return value.get();
	}

	public int increment() {
		return value.addAndGet(1);
	}
}

class LockCounter{
	private int value = 0;

	public synchronized int getValue() {
		return value;
	}

	public synchronized int increment() {
		return value++;
	}
}
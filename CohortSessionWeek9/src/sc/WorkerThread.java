package sc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WorkerThread extends Thread {    	      
	private Map<String, Integer> map = null;

	public WorkerThread(Map<String, Integer> map) {
		this.map = map;     
	}

	// For large i limit, concurrentHashMap is faster than synchronizedMap but for lower i limit, the inverse is true
	public void run() {                
		for (int i=0; i<500000; i++) {
			// Return 2 integers between 1-1000000 inclusive
			Integer newInteger1 = (int) Math.ceil(Math.random() * 1000000);
			Integer newInteger2 = (int) Math.ceil(Math.random() * 1000000);                                           
			// 1. Attempt to retrieve a random Integer element
//			synchronized(map){
				map.get(String.valueOf(newInteger1));                       
				// 2. Attempt to insert a random Integer element
				map.put(String.valueOf(newInteger2), newInteger2);      
				
//			}
		}
	}

	public static void main(String[] args) {
		ArrayList<WorkerThread> workerThreadSynchroList = new ArrayList<>();
		ArrayList<WorkerThread> workerThreadConcurrentList = new ArrayList<>();
		Map<String, Integer> hashMap = Collections.synchronizedMap(new HashMap<String, Integer>());
		ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
		for (int i = 0; i < 10; i++) {
			workerThreadSynchroList.add(new WorkerThread(hashMap));
			workerThreadConcurrentList.add(new WorkerThread(concurrentHashMap));
		}
		System.out.println("Synchro starting");
		long timeIn = System.currentTimeMillis();
		for (WorkerThread workerThread : workerThreadSynchroList) {
			workerThread.start();
		}
		for (WorkerThread workerThread : workerThreadSynchroList) {
			try {
				workerThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Synchro list time: " + (System.currentTimeMillis() - timeIn));
		System.out.println("Concurrent starting");
		timeIn = System.currentTimeMillis();
		for (WorkerThread workerThread : workerThreadConcurrentList) {
			workerThread.start();
		}
		for (WorkerThread workerThread : workerThreadConcurrentList) {
			try {
				workerThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Concurrent list time: " + (System.currentTimeMillis() - timeIn));

	}
}
package deadlock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock {
	
	public static ReentrantLock lock1 = new ReentrantLock();
	public static ReentrantLock lock2 = new ReentrantLock();
	public static int counter = 0;

	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				lock1.lock();
				System.out.println("Lock 1 aquired by process 1");
				
				try {
					System.out.println("thread 1 sleeping");
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				lock2.lock();
				System.out.println("Lock 2 aquired by process 1");
				addCounter();
				lock2.unlock();
				System.out.println("Lock 2 released by process 1");
				lock1.unlock();
				System.out.println("Lock 1 released by process 1");
				System.out.println("thread 1 done");
			}
		}).start();

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				lock2.lock();
				System.out.println("Lock 2 aquired by process 2");
				lock1.lock();
				System.out.println("Lock 1 aquired by process 2");
				addCounter();
				lock1.unlock();	
				System.out.println("Lock 1 released by process 2");
				lock2.unlock();
				System.out.println("Lock 2 released by process 2");
				System.out.println("thread 2 done");
			}
		}).start();
	}
	
	public static void addCounter(){
		counter++;
		System.out.println(counter);
	}
	
	

}

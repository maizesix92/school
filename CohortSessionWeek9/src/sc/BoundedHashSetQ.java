package sc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class BoundedHashSetQ<T> {	
	private final Set<T> set;
	public Semaphore semaphore;

	public BoundedHashSetQ (int bound) {
		this.set = Collections.synchronizedSet(new HashSet<T>());
		semaphore = new Semaphore(bound);
	}

	public boolean add(T o) throws InterruptedException {
		boolean bool = set.add(o);
		if (bool == true){
			semaphore.acquire();
			System.out.println("Integer added, permits left: " + semaphore.availablePermits());
		}
		return bool;

	}

	public boolean remove (Object o) throws InterruptedException {
		boolean bool = set.remove(o);
		if (bool == true){
			semaphore.release();
			System.out.println("Integer removed, permits left: " + semaphore.availablePermits());
		}
		return bool;
	}

	public static void main(String[] args) {
		BoundedHashSetQ<Integer> hash = new BoundedHashSetQ<>(5);
//		userThreadHashSetAdd add = new userThreadHashSetAdd(hash);
//		userThreadHashSetRemove remove = new userThreadHashSetRemove(hash);
//		add.start();
//		remove.start();
		
		ArrayList<userThreadHashSetAdd> lis = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			lis.add(new userThreadHashSetAdd(hash));
		}
		
		for (userThreadHashSetAdd userThreadHashSetAdd : lis) {
			userThreadHashSetAdd.start();
		}
		
		for (userThreadHashSetAdd userThreadHashSetAdd : lis) {
			try {
				userThreadHashSetAdd.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		for (Integer userThreadHashSetAdd : hash.) {
//			
//		}
		System.out.println(hash.toString());

	}
}

class userThreadHashSetAdd extends Thread{
	private BoundedHashSetQ<Integer> set;

	public userThreadHashSetAdd(BoundedHashSetQ<Integer> set) {
		this.set = set;
	}

	@Override
	public void run() {
//		while(true){
			for (int i = 0; i < 5; i++) {
				try {
					set.add(i);
					Thread.sleep(1000+(long)Math.random()*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
//		}

	}
}

class userThreadHashSetRemove extends Thread{
	private BoundedHashSetQ<Integer> set;

	public userThreadHashSetRemove(BoundedHashSetQ<Integer> set) {
		this.set = set;
	}

	@Override
	public void run() {
		while(true){
			for (int i = 0; i < 5; i++) {
				try {
					set.remove(i);
					Thread.sleep(1000+(long)Math.random()*5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		}

	}
}
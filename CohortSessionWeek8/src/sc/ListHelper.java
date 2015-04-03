package sc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListHelper<E> {
	public java.util.List<E> list = Collections.synchronizedList(new ArrayList<E>());

	//thread-safe
	public boolean putIfAbsent(E x) {
		synchronized (list) {
			boolean absent = !list.contains(x);
			if (absent) {
				list.add(x);
			}

			return absent;			
		}
	}

	public static void main(String[] args) {
		List<Object> lis =  Collections.synchronizedList(new ArrayList<>());
		ArrayList<Thread> lisTh = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			lisTh.add(new Th(lis));
		}
		for (Thread thread : lisTh) {
			thread.start();
		}
		for (Thread thread : lisTh) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(lis.size());
	}
}

class Th extends Thread{

	List<Object> lis;
	public Th(List<Object> lis) {
		this.lis = lis;
	}

	@Override
	public void run() {
		Object o = new Object();
		if(!lis.contains(o)) lis.add(o);
	}
}
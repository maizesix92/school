package sc;

//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//
//class test {
//	public static void main (String [] args) {
//		ArrayList<String> is = new ArrayList<String>();
//		List<String> lis = Collections.synchronizedList(is);
//		synchronized(is){
//			
//		}
//		ExtendedPairWrong obj = new ExtendedPairWrong (4,5);
//		mythread t1 = new mythread(obj);
//		t1.start();
//		mythread1 t2 = new mythread1(obj);
//		t2.start();
//	}
//	
//}
//
//class mythread extends Thread {
//	private ExtendedPairWrong mine;
//	
//	public mythread ( ExtendedPairWrong m) {
//		mine = m;
//	}
//	
//	public void run () {
//		mine.increment();
//	}
//}
//
//class mythread1 extends Thread {
//	private ExtendedPairWrong mine;
//	
//	public mythread1 ( ExtendedPairWrong m) {
//		mine = m;
//	}
//	
//	public void run () {
//		mine.incrementY();
//	}
//}

public class ExtendedPairWrong extends NewPair {
	public ExtendedPairWrong (int x, int y) {
		super(x, y);
	}
	
	public synchronized void increment () {
		incrementX();
		incrementY();
	}
}

class NewPair {
	private int x;
	private int y; 
	private Object lockx = new Object ();
	private Object locky = new Object ();
	
	
	public NewPair(int x, int y) { 
		this.x = x;
		this.y = y;
	}
	
	public void incrementX() {
		synchronized (lockx) {
			x++;			
		}
	}
	
	public void incrementY() {
		synchronized (locky) {
			y++;			
		}
	}
}
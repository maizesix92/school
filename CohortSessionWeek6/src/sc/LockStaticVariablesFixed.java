package sc;


public class LockStaticVariablesFixed {
	public static Integer saving = new Integer(5000);
	public static Integer cash;
	public static Holding hold = new Holding();

	public static void main(String args[]){ 
//		saving = new Integer(5000);
		cash = new Integer(0);
		int numberofThreads = 10000;
		
		WDfixed[] threads = new WDfixed[numberofThreads];

		for (int i = 0; i < numberofThreads; i++) {
			threads[i] = new WDfixed(hold);
			threads[i].start();
		}

		try {
			for (int i = 0; i < numberofThreads; i++) {
				threads[i].join();
			}
		} catch (InterruptedException e) {
			System.out.println("some thread is not finished");
		}

		System.out.print("The result is: " + LockStaticVariablesFixed.cash);
	}
}

class Holding{
	public Holding() {
		
	}
}

class WDfixed extends Thread {
	
	private Holding hold;
	
	public WDfixed(Holding hold) {
		this.hold = hold;
	}
	
	public void run () {
		synchronized(hold){
			if (LockStaticVariablesFixed.saving >= 1000) {
				System.out.println("I am doing something.");
				LockStaticVariablesFixed.saving = LockStaticVariablesFixed.saving - 1000;
				LockStaticVariablesFixed.cash = LockStaticVariablesFixed.cash + 1000;
			}
		}
	}	
}
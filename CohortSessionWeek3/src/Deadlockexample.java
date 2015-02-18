
import java.util.concurrent.*;
import java.util.concurrent.locks.*;  // import java.util.concurrent.locks package
class A implements Runnable
{
	private Lock first, second;
	public A(Lock first, Lock second) {
		this.first = first;
		this.second = second;
	}
	public void run() {
		try {
			first.lock();
			// do something
			SleepUtilities.nap();
			second.lock();
			// do something else
		}
		finally {
			first.unlock();

			second.unlock();
			System.out.println(" class A complete");
		}
	}
}
class B implements Runnable
{
	private Lock first, second;
	public B(Lock first, Lock second) {
		this.first = first;
		this.second = second;
	}
	public void run() {
		try {
			second.lock();
			// do something
			first.lock();
			// do something else
		}
		finally {
			second.unlock();
			first.unlock();
			System.out.println(" class B complete");
		}
	}
}
public class Deadlockexample {
	// Figure 7.5


	public static void main(String arg[]) {
		Lock lockX = new ReentrantLock();
		Lock lockY = new ReentrantLock();
		Thread threadA = new Thread(new A(lockX,lockY));
		Thread threadB = new Thread(new B(lockX,lockY));
		threadA.start();
		threadB.start();
	} }
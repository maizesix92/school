
import java.util.concurrent.*;
import java.util.concurrent.locks.*;  // import java.util.concurrent.locks package
class E implements Runnable
{
	private Lock first, second;
	public E(Lock first, Lock second) {
		this.first = first;
		this.second = second;
	}
	public void run() {
		try {
			first.lock();
			// do something
			//SleepUtilities.nap();
			second.lock();
			// do something else
		}
		finally {
			first.unlock();

			second.unlock();
			System.out.println(" class C complete");
		}
	}
}
class F implements Runnable
{
	private Lock first, second;
	public F(Lock first, Lock second) {
		this.first = first;
		this.second = second;
	}
	public void run() {
		try {
			first.lock();
			// do something
			second.lock();
			// do something else
		}
		finally {
			second.unlock();
			first.unlock();
			System.out.println(" class D complete");
		}
	}
}
public class Nodeadlockexample1 {
	// Figure 7.5


	public static void main(String arg[]) {
		Lock lockX = new ReentrantLock();
		Lock lockY = new ReentrantLock();
		Thread threadA = new Thread(new E(lockX,lockY));
		Thread threadB = new Thread(new F(lockX,lockY));
		threadA.start();
		threadB.start();
	} }
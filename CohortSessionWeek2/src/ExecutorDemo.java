

import java.util.concurrent.*;

public class ExecutorDemo {
	
	class Run1 implements Runnable{

		@Override
		public void run() {
			int count=0;
			System.out.println("pool-1-thread-1 Start");
			for (int i=0;i<100;i++){
				count++;
			}
			System.out.println("pool-1-thread-1 End");
		}
		
	}
	
	class Run2 implements Runnable{

		@Override
		public void run() {
			int count=0;
			System.out.println("pool-1-thread-2 Start");
			for (int i=0;i<100;i++){
				count++;
			}
			System.out.println("pool-1-thread-2 End");
		}
		
	}
	
	class Run3 implements Runnable{

		@Override
		public void run() {
			int count=0;
			System.out.println("pool-1-thread-3 Start");
			for (int i=0;i<100;i++){
				count++;
			}
			System.out.println("pool-1-thread-3 End");
		}
		
	}
  public static void main(String[] args) {
	  ExecutorDemo e = new ExecutorDemo();
    ExecutorService executor = Executors.newFixedThreadPool(3);    // Submit runnable tasks to the executor
    executor.execute(e.new Run1());
    executor.execute(e.new Run2());
    executor.execute(e.new Run3());
    executor.execute(e.new Run1());
    executor.execute(e.new Run2());
    
    // Shut down the executor
    executor.shutdown();
    System.out.println("Finished all threads");
  }
}
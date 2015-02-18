

import java.util.concurrent.*;

public class AccountSemaphore {
  private static AccountS account = new AccountS();

  public static void main(String[] args) {
    ExecutorService executor = Executors.newCachedThreadPool();

    // Create and launch 100 threads
    for (int i = 0; i < 100; i++) {
      executor.execute(new AddAPennyTask());
    }

    executor.shutdown();

    // Wait until all tasks are finished
    while (!executor.isTerminated()) {
    }

    System.out.println("What is balance? " + account.getBalance());
  }

  // A thread for adding a penny to the account
  private static class AddAPennyTask implements Runnable {
    public void run() {
      account.deposit(1);
    }
  }

  // An inner class for account
  private static class AccountS {
	private static Semaphore semaphore = new Semaphore(1);  //  change to allow 3 threads accessing critical section
    private int balance = 0;

    public int getBalance() {
      return balance;
    }

    public void deposit(int amount) {
   
  
      // This delay is deliberately added to magnify the
      // data-corruption problem and make it easy to see.
      try {
    	semaphore.acquire();
        System.out.println(" amount =" + amount);
        int newBalance = balance + amount;
        System.out.println("newBalance = " + newBalance);
        Thread.sleep(6);
        balance = newBalance;
      }
      catch (InterruptedException ex) {
      }
      finally {
    	  semaphore.release();
      }
      }

    }
  }


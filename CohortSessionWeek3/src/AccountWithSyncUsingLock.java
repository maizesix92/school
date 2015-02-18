

import java.util.concurrent.*;
import java.util.concurrent.locks.*;  // import java.util.concurrent.locks package

public class AccountWithSyncUsingLock {
  private static Account account = new Account();

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
  private static class Account {
	private static Lock lock = new ReentrantLock();  // create a lock
    private int balance = 0;

    public int getBalance() {
      return balance;
    }

    public void deposit(int amount) {
      lock.lock(); // acquire a lock
      System.out.println(" amount =" + amount);
      int newBalance = balance + amount;
      System.out.println("newBalance = " + newBalance);
      // This delay is deliberately added to magnify the
      // data-corruption problem and make it easy to see.
      try {
        Thread.sleep(0);
      }
      catch (InterruptedException ex) {
      }

      balance = newBalance;
      lock.unlock();  // release the lock
    }
  }
}

package sc;

public class SynchronizedAccount {


	private static int balance;

	public static void main(String[] args) {
		SynchronizedAccount account = new SynchronizedAccount();
		Thread thread1 = new userThreadWithdraw(account);
		Thread thread2 = new userThreadWithdraw(account);
//				for (int i = 0; i < 10000; i++) {
//					new userThreadDeposit(account).start();
//				}
		thread1.start();
		thread2.start();
	}

	public SynchronizedAccount() {
		balance = 5000;
	}

	public void deposit(int amount){
		synchronized(this){
			balance += amount;
			System.out.println("Deposit completed");
			System.out.println("New balance: " + balance);
		}
	}

	public boolean withdraw(int amount){
		synchronized(this){
			if (amount > balance){
				System.out.println("Insufficient balance!");
				return false;
			}

			balance -= amount;
			System.out.println("Deducted " + amount);
			System.out.println("Remaining balance: " + balance);
		}
		return true;
	}

	public void checkBalance(){
		System.out.println("Your balance is: " + balance);
	}



}

class userThreadDeposit extends Thread{

	SynchronizedAccount account;

	public userThreadDeposit(SynchronizedAccount account) {
		this.account = account;
	}

	@Override
	public void run() {
		account.checkBalance();
		account.deposit(1000);

	}
}

class userThreadWithdraw extends Thread{

	SynchronizedAccount account;

	public userThreadWithdraw(SynchronizedAccount account) {
		this.account = account;
	}

	@Override
	public void run() {
		account.checkBalance();
		account.withdraw(4000);

	}
}

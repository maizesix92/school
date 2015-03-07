package cse_mid_term;

public class ThreadWait {

	public static void main(String[] args) {
		ThreadWait wait = new ThreadWait();
		UserThread2 thread2 = wait.new UserThread2();
		UserThread1 thread1 = wait.new UserThread1(thread2);
		
		thread2.start();
		thread1.start();
		
	}
	
	class UserThread1 extends Thread{
		UserThread2 user;
		public UserThread1(UserThread2 user) {
			this.user = user;
		}
		
		@Override
		public void run() {
			for (int i = 1; i < 4; i++) {
				if (i == 2){
					try {
						System.out.print(i);
						user.join();;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else System.out.print(i);
			}
		}
	}
	
	class UserThread2 extends Thread{
		public UserThread2() {
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void run() {
			for (int i = 1; i < 4; i++) {
				System.out.print("z");
			}
		}
	}

}

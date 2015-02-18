
public class ThreadMethods {
	public static int bCount = 0;

	static Thread thread1;
	static Thread thread2;
	static Thread thread3;
	static Thread thread4;

	public static void main(String[] args) {


		Runnable a = new Runnable() {

			@Override
			public void run() {
				for (int i=0;i<100;i++){
					System.out.println("a");
				}

			}
		};

		Runnable b = new Runnable() {

			@Override
			public void run() {
				for (int i=0;i<100;i++){

					if (bCount == 20){

						thread4.start();
						try {
							thread4.join();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					System.out.println("b");
					bCount++;
				}

			}
		};

		Runnable c = new Runnable() {

			@Override
			public void run() {
				
				for (int i=0;i<100;i++){
					Thread.yield();
					System.out.println("c");
				}

			}
		};

		Runnable d = new Runnable() {


			@Override
			public void run() {
				for (int i=0;i<100;i++){
					System.out.println("d");
				}

			}
		};

		thread1 = new Thread(a);
		thread2 = new Thread(b);
		thread3 = new Thread(c);
		thread4 = new Thread(d);

		thread1.start();
		thread2.start();
		thread3.start();

		//		while(thread1.isAlive() && thread2.isAlive() && thread3.isAlive() && thread4.isAlive()){


		//		}



	}

}

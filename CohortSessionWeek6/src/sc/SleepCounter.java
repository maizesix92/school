package sc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SleepCounter {

	private static int counter = 0;
	private volatile static boolean toStop = false;

	public static void main(String[] args) {
		SleepCounter sleepCounter = new SleepCounter();
		Thread listener = sleepCounter.new UserThreadInput();
		listener.start();
		while(!toStop){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counter++;
		}
		System.out.println("Number stopped at: " + counter);
	}

	class UserThreadInput extends Thread{
		BufferedReader reader = null;

		public UserThreadInput() {
			reader = new BufferedReader(new InputStreamReader(System.in));
		}

		@Override
		public void run() {
			while (true){
				System.out.println("Press 0 to stop");
				try {
					while(!reader.ready()){}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String input;
				try {
					if ((input = reader.readLine()).equals("0")){
						toStop = true;
						break;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}


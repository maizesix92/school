package bonusquestion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.locks.ReentrantLock;

public class VotingClient {
	
	private static int voteA = 0;
	private static int voteB = 0;
	
	private static ReentrantLock lockA;
	private static ReentrantLock lockB;
	
	private static boolean voted = false;

	public static void main(String[] args) throws Exception {
		String hostName = "127.0.0.1";
		int portNumber = 8888;
		lockA = new ReentrantLock();
		lockB = new ReentrantLock();
		Socket echoSocket = new Socket(hostName, portNumber);
		PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput;
		// new thread for listening to incoming votes
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true){
					try {
						String vote = in.readLine();
						if (vote.equals("A")){
							voteForA();
						}else if (vote.equals("B")){
							voteForB();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}).start();
		while (!voted){
			
		}

	}
	
	private static void voteForA(){
		lockA.lock();
		voteA++;
		lockA.unlock();
	}
	
	private static void voteForB(){
		lockB.lock();
		voteB++;
		lockB.unlock();
	}
}

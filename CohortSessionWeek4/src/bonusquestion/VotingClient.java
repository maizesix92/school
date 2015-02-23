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
	private static boolean winnerFound = false;
	
	private static int numberOfElec = 5;

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
//		numberOfElec = Integer.parseInt(in.readLine());
		// new thread for listening to incoming votes
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				int counter = 0;
				while (counter < numberOfElec){
					try {
						String vote = in.readLine();
						if (vote.equals("A")){
							voteForA();
						}else if (vote.equals("B")){
							voteForB();
						}
						counter++;
					} catch (Exception e) {
						continue;
					}
				}
				
			}
		}).start();
		while (!voted){
			System.out.println("Please choose who you want to vote for! [A/B]");
			userInput = stdIn.readLine();
			if (userInput.equals("A") || userInput.equals("B")){
				voted = true;
				out.println(userInput);
				out.flush();
				System.out.println("Thanks for voting! Please wait for the results!");
				break;
			}
			System.out.println("Invalid input");
			continue;
		}
		while(!winnerFound){
			winnerFound = checkWinner();
		}
		out.print(true);
		out.flush();
		stdIn.close();
		in.close();
		out.close();
		echoSocket.close();
		System.out.println("Closed");

	}
	
	private static boolean checkWinner(){
		if (voteA + voteB == numberOfElec){
			if (voteA > voteB) System.out.println("A is the winner!");
			else System.out.println("B is the winner!");
			return true;
		}
		return false;
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

package SC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;

/**Homework question 2
 * @author User
 *
 */
public class FactorThreadClient{

	static boolean closeClient = false;

	public static void main(String[] args) throws UnknownHostException, IOException {
		String host = "127.0.0.1";
		int port = 9062;
		Socket socket = new Socket(host, port);
		PrintWriter socketPw = new PrintWriter(socket.getOutputStream());
		BufferedReader socketBr = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		String number = socketBr.readLine();
		int startingNumber = Integer.parseInt(socketBr.readLine());
		int increment = Integer.parseInt(socketBr.readLine());

		// Print out confirmation that received params
		System.out.println("Received params");

		WorkingThread workingThread = new WorkingThread(number, startingNumber, increment);
		workingThread.start();

		// Prints when thread starts
		System.out.println("Started working thread");

		while (workingThread.getCounter() == null){
			// if no interrupt command is sent,
			if (!socketBr.ready()) continue;
			// else interrupt
			else{
				String interrupt = socketBr.readLine();
				if (interrupt.equals("interrupt_now")){
					System.out.println("Interrupt request sent");
					workingThread.interrupt();
					closeClient = true;
					break;
					//					socketBr.close();
					//					socketPw.close();
					//					socket.close();
				}
			}
		}
		if (closeClient){
			System.out.println("Client stopped on request");
		}else{
			System.out.println("Done");
			socketPw.println("Client starting from " + startingNumber + " found prime factors");
			socketPw.println(workingThread.getCounter().toString());
			socketPw.flush();
			//		socketBr.close();
			//		socketPw.close();
			//		socket.close();
		}
	}
}

class WorkingThread extends Thread{

	private int startingNumber;
	private int increment;
	private BigInteger primeNumber;
	private BigInteger counter = null;

	public WorkingThread(String number, int start, int increase) {
		startingNumber = start;
		increment = increase;
		primeNumber = new BigInteger(number);
	}

	@Override
	public void run() {
		BigInteger counter = new BigInteger("" + startingNumber);
		boolean found = false;
		while (!found && counter.longValue() < Math.sqrt(primeNumber.longValue())){
			if (this.isInterrupted()){
				System.out.println("Client starting from " + startingNumber + " is interrupted");
				break;
			}
			if (primeNumber.mod(counter) == BigInteger.ZERO){
				found = true;
				this.counter = counter;
			}else{				
				counter = counter.add(new BigInteger("" + increment));
			}			
		}	
	}

	public BigInteger getCounter(){
		return counter;
	}
}

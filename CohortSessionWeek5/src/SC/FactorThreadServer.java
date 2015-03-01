package SC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**Homework question 2
 * @author User
 *
 */
public class FactorThreadServer {
	
	private static String prime;

	private static volatile boolean isFound = false;

	private static ArrayList<Thread> listOfThreads = new ArrayList<>();

//		static String number = "4294967297";
	static String number = "1127451830576035879";
//		static String number = "1607310476370097292596889203855070567269667934905795984956897118664324212127749670298953403271979017560"
//						+ "9601429913262345458317707205045275551070134067328238564789969408388131619464241745157048346632778213573057"
//						+ "556485618554648705303440456006343361472383645679026645743883162637555685413386695834981717272746246251646"
//						+ "689847957440284107170390913806245656762456578425410156837840724227320766089203686970819068803335160153940"
//						+ "1621576507964841597205952722487750670904522932328731530640706457382162644738538813247139315456213401586618"
//						+ "8205178235764270941251970012703500878782708897174454011457922316740989484168888682501435920269738539737851"
//						+ "2021707795176654693957752089724539218654727957249417768029150657850896270793487912491488088550072643962503"
//						+ "3021936728949277390185399024276547035995915648938170415663757378637207011391538009596833354107737156273037"
//						+ "4947278583020286633662969439250086473487692720355322650480497098272751793812528986759655285106192583767791"
//						+ "71030556482884535728812916216625430187039533668677528079544176897647303445153643525354817413650848544778690"
//						+ "688201005274443717680593899";

	public static void main(String[] args) throws Exception {
		FactorThreadServer fts = new FactorThreadServer();
		BigInteger primeNumber = new BigInteger(number);
		System.out.println("Establishing connection...");
		fts.getClients(5000);
		System.out.println("Connection established");
		System.out.println("Number of working clients: " + listOfThreads.size());
		System.out.println("Working...");
		for (Thread thread : listOfThreads) {
			thread.join();
		}
		for (Thread thread : listOfThreads){
			ClientListener listener = (ClientListener) thread;
			if (listener.getPrime() != null){
				prime = listener.getPrime();
			}
		}
		BigInteger primeFactor = new BigInteger(prime);
		BigInteger primeFactor2 = primeNumber.divide(primeFactor);
		System.out.println("Prime factors are: " + primeFactor.toString() + " , " + primeFactor2.toString());
		for (Thread thread : listOfThreads){
			ClientListener listener = (ClientListener) thread;
			listener.br.close();
			listener.pw.close();
			listener.socket.close();
		}
	}


	/**If the server does not receive any connection request by the timeout time, server stops listening for client connection
	 * @param timeout - an integer to set the timeout in milliseconds
	 * @throws IOException
	 */
	private void getClients(int timeout) throws IOException{
		
		int startingNumber = 2;
		ServerSocket serverSocket = new ServerSocket(9062);
		serverSocket.setSoTimeout(timeout);
		try {
			while(true){
				Socket socket = serverSocket.accept();
				System.out.println("Client accepted");
				ClientListener clientListener = new ClientListener(socket, startingNumber);
				listOfThreads.add(clientListener);
				startingNumber++;
			}
		} catch (java.net.SocketTimeoutException e) {
			for (Thread thread : listOfThreads) {
				thread.start();
			}
		}

	}

	class ClientListener extends Thread{

		PrintWriter pw;
		BufferedReader br;
		Socket socket;
		String primeFactor;
		int start;
		boolean toStop = false;
		

		public ClientListener(Socket sock, int start) {
			socket = sock;
			this.start = start;
		}

		@Override
		public void run() {

			try{
				pw = new PrintWriter(socket.getOutputStream());
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			}catch(IOException e){
				System.out.println("Unable to get streams");
			}
			// Sends a command to all clients to start working only after all the clients are connected
			// Sending in the order of primeNumber, startingNumber, increment
			// Sends the params to clients (eg number of working clients etc)
			pw.println(number);
			pw.println("" + start); // so that the first client will start from 2 and the next will start at 3 and so on...
			pw.println("" + listOfThreads.size());	// the number of clients is the number to increment
			pw.flush();
			
//			System.out.println("Params sent to client starting from " + start);
			
			// start listening for threads that solved the problem
			// wait for br to be ready before reading line
			try {
				while (!br.ready()){
					if (isFound){
						for (Thread thread : listOfThreads) {
							ClientListener listener = (ClientListener) thread;
							PrintWriter writer = listener.getPw();
							writer.println("interrupt_now");
							writer.flush();
							
//							System.out.println("Client starting from " + start + " is interrupted");
						}
						toStop = true;
						break;
					}
				}
				// if no other threads has solved the problem
				if (!toStop){
					String message = br.readLine();					
					primeFactor = br.readLine();
					isFound = true;
					// print out the message
					System.out.println(message);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}


		}
		
		public String getPrime(){
			return primeFactor;
		}
		
		public PrintWriter getPw(){
			return pw;
		}
		
		public BufferedReader getBr(){
			return br;
		}
	}
}



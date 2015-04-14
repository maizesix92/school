package sc;
import java.io.*;
import java.math.BigInteger;
import java.net.*;

public class MultipleClient {
	public static void main(String[] args) throws Exception {
		int numberOfClients = 100; //vary this number here
		long startTime = System.currentTimeMillis();    	
		BigInteger n = new BigInteger("4294967297");
		//    	BigInteger n = new BigInteger("239839672845043");
		Thread[] clients = new Thread[numberOfClients];

		for (int i = 0; i < numberOfClients; i++) {
			clients[i] = new Thread(new Client(n, i));
			clients[i].start();
		}
		System.out.println("Clients have started");
		for (int i = 0; i < numberOfClients; i++) {
			clients[i].join();
		}
		System.out.println("Spent time: " + (System.currentTimeMillis() - startTime));
	}
}

class Client implements Runnable {
	private final BigInteger n;
	private int ID;
	public Client (BigInteger n, int ID) {
		this.n = n;
		this.ID = ID;
	}

	public void run() {
		String hostName = "127.0.0.1";
		int portNumber = 4321;

		try {
			Socket socket = new Socket(hostName, portNumber);
			System.out.println("Connected to server");
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in =
					new BufferedReader(
							new InputStreamReader(socket.getInputStream()));
			// Include the if-else statement to send a shutdown command to the server (for cohort question 3)
//			if (ID == 64){	// sending a stop command
//				out.println("Stop");
//				out.flush();
//				System.out.println("Sent stop command");
//				out.close();
//				in.close();
//				socket.close();        		
//			}else{
				out.println(n.toString());
				out.flush();        	
				System.out.println(in.readLine());
				out.close();
				in.close();
				socket.close();
//			}
		}
		catch (Exception e) {
		}
	}
}

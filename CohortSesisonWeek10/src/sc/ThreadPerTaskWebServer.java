package sc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadPerTaskWebServer {
	private static long startTime = 0;
	public static void main (String[] args) throws Exception {
		ServerSocket socket = new ServerSocket(4321, 1000);
		while (true) {
//			System.out.println("accpet..");
			Socket connection = socket.accept();					
			handleRequest(connection);
		}
		
	}
	
	private static void handleRequest (final Socket connection) throws Exception {
		new Thread(new Runnable() {			
			public void run() {
				BufferedReader in;
				PrintWriter pWriter;
				try {
//					startTime = System.currentTimeMillis();	
					in = new BufferedReader(new InputStreamReader(connection.getInputStream()));	
					pWriter = new PrintWriter(connection.getOutputStream(), true);
					pWriter.println(factor(new BigInteger(in.readLine())).toString());
					pWriter.flush();
//					System.out.println("Spent time: " + (System.currentTimeMillis() - startTime));
				} catch (IOException e) {}
				
			}					
		}).start();
		

	}
	
	private static BigInteger factor(BigInteger n) {
		BigInteger i = new BigInteger("2");
		BigInteger zero = new BigInteger("0");
		
		while (i.compareTo(n) < 0) {			
			if (n.remainder(i).compareTo(zero) == 0) {
				return i;
			}
			
			i = i.add(new BigInteger("1"));
		}
		
		assert(false);
		return null;
	}

}

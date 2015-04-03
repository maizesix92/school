package sc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class WebServer {
	public static void main (String[] args) throws Exception {
		ServerSocket socket = new ServerSocket(4321, 1000);

		long startTime = 0;
		while (true) {
			Socket connection = socket.accept();
			System.out.println("Client connected");
			if (startTime == 0) {
				startTime = System.currentTimeMillis();
			}
			handleRequest(connection);
		}
	}
	
	private static void handleRequest (Socket connection) throws Exception {
		PrintWriter pw = new PrintWriter(connection.getOutputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		BigInteger number = new BigInteger(br.readLine());
		BigInteger result = factor(number);
		pw.println(result.toString());
		pw.flush();
		pw.close();
		br.close();
		connection.close();
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
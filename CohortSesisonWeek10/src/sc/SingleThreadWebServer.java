package sc;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SingleThreadWebServer {
	private static long startTime = 0;
	public static void main (String[] args) throws Exception {
		ServerSocket socket = new ServerSocket(4321, 1000);
		while (true) {
			Socket connection = socket.accept();
//			System.out.println("accept..");
			startTime = System.currentTimeMillis();			
			handleRequest(connection);
		}
	}
	
	private static void handleRequest (Socket connection) throws Exception {
//		startTime = System.currentTimeMillis();	
		PrintWriter pw = new PrintWriter(connection.getOutputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		BigInteger number = new BigInteger(br.readLine());
		BigInteger result = factor(number);
		pw.println(result.toString());
		pw.flush();
		pw.close();
		br.close();
		connection.close();		
//		System.out.println("Spent time: " + (System.currentTimeMillis() - startTime));
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

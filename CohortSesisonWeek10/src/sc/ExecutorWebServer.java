package sc;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
 
public class ExecutorWebServer {
	private static int NTHREADS = 100;
	private static Executor exec;
	private static float CPUUtilization = 0.75f;
	private static float ratio = 0.5f; 
	
    public static void main(String[] args) throws Exception {
		ServerSocket socket = new ServerSocket(4321, 1000);
		// This line is meant for cohort question 4
		NTHREADS = (int)Math.round(Runtime.getRuntime().availableProcessors() * CPUUtilization * (1 + ratio));
		/////////////////////////////
		
		exec = Executors.newFixedThreadPool(NTHREADS);
		while (true) {
			final Socket connection = socket.accept();
			Runnable task = new Runnable () {
				public void run() {
					try {
						handleRequest(connection);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};			
			exec.execute(task);
		}
    }

	protected static void handleRequest(Socket connection) throws Exception{
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
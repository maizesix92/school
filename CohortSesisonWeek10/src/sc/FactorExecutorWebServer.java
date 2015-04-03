package sc;

import java.awt.Checkbox;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.crypto.Mac;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class FactorExecutorWebServer {
	private static long startTime = 0;
	private static final int NTHREADS = 100;
	private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);
	
    public static void main(String[] args) throws Exception {
		ServerSocket socket = new ServerSocket(4321, 1000);

		while (true) {
			final Socket connection = socket.accept();
			Runnable task = new Runnable () {
				public void run() {
					handleRequest(connection);
				}
			};			
			exec.execute(task);
		}
    }

	protected static void handleRequest(final Socket connection){
			startTime = System.currentTimeMillis();	
			PrintWriter pw;
			try {
				pw = new PrintWriter(connection.getOutputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				BigInteger number = new BigInteger(br.readLine());
				BigInteger result = factor(number);
				pw.println(result.toString());
				pw.flush();
				pw.close();
				br.close();
				connection.close();		
//				System.out.println("Spent time1: " + (System.currentTimeMillis() - startTime));
			} catch (IOException e) {}						
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
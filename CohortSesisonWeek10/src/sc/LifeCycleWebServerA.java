package sc;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**Part A of Homework Question
 * RejectedExecutionException is thrown if the if statement in the catch block is commented out and while(true) is used instead
 * This forces the server to continue accepting clients after Executor has shutdown to throw the RejectedExecutionException
 * @author User
 *
 */
public class LifeCycleWebServerA {
	private static final ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(100);
	private static LifeCycleWebServerA cycle;

	public static void main(String[] args) throws Exception {
		ServerSocket socket = new ServerSocket(4321);
		cycle = new LifeCycleWebServerA();
		while(true){	
			try {
				final Socket connection = socket.accept();
				System.out.println("Client accepted");
				Runnable task = new Runnable () {
					public void run() {
						handleRequest(connection);
					}
				};

				exec.execute(task);
			} catch (RejectedExecutionException e) {
				System.out.println("LOG: task submission is rejected.");
			}			
		}
	}

	public void stop() {
		exec.shutdown();
		System.out.println("Stopping");
	}

	protected static void handleRequest(Socket connection) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
			String message = in.readLine();
			System.out.println("Input taken");
			if (message.equals("Stop")){
				System.out.println("Request to stop");
				cycle.stop();
			}else{
				BigInteger number = new BigInteger(message);
				BigInteger result = factor(number);
				System.out.println("sending results: " + String.valueOf(result));
				out.println(result);
				out.flush();
				in.close();
				out.close();
				connection.close();
			}
		}
		catch (Exception e) {
			System.out.println("Something went wrong with the connection");
		}
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

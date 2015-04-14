package sc;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LifeCycleWebServerWithFuture {
	private static final ExecutorService exec = new ScheduledThreadPoolExecutor (100);


	public static void main(String[] args) throws Exception {
		ServerSocket socket = new ServerSocket(4321);

		while (!exec.isShutdown()) {
			try {
				final Socket connection = socket.accept();
				Runnable task = new Runnable () {
					public void run() {
						handleRequest(connection);
					}
				};
				//				Future futureTask = exec.submit(task);
				exec.execute(task);
			} catch (RejectedExecutionException e) {
				if (!exec.isShutdown()) {
					System.out.println("LOG: task submission is rejected.");
				}
			}			
		}
	}

	public void stop() {
		exec.shutdown();
	}

	protected static void handleRequest(Socket connection) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			BigInteger number = new BigInteger(in.readLine());
			Callable<BigInteger> task = new Callable<BigInteger>() {

				@Override
				public BigInteger call() throws Exception {

					return factor(number);
				}
			};			
			Future<BigInteger> futureTask =  exec.submit(task);			
			PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
			try{
				BigInteger result = futureTask.get(3, TimeUnit.MINUTES);
				System.out.println("sending results: " + String.valueOf(result));
				out.println(result);
				out.flush();
			}catch(TimeoutException e){
				out.println("Timeout");
				out.flush();
			}
			
			in.close();
			out.close();
			connection.close();
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
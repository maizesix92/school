package bonusquestion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.io.PrintWriter;

public class VotingServer {

	private static ServerSocket serverSocket;
	private static ArrayList<Socket> listOfSockets = new ArrayList<Socket>();
	private static ArrayList<PrintWriter> listOfPw = new ArrayList<PrintWriter>();
	//	private static ArrayList<BufferedReader> listOfBr = new ArrayList<BufferedReader>();
	private static ArrayList<Thread> listOfThreads = new ArrayList<Thread>();
	private static int numOfElectorates = 5;
	private static boolean winnerFound;

	public static void main(String[] args) throws Exception {
		serverSocket = new ServerSocket(8888);
		System.out.println("Waiting for electorates");
		for (int i = 0; i < numOfElectorates; i++) {
			Socket client = serverSocket.accept();
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream());
			//			out.println(numOfElectorates);
			//			out.flush();
			listOfSockets.add(client);
			//			listOfBr.add(in);
			listOfPw.add(out);
			listOfThreads.add(new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						while (true){
							String vote = in.readLine();	// getting the vote
							if ((winnerFound = Boolean.parseBoolean(vote)) == true){
								for (int j = 0; j < listOfPw.size(); j++) {
									listOfPw.get(j).close();
									listOfSockets.get(j).close();
								}
								System.out.println("Sockets closed");
								break;
							}
							for (int j = 0; j < listOfPw.size(); j++) {
								listOfPw.get(j).println(vote);
								listOfPw.get(j).flush();
								//							listOfPw.get(j).close();
							}
						}
					} catch (IOException e) {
						for (int j = 0; j < listOfPw.size(); j++) {
							try {
								listOfSockets.get(j).close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						System.out.println("Sockets closed");
					} 

				}
			}));

		}
		for (Thread thread : listOfThreads) {
			thread.start();
		}
		System.out.println("Connection done");


	}

}

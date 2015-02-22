package bonusquestion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.PrintWriter;

public class VotingServer {
	
	private static ServerSocket serverSocket;
	private static ArrayList<Socket> listOfSockets = new ArrayList<Socket>();
	private static ArrayList<PrintWriter> listOfPw = new ArrayList<PrintWriter>();
	private static ArrayList<BufferedReader> listOfBr = new ArrayList<BufferedReader>();
	private static int numOfElectorates = 5;

	public static void main(String[] args) throws Exception {
		serverSocket = new ServerSocket(8888);
		System.out.println("Waiting for electorates");
		for (int i = 0; i < numOfElectorates; i++) {
			Socket client = serverSocket.accept();
			listOfSockets.add(client);
			listOfBr.add(new BufferedReader(new InputStreamReader(client.getInputStream())));
			listOfPw.add(new PrintWriter(client.getOutputStream()));
		}
		System.out.println("Connection done");
		

	}

}

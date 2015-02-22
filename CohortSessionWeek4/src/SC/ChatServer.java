package SC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class ChatServer {

	private static ArrayList<Socket> listOfSockets;
	private static ArrayList<PrintWriter> listOfPw;
	private static ArrayList<BufferedReader> listOfBr;
	private static ServerSocket serverSocket;

	public static void main(String[] args) throws Exception {
		ChatServer cs = new ChatServer();
		listOfSockets = new ArrayList<>();
		listOfPw = new ArrayList<>();
		listOfBr = new ArrayList<>();
		serverSocket = new ServerSocket(4321);

		// Clients given 10 seconds to connect to server
		// After 10 secs are up, stop listening for sockets
		cs.getClients();
		cs.startChatGroup();
	}

	public void startChatGroup() throws Exception{
		int counter = 0;
		while (true){

			PrintWriter currentPw = listOfPw.get(counter);
			BufferedReader currentBr = listOfBr.get(counter);
			currentPw.println("Please type your message: ");
			currentPw.println("Type 'Bye' to exit");
			currentPw.println("serverEndMessage");
			currentPw.flush();
			String s = currentBr.readLine();
			
			for (int j = 0; j < listOfSockets.size(); j++) {
				PrintWriter tempPw = listOfPw.get(j);
				tempPw.print(String.format("Client %d says: %s\r\n", counter, s));
				tempPw.flush();
			}
			counter++;
			counter = counter%listOfSockets.size();
		}
	}
	
	
	public void getClients() throws Exception{
		int clientCounter = 0;
		serverSocket.setSoTimeout(10000); 
		while (true){
			try{
				Socket socket = serverSocket.accept();
				listOfSockets.add(socket);
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				listOfPw.add(pw);
				listOfBr.add(br);
				pw.print(String.format("You are client number %d\r\n", clientCounter));
				pw.flush();
				clientCounter++;
			}catch(SocketTimeoutException e){
				System.out.println("Server has stopped listening");
				System.out.println(String.format("Number of clients connected = %d", listOfSockets.size()));
				break;
			}
		}
	}

}

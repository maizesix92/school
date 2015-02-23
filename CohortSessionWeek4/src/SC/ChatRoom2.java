package SC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class ChatRoom2 {

	private static ServerSocket serverSocket;
	private static ArrayList<Socket> listOfSockets = new ArrayList<Socket>();
	private static ArrayList<PrintWriter> listOfPw = new ArrayList<PrintWriter>();
	private static ArrayList<BufferedReader> listOfBr = new ArrayList<BufferedReader>();


	public static void main(String[] args) throws Exception {
		ChatRoom2 room = new ChatRoom2();
		room.getClient();
		room.startChat();
	}

	public void startChat() {
		while(true){
			for (int i = 0; i < listOfBr.size(); i++) {
				PrintWriter currentPw = listOfPw.get(i);
				BufferedReader currentBr = listOfBr.get(i);

				try{
					currentPw.println("serverEndMessage");
					currentPw.flush();
					String s;
					while ((s = currentBr.readLine()) != null && !s.equals("endMessage")){
						if (s.contains("bye")){
							System.out.println("Client " + i + " has left the chat!");
							listOfBr.get(i).close();
							listOfSockets.get(i).close();
							listOfSockets.remove(i);
							listOfBr.remove(i);
							break;
						}
						System.out.print("Client " + i + " says: ");
						System.out.println(s);
					}
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}



	}


	public void getClient() throws IOException {
		serverSocket = new ServerSocket(1992);
		while(true){
			try {				
				serverSocket.setSoTimeout(10000);
				Socket clientSocket = serverSocket.accept();				
				PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				listOfPw.add(pw);
				listOfSockets.add(clientSocket);
				listOfBr.add(br);
				pw.println("You are now connected to the server!");
				pw.println("Please wait...");
				pw.flush();
			}catch(java.net.SocketTimeoutException e){

				break;				
			}

		}

	}
}

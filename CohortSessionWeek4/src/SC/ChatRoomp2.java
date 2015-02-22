package SC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatRoomp2 {

	private static ServerSocket serverSocket;
	private static ArrayList<Socket> listOfSockets = new ArrayList<Socket>();
	//	private static ArrayList<PrintWriter> listOfPw = new ArrayList<PrintWriter>();
	private static ArrayList<BufferedReader> listOfBr = new ArrayList<BufferedReader>();


	public static void main(String[] args) throws Exception {
		ChatRoom room = new ChatRoom();
		room.getClient();

	}


	public void getClient() throws Exception{
		serverSocket = new ServerSocket(1992);
		Thread clientListener = new Thread(new Runnable() {
			private int ID = 0;
			@Override
			public void run() {
				while(true){
					try {
						
						Socket clientSocket = serverSocket.accept();
						ID++;
						PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
						BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
						pw.println("You are now connected to the server!");
						pw.flush();
						new Thread(new Runnable(){
							final int threadID = ID;
							@Override
							public void run() {
								String s;
								try {
									while ((s = br.readLine()) != null){
										if (s.contains("bye")){
											System.out.println("Client " + threadID + " has left the chat!");
											break;
										}
										System.out.print("Client " + threadID + " says: ");
										System.out.println(s);
									}
									br.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}).start();
						listOfSockets.add(clientSocket);
//						listOfBr.add(br);
						
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				
			}
		});
		clientListener.start();
	}

}

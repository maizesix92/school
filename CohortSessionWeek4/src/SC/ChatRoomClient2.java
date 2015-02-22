package SC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.ServerException;

public class ChatRoomClient2 {

	public static void main(String[] args) throws Exception {
		String hostName = "127.0.0.1";
		int portNumber = 1992;

		Socket echoSocket = new Socket(hostName, portNumber);
		PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
		BufferedReader in =
				new BufferedReader(
						new InputStreamReader(echoSocket.getInputStream()));
		BufferedReader stdIn =
				new BufferedReader(
						new InputStreamReader(System.in));
		String userInput;
		String serverInput;
		while (true){
			while ((serverInput = in.readLine()) != null && !serverInput.equals("serverEndMessage")) {
				System.out.println(serverInput);
			}
			System.out.println("Type your message: ");
			try{
				echoSocket.setSoTimeout(5000);
				while (!stdIn.ready()){
					
				}
//				if (stdIn.ready()){
					userInput = stdIn.readLine();
					out.println(userInput);
					out.println("endMessage");
					out.flush();
					echoSocket.setSoTimeout(0);
					if (userInput.equals("bye")) {				
						break;
					}
//				}
			}catch(SocketException e){
				System.out.println("Skipping...");
				out.println();
				out.flush();
			}
		}
		echoSocket.close();
		in.close();
		out.close();
		stdIn.close();           

	}



}

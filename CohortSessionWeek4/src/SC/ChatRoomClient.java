package SC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatRoomClient {

	public static void main(String[] args) throws Exception {
		String hostName = "127.0.0.1";
		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		BufferedReader stdIn = null;
		int portNumber = 1992;
		try{
			echoSocket = new Socket(hostName, portNumber);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(
							new InputStreamReader(echoSocket.getInputStream()));
			stdIn =
					new BufferedReader(
							new InputStreamReader(System.in));
			String userInput;
			System.out.println(in.readLine());
			do {
				System.out.println("Type your message: ");
				userInput = stdIn.readLine();
				out.println(userInput);
				out.flush();
			} while (!userInput.equals("bye"));
			echoSocket.close();
			in.close();
			out.close();
			stdIn.close();        
		}catch(Exception e){
			echoSocket.close();
			in.close();
			out.close();
			stdIn.close();        
		}    

	}

}

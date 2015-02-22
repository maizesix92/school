package SC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

	public static void main(String[] args) throws Exception{
		String hostName = "127.0.0.1";
		int portNumber = 4321;

		Socket echoSocket = new Socket(hostName, portNumber);
		PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput;
		// Upon connection, print out the client number associated
		String clientNum = in.readLine();
		System.out.println(clientNum);
		System.out.println("Please wait for your turn...");
		String input;
		while (true){			
			while((input = in.readLine()) != null && !input.equals("serverEndMessage")){
				System.out.println(input);
			}
			userInput = stdIn.readLine();
			if (!userInput.equals("Bye")){
				out.println(userInput);
				out.flush();
				System.out.println("Wait for your turn");
			}else{
				out.println("Client left");
				out.flush();
				break;
			}
		}
		echoSocket.close();
		in.close();
		out.close();
		stdIn.close();           

	}

}

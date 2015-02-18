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
		BufferedReader in =
				new BufferedReader(
						new InputStreamReader(echoSocket.getInputStream()));
		BufferedReader stdIn =
				new BufferedReader(
						new InputStreamReader(System.in));
		String userInput;
		do {
			userInput = stdIn.readLine();
			out.println(userInput);
			out.flush();
			System.out.println("Husband says: " + in.readLine());
		} while (!userInput.equals("Bye"));

		echoSocket.close();
		in.close();
		out.close();
		stdIn.close();           

	}

}
package SC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

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

	}

}

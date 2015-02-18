

/**
 * Time-of-day server listening to port 6013.
 *
 * Figure 3.26
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */ 

import java.net.*;
import java.io.*;

public class Server
{
	public static void main(String[] args) {

		try {
     //       final ServerSocket sock = new ServerSocket();
       //     sock.setReuseAddress(true);
        //    sock.bind(new InetSocketAddress(1027));
			
			ServerSocket sock = new ServerSocket(1056);

			// now listen for connections
			while (true) {
				System.out.println("Waiting for client on port 1056");
				Socket client = sock.accept();
				System.out.println("Client on port 1056 accepted");
				
				// we have a connection
				PrintWriter pout = new PrintWriter(client.getOutputStream(), true);
				pout.println("Hello client! You are connected to port 1056!");
                //Reading the message from the client
               
                InputStream is = client.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
               
                String number = br.readLine();	// note to self: the line it is reading from MUST end with \n or \r if not it will get stuck as end of line is not detected
                System.out.println("Message received from client is "+number);


				
				
				
				// write to the socket and send back to client
				pout.println("Successful connection");
	


				client.close();
		//		ServerSocket.setReUseAddress(true);
			}
		}
		catch (IOException ioe) {
				System.err.println(ioe);
		}
	}
}
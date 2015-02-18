

/**
 * Client program requesting current date from server.
 *
 * Figure 3.27
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */
 
import java.net.*;
import java.io.*;

public class Client
{
	public static void main(String[] args) throws IOException {
		InputStream in = null;
		BufferedReader bin = null;
		Socket sock = null;

		try {
			sock = new Socket("127.0.0.1",1056);
			OutputStream outStream = sock.getOutputStream();
			OutputStreamWriter outStreamWrite = new OutputStreamWriter(outStream);
			BufferedWriter bufWrite = new BufferedWriter(outStreamWrite);
			
			String message = "Hello server! I am connected to port 1056!!! :)\r";
			bufWrite.write(message);
			bufWrite.flush();
			System.out.println("Message sent to server");
			
			
			in = sock.getInputStream();
			bin = new BufferedReader(new InputStreamReader(in));
			         
            
			
			String line;
			while( (line = bin.readLine()) != null)
				System.out.println(line);
			
		}
		catch (IOException ioe) {
				System.err.println(ioe);
		}
                finally {
                    sock.close();
                }
	}
}
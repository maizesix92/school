import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Question5Server {

	public static void main(String[] args) {
		ServerSocket client;
		try {
			client = new ServerSocket(5575);
			while(true){
				Socket socket = client.accept();
				// Infinitely listens for a connection
				
				PrintWriter pw = new PrintWriter(socket.getOutputStream());
				String quote = "An old silent pond...\nA frog jumps into the pond,\nsplash! Silence again.\n\nHaiku credit:http://examples.yourdictionary.com/examples-of-haiku-poems.html";
				pw.println(quote);
				pw.flush();
				
				socket.close();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}

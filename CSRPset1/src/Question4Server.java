import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Question4Server {

	public static void main(String[] args) {
		ServerSocket client;
		try {
			client = new ServerSocket(6221);
			while(true){
				Socket socket = client.accept();
				// Infinitely listens for a connection
				
				PrintWriter pw = new PrintWriter(socket.getOutputStream());
				String quote = "All's well ends well!\n";
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

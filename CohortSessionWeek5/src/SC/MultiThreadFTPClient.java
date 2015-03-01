package SC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**Homework question 3
 * @author User
 *
 */
public class MultiThreadFTPClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// Setting the timeout time in ms
		int timeout = 2;
		// Names of the different text files to pass to server
		//				String textName = "File1_1.txt";
		//				String textName = "File1_2.txt";
		//				String textName = "File1_3.txt";
		//				String textName = "File1_4.txt";
		//				String textName = "File1_5.txt";
		//				String textName = "File1_6.txt";
		//				String textName = "File1_7.txt";
		//				String textName = "File1_8.txt";
						String textName = "Big_file.txt";
		String hostName = "127.0.0.1";
		int portNumber = 1986;
		// Client stuff
		Socket echoSocket = new Socket(hostName, portNumber);
		PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		try{

			// Reading from text file
			FileReader textFile = new FileReader("D:\\SUTD\\Term 5\\Software Construction\\Week 4\\TextFiles\\" + textName); // Change this directory as according to respective computer
			BufferedReader stdIn = new BufferedReader(textFile);
			String userInput;

			while ((userInput = stdIn.readLine())!= null){				
				while(true){
					try{
						out.println(userInput);
						out.flush();
						// Checking for timeout
						long timeIn = System.currentTimeMillis();
						while(!in.ready()){
							long timeDiff = System.currentTimeMillis() - timeIn;
							if (timeDiff >= timeout) throw new SocketTimeoutException();
						}

						String acknowledgement = in.readLine();
						if (acknowledgement.equals("Received")){
							break;
						}
					}catch(SocketTimeoutException e){
						// re-transmitting 
						System.out.println("Re-transmitting");
						continue;
					}
				}
			}
			System.out.println("File sent");
			out.close();
			in.close();
			stdIn.close();
			echoSocket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}



package SC;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**Homework question 3
 * @author User
 *
 */
public class MultiThreadFTPServer {

	private static ServerSocket serverSocket;
//	private static ArrayList<Socket> listOfSockets = new ArrayList<Socket>();
	//	private static ArrayList<PrintWriter> listOfPw = new ArrayList<PrintWriter>();
//	private static ArrayList<BufferedReader> listOfBr = new ArrayList<BufferedReader>();
//	private static ArrayList<Thread> listOfThreads = new ArrayList<Thread>();
	
	public static void main(String[] args) throws Exception {
		MultiThreadFTPServer ft = new MultiThreadFTPServer();
		ft.getClient();

	}

	public void getClient() throws Exception{
		serverSocket = new ServerSocket(1986);
		Thread clientListener = new Thread(new Runnable() {
			private int ID = 0;
			@Override
			public void run() {
				while(true){
					try {

						Socket clientSocket = serverSocket.accept();
						ID++;
						PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
						BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
						new Thread(new Runnable(){
							final int threadID = ID;
							@Override
							public void run(){
								FileWriter fwToFile = null;
								try {
									fwToFile = new FileWriter(String.format("C:\\Users\\User\\Desktop\\FileResult%d.txt", threadID), true);

								} catch (IOException e1) {
									e1.printStackTrace();
								}
								String textFromFile;
								try {
									while ((textFromFile = br.readLine()) != null){
										// 1) Readline to get string
										// 2) Send a feedback to client
										// 3) Write to file

										pw.println("Received");
										pw.flush();
										fwToFile.write(textFromFile);
										fwToFile.flush();

									}
									fwToFile.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}).start();
						//						listOfSockets.add(clientSocket);

					} catch (IOException e) {
						e.printStackTrace();
					}

				}

			}
		});
		clientListener.start();
	}

}

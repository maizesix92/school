package cse_mid_term;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerClientReceive {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		PrintWriter pw = null;
		BufferedReader br = null;
		
		try {
			serverSocket = new ServerSocket(9062);
			Socket sock = serverSocket.accept();
			pw = new PrintWriter(sock.getOutputStream());
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			String message = br.readLine();
			System.out.println("Message received from client is " + message);
			pw.println("Message received from server: Successful connection");
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally{
			pw.close();
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

}

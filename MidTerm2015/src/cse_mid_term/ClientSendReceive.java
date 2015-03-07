package cse_mid_term;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;

public class ClientSendReceive {

	public static void main(String[] args) {
		
		String host = "127.0.0.1";
		int port = 9062;
		Socket sock = null;
		PrintWriter pw = null;
		BufferedReader br = null;
		
		try {
			sock = new Socket(host, port);
			pw = new PrintWriter(sock.getOutputStream());
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String message = "59";
		pw.println(message);
		pw.flush();
		System.out.println("Message sent to server : " + message);
		try {
			System.out.println(br.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			pw.close();
			try {
				br.close();
				sock.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

}

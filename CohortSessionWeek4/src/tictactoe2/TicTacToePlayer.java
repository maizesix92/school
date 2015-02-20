package tictactoe2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TicTacToePlayer {
	

	static Socket clientSocket;
	static BufferedReader in;
	static BufferedReader playerInput;
	static PrintWriter out;

	public static void main(String[] args) throws Exception{
		TicTacToePlayer player = new TicTacToePlayer();
		String host = "127.0.0.1";
		int port = 6447;
		clientSocket = new Socket(host, port);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		playerInput = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(clientSocket.getOutputStream());
		
		System.out.println("Please wait for other player...");
		while (!clientSocket.isClosed()){
			String stringFromServer;
			String playerIpt;
			boolean legit = false;
			// Getting the gameboard
			while ((stringFromServer = in.readLine()) != null && !stringFromServer.equals("end")){
				if (stringFromServer.equals("endgame")){
					player.endGame();
					legit = true;
					break;
				}
				System.out.println(stringFromServer);
			}
			// Seeking user input
			while (!legit){
				int position = 99;
				playerIpt = playerInput.readLine();
				try{
					position = Integer.parseInt(playerIpt);
					if (position >= 9){
						System.out.println("Please key in values between 0 to 8");
						continue;
					}
				}catch(NumberFormatException e){
					System.out.println("Please key in a number");
				}
				legit = true;
				out.println(position);
				out.flush();
			}
			
		}
		
	}
	
	private void endGame() throws IOException{
		in.close();
		out.close();
		playerInput.close();
		clientSocket.close();
	}

}

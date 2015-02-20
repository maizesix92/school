package tictactoe2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TicTacToe {

	static Board gameBoard = null;
	static PlayerController controller = null;
	static Player player1 = null;
	static Player player2 = null;
	static boolean winnerFound;
	static boolean playing = true;
	static PrintWriter[] listOfPw = new PrintWriter[2];
	static BufferedReader[] ListOfBr = new BufferedReader[2];
	static Socket[] listOfSockets = new Socket[2];

	public static void main(String[] args) throws Exception{
		TicTacToe game = new TicTacToe();
		listOfPw = new PrintWriter[2];
		ListOfBr = new BufferedReader[2];
		listOfSockets = new Socket[2];
		ServerSocket serverSocket = new ServerSocket(6447);
		System.out.println("Waiting for players...");
		for (int i = 0; i < listOfSockets.length; i++) {
			Socket player = serverSocket.accept();
			listOfSockets[i] = player;
			listOfPw[i] = new PrintWriter(player.getOutputStream());
			ListOfBr[i] = new BufferedReader(new InputStreamReader(player.getInputStream()));
		}
		System.out.println("Players found");		
		gameBoard = Board.getBoard();
		controller = new PlayerController(gameBoard);
		winnerFound = false;



		while(winnerFound == false){
			for (int i = 0; i < listOfSockets.length; i++) {
				PrintWriter currentPw = listOfPw[i];
				BufferedReader currentBr = ListOfBr[i];
				currentPw.print(gameBoard.getTicTacToeBoard());
				currentPw.println(String.format("Player %d please place your piece (From 0-8)", controller.getCurrentPlayerNumber()));
				currentPw.println("end");
				currentPw.flush();
				int position = 99;
				boolean legit = false;
				while(!legit){
					try{
						position = Integer.parseInt(currentBr.readLine());	// reading for position
						legit = true;
//						currentPw.println(legit); // what is the purpose of this?????? LOL
//						currentPw.flush();
						controller.getCurrentPlayer().putPiece(position);
					}catch(PositionTakenException e){
						legit = false;
					}
				}
				System.out.println(gameBoard.getTicTacToeBoard());
				CheckWinner checker = new CheckWinner(gameBoard, controller, position);
				winnerFound = checker.checkWinner();
				if (winnerFound){
					game.endGame(controller.getCurrentPlayerNumber(), checker.getWin());
					break;
				}
				controller.changePlayer();
			}
		}
		
	}

	public void endGame(int playerNo, String win) throws Exception{
		for (int i = 0; i < listOfSockets.length; i++) {
			listOfPw[i].println(win);
			listOfPw[i].println("endgame");
			listOfPw[i].close();
			ListOfBr[i].close();
			listOfSockets[i].close();
		}
		System.out.println(win);
	}


}

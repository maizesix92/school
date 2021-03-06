package tictactoe;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class TicTacToe {

	static Board gameBoard = null;
	static PlayerController controller = null;
	static Player player1 = null;
	static Player player2 = null;
	static boolean winnerFound;
	static boolean playing = true;

	public static void main(String[] args) {
		while (playing){
			TicTacToe game = new TicTacToe();
			game.initialize();
			Scanner scanIn = new Scanner(System.in);
			System.out.println(gameBoard.getTicTacToeBoard());
			while(winnerFound == false){
				System.out.println(String.format("Player %d please place your piece (From 0-8)", controller.getCurrentPlayerNumber()));
				int position = scanIn.nextInt();
				try{
					controller.getCurrentPlayer().putPiece(position);
				}catch(PositionTakenException e){
					continue;
				}
				System.out.println(gameBoard.getTicTacToeBoard());
				CheckWinner checker = new CheckWinner(gameBoard, controller, position);
				winnerFound = checker.checkWinner();
				controller.changePlayer();
			}

			String response = "";
			while (response.isEmpty()){
				System.out.println("Restart? [y/n]");
				response = scanIn.next();
				if (response.contains("y") || response.contains("Y")){
					game.restart();
				}else if (response.contains("n") || response.contains("N")) {
					playing = false;
				}else {
					System.out.println("Invalid input");
					response = "";
					continue;
				}
			}

		}

	}



	public void initialize(){

		gameBoard = Board.getBoard();
		controller = new PlayerController(gameBoard);
		winnerFound = false;
	}

	public void restart(){
		gameBoard = gameBoard.restart();;
		controller = new PlayerController(gameBoard);;
		winnerFound = false;
	}
}
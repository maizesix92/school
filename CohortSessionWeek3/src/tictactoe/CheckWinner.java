package tictactoe;

public class CheckWinner {
	
	private Board board;
	private PlayerController controller;
	private int position;
	
	public CheckWinner(Board board, PlayerController controller, int position) {
		this.board = board;
		this.controller = controller;
		this.position = position; // 0 - 8
		//
		//	0	||	1	||	2
		//========================
		//	3	||	4	||	5
		//========================
		//	6	||	7	||	8
		//
	}
	
	public boolean checkWinner(){
		int currentRow = 99;
		int currentCol = 99;
		switch(position){
		case(0):
			currentRow = 0;
			currentCol = 0;
			break;
		case(1):
			currentRow = 0;
			currentCol = 1;
			break;
		case(2):
			currentRow = 0;
			currentCol = 2;
			break;
		case(3):
			currentRow = 1;
			currentCol = 0;
			break;
		case(4):
			currentRow = 1;
			currentCol = 1;
			break;
		case(5):
			currentRow = 1;
			currentCol = 2;
			break;
		case(6):
			currentRow = 2;
			currentCol = 0;
			break;
		case(7):
			currentRow = 2;
			currentCol = 1;
			break;
		case(8):
			currentRow = 2;
			currentCol = 2;
			break;
		}
		if (board.getTTTBoard()[currentRow][0].getType() == controller.getCurrentPlayer().getPlayerType() // Matching horizontal rows
				&& board.getTTTBoard()[currentRow][1].getType() == controller.getCurrentPlayer().getPlayerType()
				&& board.getTTTBoard()[currentRow][2].getType() == controller.getCurrentPlayer().getPlayerType()
				||	// Matching vertical columns
				   board.getTTTBoard()[0][currentCol].getType() == controller.getCurrentPlayer().getPlayerType()
				&& board.getTTTBoard()[1][currentCol].getType() == controller.getCurrentPlayer().getPlayerType()
				&& board.getTTTBoard()[2][currentCol].getType() == controller.getCurrentPlayer().getPlayerType()
				||	// Matching diagonally from top left to bottom right
				   board.getTTTBoard()[0][0].getType() == controller.getCurrentPlayer().getPlayerType()
				&& board.getTTTBoard()[1][1].getType() == controller.getCurrentPlayer().getPlayerType()
				&& board.getTTTBoard()[2][2].getType() == controller.getCurrentPlayer().getPlayerType()
				||	// Matching diagonally from bottom left to top right
				   board.getTTTBoard()[0][2].getType() == controller.getCurrentPlayer().getPlayerType()
				&& board.getTTTBoard()[1][1].getType() == controller.getCurrentPlayer().getPlayerType()
				&& board.getTTTBoard()[2][0].getType() == controller.getCurrentPlayer().getPlayerType()){
			System.out.println(String.format("Player %d is the winner!", controller.getCurrentPlayerNumber()));
			return true;
		}else if (controller.getTimes() == 8){
			System.out.println("It's a tie!");
			return true;
		}
		return false;
	}
}

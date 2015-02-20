package tictactoe2;

public class Board {
	
	static Board board;
	private BoardPosition[][] ticTacToeBoard;
	
	private Board() {
		int position = 0;
		ticTacToeBoard = new BoardPosition[3][3];
		for (int i=0;i<ticTacToeBoard.length;i++){
			for (int j=0;j<ticTacToeBoard[i].length;j++){
				ticTacToeBoard[i][j] = new BoardPosition(position);
				position++;
			}
		}
	}
	
	public static Board getBoard(){
		if (board == null){
			board = new Board();
			return board;
		}else{
			return board;
		}
	}
	
	public BoardPosition getBoardPosition(int i){
		switch(i){
		case(0):
			return ticTacToeBoard[0][0];
		case(1):
			return ticTacToeBoard[0][1];
		case(2):
			return ticTacToeBoard[0][2];
		case(3):
			return ticTacToeBoard[1][0];
		case(4):
			return ticTacToeBoard[1][1];
		case(5):
			return ticTacToeBoard[1][2];
		case(6):
			return ticTacToeBoard[2][0];
		case(7):
			return ticTacToeBoard[2][1];
		case(8):
			return ticTacToeBoard[2][2];
		default:
			return null;
		}
	}
	
	public String getTicTacToeBoard(){
		String s = String.format("[%s, %s, %s]\n\r[%s, %s, %s]\n\r[%s, %s, %s]\n\r"
				, ticTacToeBoard[0][0], ticTacToeBoard[0][1], ticTacToeBoard[0][2],
						ticTacToeBoard[1][0],ticTacToeBoard[1][1],ticTacToeBoard[1][2],
								ticTacToeBoard[2][0],ticTacToeBoard[2][1],ticTacToeBoard[2][2]);
		return s;
	}
	
	public BoardPosition[][] getTTTBoard(){
		return ticTacToeBoard;
	}
	
	public Board restart(){
		board = null;
		return getBoard();
	}
	
	
	
	
	
}

package tictactoe2;

public class PlayerController {
	
	private Player player1;
	private Player player2;
	private Player[] playerOrder = new Player[2];
	private Player currentPlayer;
	private int playerPointer = 0;
	private int times = 0;
	
	public PlayerController(Board board) {
		this.player1 = new Player(board, Type.X);
		this.player2 = new Player(board, Type.O);
		playerOrder[0] = this.player1;
		playerOrder[1] = this.player2;
		currentPlayer = playerOrder[playerPointer];
	}
	
	public void changePlayer(){
		times++;
		playerPointer++;
		playerPointer = playerPointer%2;
		currentPlayer = playerOrder[playerPointer];
	}
	
	public Player getCurrentPlayer(){
		return currentPlayer;
	}
	
	public int getCurrentPlayerNumber(){
		if (currentPlayer == player1){
			return 1;
		}else return 2;
	}
	
	public int getTimes(){
		return times;
	}

}

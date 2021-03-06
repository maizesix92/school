package tictactoe2;


public class Player implements Person{

	private Type type;
	private Board board;

	public Player(Board board, Type type) {
		this.type = type;
		this.board = board;
	}



	@Override
	public void putPiece(int position) throws PositionTakenException{
		BoardPosition post = board.getBoardPosition(position);		
		post.setType(type);	
		
	}

	public Type getPlayerType(){
		return type;
	}
}

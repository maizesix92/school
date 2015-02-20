package tictactoe2;

public class PositionTakenException extends Exception{
	public PositionTakenException() {
		super("Position has been taken");
	}
}

package tictactoe;

public class PositionTakenException extends Exception{
	public PositionTakenException() {
		super("Position has been taken");
	}
}

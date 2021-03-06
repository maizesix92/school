package tictactoe2;

public class BoardPosition {

	private Type type = null;
	private int position;

	public BoardPosition(int position) {
		this.position = position;		
	}

	public void setType(Type type) throws PositionTakenException{
		if (this.type == null){
			this.type = type;
		}else throw new PositionTakenException();
	}

	public Type getType(){
		return type;
	}

	@Override
	public String toString() {
		String s = "";
		if (type == Type.X){
			s+="X";
		}else if(type == Type.O){
			s += "O";
		}else{
			s += String.format("(%d)", position);
			
		}

		return s;
	}
}

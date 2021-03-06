package electorate;

public class Electorate implements IElectorate{

	private boolean voted;
	private int ID;

	public Electorate(int ID) {
		voted = false;
		this.ID = ID;
	}

	@Override
	public void castVote(Candidate can) {
		if (voted == false){
			can.setVoteCount(can.getVoteCount()+1);
			voted = true;
		}
	}
	
	public boolean getVoted(){
		return voted;
	}
	
	public int getID(){
		return ID;
	}
}

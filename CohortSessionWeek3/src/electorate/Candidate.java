package electorate;

public class Candidate {
	
	private String name;
	private int voteCount = 0;
	
	public Candidate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}
	
	
}

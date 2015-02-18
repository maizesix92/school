package electorate;

public class CheckWinner {
	public void checkWinner(Candidate a, Candidate b){
		if (a.getVoteCount()>b.getVoteCount()){
			System.out.println("A is the winner!");
		}else if (a.getVoteCount()<b.getVoteCount()){
			System.out.println("B is the winner!");
		}else System.out.println("Its a tie!");
	}
}

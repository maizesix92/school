package electorate;

import java.util.ArrayList;
import java.util.Scanner;

public class ElectorateMain {
	
	static int numberOfElectorates = 5;
	
	

	public static void main(String[] args) {
		Candidate a = new Candidate("A");
		Candidate b = new Candidate("B");
		ArrayList<Electorate> electorateList = new ArrayList<>();
		for (int i = 0; i < numberOfElectorates; i++) {
			electorateList.add(new Electorate(i));
		}
		for (Electorate electorate : electorateList) {
			while (electorate.getVoted() == false){
				Scanner scanIn = new Scanner(System.in);
				System.out.println(String.format("Electorate %d: Would you like to vote for Candidate A or B?", electorate.getID()));
				String s = scanIn.next();
				if (s.contains("A") || s.contains("a")){
					electorate.castVote(a);
				}else if(s.contains("B") || s.contains("b")){
					electorate.castVote(b);
				}else{
					System.out.println("Please key in A or B!");
				}
			}
		}
		CheckWinner checker = new CheckWinner();
		checker.checkWinner(a, b);
		

	}

}

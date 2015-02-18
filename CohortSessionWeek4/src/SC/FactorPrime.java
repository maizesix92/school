package SC;

import java.math.BigInteger;
import java.util.ArrayList;

public class FactorPrime {

	public static void main(String[] args) {
		FactorPrime fp = new FactorPrime();
		System.out.println(fp.trialDivision(new Big4294967297));

	}
	
	public long trialDivision(BigInteger primeNumber){
		long counter = 2;
		boolean found = false;
		while (!found && counter < Math.sqrt(primeNumber.longValue())){
			if (primeNumber.longValue()%counter == 0){
				found = true;
			}else{
				counter++;
			}
		}
		return counter;
	}

}

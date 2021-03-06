package SC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;

public class FactorPrimeClientMul {

	public static void main(String[] args) throws Exception{
		String hostName = "127.0.0.1";
		int portNumber = 4321;
		String[] primeNumber;
		Socket echoSocket = new Socket(hostName, portNumber);
		BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

		primeNumber = in.readLine().split(" ");
		echoSocket.close();
		in.close();
		long timeIn = System.currentTimeMillis();
		BigInteger primeNumberIn = new BigInteger(primeNumber[0]);
		BigInteger primeFactor1 = trialDivision(primeNumberIn, Integer.parseInt(primeNumber[1]));
		BigInteger primeFactor2 = primeNumberIn.divide(primeFactor1);
		long timeOut = System.currentTimeMillis();
		System.out.println("Prime factors are:");
		System.out.print(primeFactor1.longValue() + ", ");
		System.out.println(primeFactor2.longValue());
		System.out.println("Time taken: " + (timeOut - timeIn) + " ms");
		       


	}

	public static BigInteger trialDivision(BigInteger primeNumber, Integer part){
		BigInteger counter = new BigInteger("2");
		String value = String.format("%d", 1+part.intValue());
		boolean found = false;
		while (!found && counter.longValue() < Math.sqrt(primeNumber.longValue())){
			if (primeNumber.mod(counter) == BigInteger.ZERO){
				found = true;
			}else{				
				counter = counter.add(new BigInteger(value));
			}
		}
		return counter;
	}
}



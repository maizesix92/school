package SC;

import java.math.BigInteger;

/**Cohort Exercise 6
 * @author User
 *
 */
public class FactorThread {

	public static volatile boolean isFound = false;

	public static void main(String[] args) {
		//				String number = "4294967297";
		String number = "1127451830576035879";
		//		String number = "1607310476370097292596889203855070567269667934905795984956897118664324212127749670298953403271979017560"
		//				+ "9601429913262345458317707205045275551070134067328238564789969408388131619464241745157048346632778213573057"
		//				+ "556485618554648705303440456006343361472383645679026645743883162637555685413386695834981717272746246251646"
		//				+ "689847957440284107170390913806245656762456578425410156837840724227320766089203686970819068803335160153940"
		//				+ "1621576507964841597205952722487750670904522932328731530640706457382162644738538813247139315456213401586618"
		//				+ "8205178235764270941251970012703500878782708897174454011457922316740989484168888682501435920269738539737851"
		//				+ "2021707795176654693957752089724539218654727957249417768029150657850896270793487912491488088550072643962503"
		//				+ "3021936728949277390185399024276547035995915648938170415663757378637207011391538009596833354107737156273037"
		//				+ "4947278583020286633662969439250086473487692720355322650480497098272751793812528986759655285106192583767791"
		//				+ "71030556482884535728812916216625430187039533668677528079544176897647303445153643525354817413650848544778690"
		//				+ "688201005274443717680593899";
		Thread[] list = new Thread[3];
		FactorThread fp = new FactorThread();
		Thread thread1 = fp.new FactoringPrime(number, 2);
		Thread thread2 = fp.new FactoringPrime(number, 3);
		Thread thread3 = fp.new FactoringPrime(number, 4);
		list[0] = thread1;
		list[1] = thread2;
		list[2] = thread3;
		thread1.start();
		thread2.start();
		thread3.start();
		while(!isFound){

		}
		for (Thread thread : list) {
			if (!thread.isInterrupted()) thread.interrupt();
		}
	}

	class FactoringPrime extends Thread{

		private BigInteger starting;
		private BigInteger bigInteger;


		public FactoringPrime(String number, int starting) {
			this.starting = new BigInteger(starting+"");
			this.bigInteger = new BigInteger(number);
		}

		@Override
		public void run() {
			boolean toPrint = false;
			BigInteger counter = starting;
			while (true){
				if (this.isInterrupted()){
					System.out.println("Thread interrupted");
					break;
				}
				if (bigInteger.divideAndRemainder(counter)[1] == BigInteger.ZERO){
					isFound = true;
					toPrint = true;
					break;
				}else{
					counter = counter.add(new BigInteger("3"));
				}

			}
			if (toPrint){
				System.out.println(String.format("Thread starting at %d found prime factors!", starting));
				System.out.println(String.format("Prime factors is: %s", counter.toString()));
				System.out.println(String.format("Prime factors are: %s\nand\n%s", counter.toString(), (bigInteger.divide(counter)).toString()));

			}
		}

	}


}

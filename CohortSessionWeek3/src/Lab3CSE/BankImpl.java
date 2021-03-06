package Lab3CSE;

import java.util.Arrays;
import java.util.Iterator;

public class BankImpl implements Bank {
	private int numberOfCustomers;	// the number of customers
	private int numberOfResources;	// the number of resources

	private int[] available; 	// the available amount of each resource
	private int[][] maximum; 	// the maximum demand of each customer
	private int[][] allocation;	// the amount currently allocated
	private int[][] need;		// the remaining needs of each customer

	public BankImpl (int[] resources, int numberOfCustomers) {
		numberOfResources = resources.length;
		this.numberOfCustomers = numberOfCustomers;
		available = resources;
		maximum = new int[numberOfCustomers][numberOfResources];
		allocation = new int[numberOfCustomers][numberOfResources];
		need = new int[numberOfCustomers][numberOfResources];



	}

	public int getNumberOfCustomers() {
		return numberOfCustomers;		
	}

	public void addCustomer(int customerNumber, int[] maximumDemand) {
		// TODO: initialize the maximum, allocation, need for this customer
		maximum[customerNumber] = maximumDemand;
		for (int i = 0; i < allocation[customerNumber].length; i++) {
			allocation[customerNumber][i] = 0;
			need[customerNumber][i] = maximum[customerNumber][i];
		}

		for (int i = 0; i < numberOfCustomers; i++) {				// customer
			for (int j = 0; j < numberOfResources; j++) {		// resources
				if (maximum[i][j] > available[j]){
					System.out.println("Customer requires too much resources! Aborting...");
					//System.exit(-1);
					return;
				}
			}
		}		
	}

	public void getState() {
		System.out.println("==========================================");
		System.out.print("Available resources: ");
		System.out.println(Arrays.toString(available));
		for (int i = 0; i<allocation.length;i++){
			System.out.print(String.format("Customer %d's allocation: ", i));
			System.out.println(Arrays.toString(allocation[i]));
			System.out.print(String.format("Customer %d's maximum: ", i));
			System.out.println(Arrays.toString(maximum[i]));
			System.out.print(String.format("Customer %d's need: ", i));
			System.out.println(Arrays.toString(need[i]));
		}
	}

	public synchronized boolean requestResources(int customerNumber, int[] request) {
		if (!checkSafe(customerNumber, request)){
			System.out.println("Bank cannot support request! Too much resources needed!");
			return false;
		}
		for (int i=0;i<allocation[customerNumber].length;i++){
			allocation[customerNumber][i] += request[i];
			need[customerNumber][i] = maximum[customerNumber][i] - allocation[customerNumber][i];
			available[i] -=request[i];
		}
		return true;
	}

	public synchronized void releaseResources(int customerNumber, int[] release) {
		for (int i = 0; i < allocation[customerNumber].length; i++) {
			available[i] += release[i];
			allocation[customerNumber][i] -=release[i];
		}
	}

	private synchronized boolean checkSafe(int customerNumber, int[] request) {
		for (int i = 0; i < request.length; i++) {
			if (available[i] < request[i]){
				System.out.println("Bank cannot support request! Too much resources needed!");
				return false;
			}
			if (maximum[customerNumber][i] < request[i] + allocation[customerNumber][i]){
				System.out.println("Customer's request cannot be greater than its maximum!");
				return false;
			}
		}
		
		boolean[] finish = new boolean[numberOfCustomers];
		for (int i=0;i<numberOfCustomers;i++){
			finish[i] = false;
		}
		int[] tempAvailable = Arrays.copyOf(available, available.length);
		
		for (int i = 0; i < tempAvailable.length; i++) {
			tempAvailable[i] -= request[i];
			need[customerNumber][i] -= request[i];
			allocation[customerNumber][i] += request[i];
		}
		
		// Banker's algo
		int numberOfLoops = 0;
		boolean possible = true;
		while(possible){
			possible = false;
			for (int i=0; i<numberOfCustomers; i++){
				if (finish[i] == false){
					// If finish[i] is false, do check to see if the customer can be allocated the resource
					// Checking every resource needed
					boolean customerCanAccommodate = true;
					for (int j = 0; j < tempAvailable.length; j++) {						
						if (need[i][j] > tempAvailable[j]){
							// if the need for that resource is greater than the availability, cannot accommodate and move on to next customer
							customerCanAccommodate = false;
							break;
						}
					}
					if (customerCanAccommodate == true){
						// If customer can accommodate, add the allocated so far to availability
						for (int j = 0; j < tempAvailable.length; j++) {
							tempAvailable[j] += allocation[i][j];
						}
						possible = true;
						finish[i] = true;
					}
				}
			}
			numberOfLoops++;
			if (numberOfLoops == numberOfCustomers){
				break;
			}
		}
		for (int i = 0; i < available.length; i++) {
			need[customerNumber][i] += request[i];
			allocation[customerNumber][i] -= request[i];
		}
		
		int finishCount = 0;
		for (int i=0;i<finish.length;i++){
			if (finish[i] == true){
				finishCount++;
			}
		}
		if (finishCount == finish.length){
			return true;
		}else
			return false;
	}
}
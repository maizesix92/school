package SC;

/**Cohort Exercise 5
 * @author User
 *
 */
public class MultiThreadSearch {

	static boolean isFound = false;

	public static void main(String[] args) {
		MultiThreadSearch mult = new MultiThreadSearch();
		
		int size = 50;	 // size of array to search in 
		int target = 30; // target value to search
		
		int[] list = new int[size];
		for (int i = 0; i < list.length; i++) {
			list[i] = i;
		}
		SearchingThread thread1 = mult.new SearchingThread(1, list, list.length/2, 0, target);
		SearchingThread thread2 = mult.new SearchingThread(2, list, list.length, list.length/2, target);
		thread1.start();
		thread2.start();
		// This is busy waiting
		while (!isFound){

		}
		thread1.interrupt();
		thread2.interrupt();
	}

	class SearchingThread extends Thread{

		private int lower;
		private int upper;
		private int[] integerArray;
		private int target;
		private int ID;

		public SearchingThread(int ID, int[] array, int upper, int lower, int target) {
			this.upper = upper;
			this.lower = lower;
			this.integerArray = array;
			this.target = target;
			this.ID = ID;
		}

		@Override
		public void run() {
			for (int i = lower; i < upper; i++) {
				if (this.isInterrupted()){
					System.out.println("Thread " + ID + " is interrupted");
					break;
				}
				if (target == integerArray[i]){
					isFound = true;
					System.out.println("Thread " + ID + " found " + target + " in position " + i);	
					break;
				}								
			}
			if (!isFound) System.out.println("Thread " + ID + " unable to find " + target);

		}
	}

}

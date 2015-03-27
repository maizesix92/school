package sc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchExercise {
	
	private ArrayList<String> arrayOfGrades;	
	private CountDownLatch countDownLatch;
	private ArrayList<ScannerThread> list = new ArrayList<>();
	private static int numberOfThreads = 5;
	
	public CountDownLatchExercise(ArrayList<String> array) {
		arrayOfGrades = array;
		countDownLatch = new CountDownLatch(7);
	}

	public static void main(String[] args) {
		//{"A", "B", "C", "D", "E", "F"};
		
		ArrayList<String> grades = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F"));
		ArrayList<String> listOfGrades = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			Collections.shuffle(grades);
			listOfGrades.add(grades.get(0));
			System.out.print(grades.get(0) + " , ");
		}
		System.out.println("");
		
		CountDownLatchExercise ex = new CountDownLatchExercise(listOfGrades);//Add in arraylist of grades here
		for (int i = 0; i < numberOfThreads; i++) {
			ex.list.add(new ScannerThread(new ArrayList<>(ex.arrayOfGrades.subList(i * (ex.arrayOfGrades.size()/numberOfThreads), (i+1) * (ex.arrayOfGrades.size()/numberOfThreads))), ex.countDownLatch));
		}
		for (ScannerThread thread : ex.list) {
			thread.start();
		}
		try {
			ex.countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Counted 7 Fs, stopping all threads");
		for (ScannerThread thread : ex.list) {
			thread.interrupt();
		}
	}

}

class ScannerThread extends Thread{
	
	private ArrayList<String> subArray;
	private CountDownLatch latch;
	
	public ScannerThread(ArrayList<String> subArray, CountDownLatch latch) {
		this.subArray = subArray;
		this.latch = latch;
	}
	
	@Override
	public void run() {
		for (String string : subArray) {
			if (isInterrupted()){
				System.out.println("Stopping thread");
				break;
			}
			if (string.equals("F") || string.equals("f")){
				latch.countDown();
			}
		}
	}
}
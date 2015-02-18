package Lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MultiThread {
	static int NumOfThread = 20;
	static ArrayList<Integer> overallMax = new ArrayList<>();
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		// read data from txt
		Scanner fileIn = new Scanner(new File("C:\\Users\\User\\Desktop\\SUTD\\Term 5\\Computer System Engineering\\Week 2\\input_1.txt"));
		ArrayList<Integer> array = new ArrayList<Integer>();
		ArrayList<SimpleThread> threads = new ArrayList<>();
		while(fileIn.hasNextInt()){
			array.add(fileIn.nextInt());
		}
		fileIn.close();
		
		int elemPerPart = array.size()/NumOfThread;
		for (int i=0; i<array.size();i += elemPerPart){
			ArrayList<Integer> lis = new ArrayList<>(array.subList(i, i+elemPerPart));
			SimpleThread st = new SimpleThread(lis);
			threads.add(st);
		}
		for (SimpleThread th : threads){
			th.start();
			
		}
		for (SimpleThread th : threads){
			th.join();
		}

		for (SimpleThread th : threads){
			overallMax.add(th.getMax());
		}
		Collections.sort(overallMax);
		System.out.println(overallMax.get(overallMax.size()-1));
		
		

	}
}

//extend thread
class SimpleThread extends Thread {
	private ArrayList<Integer> list;
	private int max = 0;

	public int getMax() {
		return max;
	}

	SimpleThread(ArrayList<Integer> array) {
		list = array;
	}

	public void run() {
//		Collections.sort(list);
//		max = list.get(list.size()-1);
		for (int i=1;i<list.size();i++){
			if ((list.get(i) >= list.get(i-1)) && (list.get(i) >= max)){
				max = list.get(i);
			}else if ((list.get(i) < list.get(i-1)) && (list.get(i-1) >= max)){
				max = list.get(i-1);
			}
		}
		
	}
}
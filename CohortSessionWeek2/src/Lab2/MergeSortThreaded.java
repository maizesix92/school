package Lab2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

/**
 * @author 1000800
 * code reference: http://karmaandcoding.blogspot.sg/2012/02/merge-n-sorted-listsarrays.html
 */

// All tests would be done when main is executed
public class MergeSortThreaded {
	static int threadCount = 0;
	static int[] numOfThreads1 = new int[]{2,5,10,20,50};	// "Automatic" testing 
	static int[] numOfThreads2 = new int[]{2,5,10,20,50,100,200,500,1000,2000};	// "Automatic" testing 
	static String[] dir = new String[]{"C:\\Users\\User\\Desktop\\SUTD\\Term 5\\Computer System Engineering\\Week 2\\input_1.txt", "C:\\Users\\User\\Desktop\\SUTD\\Term 5\\Computer System Engineering\\Week 2\\input_2.txt"};
	private static int TOTAL = 50;

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		
		for (int dirCount = 0; dirCount<dir.length;dirCount++){
			HashMap<String, ArrayList<Integer>> sorting = new HashMap<String, ArrayList<Integer>>();
			int[] th;
			if (dirCount == 0){
				th = numOfThreads1;
			}else th = numOfThreads2;
			// read data from txt
			Scanner fileIn = new Scanner(new File(dir[dirCount]));
			ArrayList<Integer> array = new ArrayList<Integer>();
			ArrayList<MergeThread> threads = new ArrayList<>();
			while(fileIn.hasNextInt()){
				array.add(fileIn.nextInt());
			}
			fileIn.close();
			for (int thr : th){	// thr is the number of threads it is going to split into
				int elemPerPart = array.size()/thr;
				for (int i=0; i<array.size();i += elemPerPart){
					ArrayList<Integer> lis = new ArrayList<>(array.subList(i, i+elemPerPart));
					MergeThread mt = new MergeThread(lis);
					threads.add(mt);
				}

				long timeIn = System.currentTimeMillis();

				for (MergeThread m : threads){
					m.start();
				}
				for (MergeThread m : threads){
					m.join();
				}
				// adding into hashmap
				int count = 0;
				for (MergeThread m : threads){
					String s = String.format("ArrayList%d", count);
					sorting.put(s, m.getInternal());
					count++;
				}
				ArrayList<Integer> finalResult = mergeArrays(sorting);
				long timeOut = System.currentTimeMillis();
				double timeTaken = timeOut - timeIn;

				// All printing will be done here
				
				// Meant for printing into a text file in desktop
				String pathName = String.format("C:\\Users\\User\\Desktop\\sortedArrayResultsInput%d_threads%d.txt", dirCount+1, thr);
				try {
					FileWriter fw = new FileWriter(pathName);
					PrintWriter pw = new PrintWriter(fw);
					pw.printf("Reading from input_%d \r\n", dirCount+1);
					pw.printf("Number of threads: %d \r\n", thr);
					pw.printf("Time taken to run: %f \r\n", timeTaken);
					pw.println("Result of sorted array: \r\n");
					for (Integer in : finalResult){
						pw.print(in + " ,");
					}
					pw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				System.out.println(String.format("Reading from input_%d", dirCount+1));
//				System.out.println(String.format("Number of threads: %d", thr));				
//				System.out.println(String.format("Time taken to run: %f", timeTaken));
//				System.out.println("Result of sorted array:\n");
//				for (Integer in : finalResult){
//					System.out.print(in + " ,");
//				}
//				System.out.println("\n");
//				System.out.println("==========================================");
				
				threads.clear();
			}
		}
	}

	// function to merge the N sorted arrays
	public static ArrayList<Integer> mergeArrays(HashMap<String, ArrayList<Integer>> arrayMap) {
		ArrayList<Integer> mergedList = new ArrayList<Integer>();
		Set<String> keySet = arrayMap.keySet();
		Comparator<Node> comparator = new NumericComparator();
		PriorityQueue<Node> minHeap = new PriorityQueue<Node>(TOTAL, comparator);

		Iterator<String> iter = keySet.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			ArrayList<Integer> list = arrayMap.get(key);
			if (list != null) {
				Integer data = list.remove(0);
				Node node = new Node(data, key);
				minHeap.add(node);
			}
		}

		while (minHeap.size() > 0) {
			Node node = minHeap.remove();
			//System.out.print(node.data + " ");
			mergedList.add(node.data);
			String id = node.id;
			ArrayList<Integer> list = arrayMap.get(id);
			if (list != null && list.size() > 0) {
				Integer data = list.remove(0);
				Node newNode = new Node(data, id);
				minHeap.add(newNode);
			}
		}

		return mergedList;

	}

}

// extend thread
class MergeThread extends Thread {
	private ArrayList<Integer> list;

	public ArrayList<Integer> getInternal() {
		return list;
	}

	private ArrayList<Integer> merge(ArrayList<Integer> left, ArrayList<Integer> right){
		ArrayList<Integer> res = new ArrayList<>();
		int leftCount = 0;
		int rightCount = 0;
		while (leftCount < left.size() || rightCount < right.size()){
			if (leftCount < left.size() && rightCount < right.size()){
				if (left.get(leftCount)<= right.get(rightCount)){	
					// scanning elements form the left to the right in every arraylist and cmp value
					res.add(left.get(leftCount));
					leftCount++;
				}else{
					res.add(right.get(rightCount));
					rightCount++;
				}
			}else{
				if (leftCount >= left.size()){
					// copy everything from right arraylist
					// starting idx is rightCount
					for (int i = rightCount; i<right.size();i++){
						res.add(right.get(i));
					}
				}else{
					// else consider the LEFT only
					for (int i = leftCount; i<left.size();i++){
						res.add(left.get(i));
					}
				}
				break;
			}
		}
		return res;
	}

	// TODO: implement merge sort here
	private ArrayList<Integer> mergeSort(ArrayList<Integer> lis){

		if (lis.size()>1){
			ArrayList<Integer> left = new ArrayList<>(lis.subList(0, lis.size()/2));
			left = mergeSort(left);
			ArrayList<Integer> right = new ArrayList<>(lis.subList(lis.size()/2, lis.size()));
			right = mergeSort(right);
			lis = merge(left, right);

		}
		return lis;
	}

	MergeThread(ArrayList<Integer> array) {
		list = array;
	}

	public void run() {
		// TODO: implement actions here
		list = mergeSort(list);
	}
}

class Node {
	int data;
	String id;
	Node(int data, String key) {
		this.data = data;
		this.id = key;
	}
}

class NumericComparator implements Comparator<Node> {

	public int compare(Node o1, Node o2) {
		if (o1.data < o2.data) {
			return -1;
		} else if (o1.data > o2.data) {
			return 1;
		} 
		return 0;
	}
}
package SC;

import java.util.Random;

public class MatrixThreadArray {
	
	static int[][] matA;
	static int[][] matB; 
	static int[][] result;

	public static void main(String[] args) throws InterruptedException {
		int size = 200;
		int numberOfThreads = 25;
		Thread[] listOfThreads = new Thread[numberOfThreads];
		MatrixThreadArray mat = new MatrixThreadArray();
		matA = mat.squareMatrixGenerator(size);
		matB = mat.squareMatrixGenerator(size);
		result = new int[size][size];
		for (int i = 0; i < listOfThreads.length; i++) {
			listOfThreads[i] = new Thread(mat.new Thread1(matA, matB, i*(matA.length/numberOfThreads), (i+1)*(matA.length/numberOfThreads)));
		}
		long timeIn = System.currentTimeMillis();
		for (int j = 0; j < listOfThreads.length; j++) {
			listOfThreads[j].start();
		}
		for (int i = 0; i < listOfThreads.length; i++) {
			listOfThreads[i].join();
		}
		long timeDiff = System.currentTimeMillis() - timeIn;		
		// For debugging purposes
//		for (int i = 0; i < matA.length; i++) {
//			for (int j = 0; j < matA[0].length; j++) {
//				System.out.print(matA[i][j] + " ");
//			}
//			System.out.println("");
//		}
//		System.out.println("Multiplied by...........\n");
//		for (int i = 0; i < matB.length; i++) {
//			for (int j = 0; j < matB[0].length; j++) {
//				System.out.print(matB[i][j] + " ");
//			}
//			System.out.println("");
//		}
//		System.out.println("");
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				System.out.print(result[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("Time taken: " + timeDiff + " ms");
	}
	
	public int[][] squareMatrixGenerator(int size){
		Random random = new Random();
		int[][] genMat = new int[size][size];
		for (int i = 0; i < genMat.length; i++) {
			for (int j = 0; j < genMat[0].length; j++) {
				genMat[i][j] = random.nextInt(6);
			}
		}
		return genMat;
	}
	
	class Thread1 implements Runnable{
		
		int[][] matA;
		int[][] matB;
		int lower;
		int upper;
		
		public Thread1(int[][] matA, int[][]matB, int lower, int upper) {
			this.lower = lower;
			this.upper = upper;
			this.matA = matA;
			this.matB = matB;
		}
		
		@Override
		public void run() {
			//assume that the number of columns and the number of rows are the same       	
	    	
			for (int i = lower; i < upper; i++) {
	        	for (int j = 0; j < matB[0].length; j++) {
	            	for (int k = 0; k < matA[0].length; k++) {
	            		result[i][j] += matA[i][k]*matB[k][j]; 
	            	}
	            }
	        }
		}
	}
	
	

}

package SC;

public class MatrixThread {
	
	static int[][] matA;
	static int[][] matB; 
	static int[][] result;

	public static void main(String[] args) throws InterruptedException {
		MatrixThread mat = new MatrixThread();
		matA = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
		matB = new int[][]{{2,3,1},{5,2,1},{7,9,3}};
		result = new int[matA.length][matA.length];
		Thread1 thread1 = mat.new Thread1();
		Thread2 thread2 = mat.new Thread2();
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				System.out.print(result[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	class Thread1 extends Thread{
		
		public Thread1() {
			
		}
		
		@Override
		public void run() {
			super.run();
			//assume that the number of columns and the number of rows are the same   
	    	
	    	
	    	for (int i = 0; i < matA.length; i+=2) {
	        	for (int j = 0; j < matB[0].length; j++) {
	            	for (int k = 0; k < matA[0].length; k++) {
	            		result[i][j] += matA[i][k]*matB[k][j]; 
	            	}
	            }
	        }
		}
	}
	
	class Thread2 extends Thread{
		
		
		public Thread2() {
			
		}
		
		@Override
		public void run() {
			super.run();
			//assume that the number of columns and the number of rows are the same   
	    	
	    	for (int i = 1; i < matA.length; i+=2) {
	        	for (int j = 0; j < matB[0].length; j++) {
	            	for (int k = 0; k < matA[0].length; k++) {
	            		result[i][j] += matA[i][k]*matB[k][j]; 
	            	}
	            }
	        }
		}
	}

}

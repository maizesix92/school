package SC;

public class Matrix {
	
    public static void main(String[] args) throws Exception {
    	int[][] A = {{1,2,3},{4,5,6},{7,8,9},{7,8,9}};
    	int[][] B = {{1,2,3},{4,5,6},{7,8,9}};
    	
    	int[][] result = Multi(A, B);
    	
    	PrintMatrix(result);
    }
    
    public static int[][] Multi(int[][] first, int[][] second) {
    	//assume that the number of columns in first and the number of rows in second is the same   
    	//assume that both first and second are not []
    	int[][] result = new int[first.length][second[0].length];
    	
    	for (int i = 0; i < first.length; i++) {
        	for (int j = 0; j < second[0].length; j++) {
            	for (int k = 0; k < first[0].length; k++) {
            		result[i][j] += first[i][k]*second[k][j]; 
            	}
            }
        }
            	
        return result;
    }
    
    public static void PrintMatrix (int[][] toprint) {
    	//assume that toprint is a square matrix
    	//assume that toprint is not []
    	for (int i = 0; i < toprint.length; i++) {
        	for (int j = 0; j < toprint[0].length; j++) {
        		System.out.print(toprint[i][j] + "\t");
        	}
    		System.out.println();
    	}
    }
}
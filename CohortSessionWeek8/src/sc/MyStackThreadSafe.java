package sc;

public class MyStackThreadSafe {
	private final int maxSize;
	private long[] stackArray;
	private int top; //invariant: top < stackArray.length && top >= -1	
	
	//pre-condition: s > 0
	//post-condition: maxSize == s && top == -1 && stackArray != null
	public MyStackThreadSafe(int s) { //Do we need "synchronized" for the constructor?
		maxSize = s;
	    stackArray = new long[maxSize];
	    top = -1;
	}
	
	//pre-condition: top+1 < maxSize
	//post-condition: ???
	public synchronized void push(long j) {
		stackArray[++top] = j;
	}

	//pre-condition: top >= 0
	//post-condition: ???
	public synchronized long pop() {
		long toReturn; 
		
		while (isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		toReturn = stackArray[top--];
		notifyAll();			
	    return toReturn;
	}
	
	//pre-condition: ???
	//post-condition: ???
	public long peek() {
	    return stackArray[top];
	}

	//pre-condition: ???
	//post-condition: ???
	public boolean isEmpty() {
		return (top == -1);
	}
	
	//pre-condition: ???
	//post-condition: ???
	public boolean isFull() {
		return (top == maxSize - 1);
	}
}
package sc;

public class MyStackThreadSafe {
	private final int maxSize;
	private long[] stackArray;
	private int top; //invariant: top < stackArray.length && top >= -1	
	
	//pre-condition: s > 0
	//post-condition: maxSize == s && top == -1 && stackArray != null
	public MyStackThreadSafe(int s) { //Do we need "synchronized" for the constructor? Nope
		maxSize = s;
	    stackArray = new long[maxSize];
	    top = -1;
	}
	
	//pre-condition: top+1 < maxSize
	//post-condition: stackArray.length <= maxSize && top + 1 <= maxSize && this.isEmpty() != true
	public synchronized void push(long j) {
		stackArray[++top] = j;
		notifyAll();
	}

	//pre-condition: top >= 0
	//post-condition: top >= -1 && 0 <= stackArray.length <= maxSize && this.isFull() != true
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
	    return toReturn;
	}
	
	//pre-condition: stackArray.length > 0 && 0 <= top + 1 <= maxSize && stackArray != null
	//post-condition: stackArray.length > 0 && 0 <= top + 1 <= maxSize && stackArray != null
	public long peek() {
	    return stackArray[top];
	}

	//pre-condition: -1 <= top < maxSize && stackArray != null
	//post-condition: -1 <= top < maxSize && stackArray != null
	public boolean isEmpty() {
		return (top == -1);
	}
	
	//pre-condition: -1 <= top < maxSize && stackArray != null
	//post-condition: -1 <= top < maxSize && stackArray != null
	public boolean isFull() {
		return (top == maxSize - 1);
	}
}
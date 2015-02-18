import java.util.concurrent.*;
public class BoundedBuffer<E>
{
private static final int BUFFER_SIZE1 = 5;
private E[] buffer;
private int in, out;
private Semaphore mutex;
private Semaphore empty;
private Semaphore full;
public BoundedBuffer() {
// buffer is initially empty
in = 0;
out = 0;
mutex = new Semaphore(1);
empty = new Semaphore(BUFFER_SIZE1);
full = new Semaphore(0);
buffer = (E[]) new Object[BUFFER_SIZE1]; }

public void insert(E item) {
	// Producers call this method
 try {
	empty.acquire();
	mutex.acquire();
	// add an item to the buffer
	System.out.println("insert item: " + item);
	buffer[in] = item;
	in = (in + 1) % BUFFER_SIZE1; }
 catch (InterruptedException ex) {
      }
      finally {
	mutex.release();
	full.release();
 }

}
// Figure 6.10
//Consumers call this method
public E remove() {
E item1=null;
try {
full.acquire();
mutex.acquire();
//remove an item from the buffer
item1 = buffer[out];
out = (out + 1) % BUFFER_SIZE1; }
catch (InterruptedException ex) {
}
finally {

mutex.release();
System.out.println("remove item: "+ item1);
empty.release(); 
 return item1; }
}
}
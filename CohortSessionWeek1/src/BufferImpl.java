import java.util.ArrayList;



/**
 * An implementation of the Buffer interface 
 * illustrating shared memory.
 *
 * Figure 3.18
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */


@SuppressWarnings("unchecked")

public class BufferImpl<E> implements Buffer<E>
{

	private ArrayList<E> elements;
	private int in, out, count;

	public BufferImpl() {
		count = 0;
		in = 0;
		out = 0;

		elements = new ArrayList<E>();
	}


	// producers call this method
	
	public void insert(E item) {
		
		// To make it an unbounded buffer, remove the top limit to allow unlimited entries
		
		elements.add(item);
		++count;
	}

	// consumers call this method
	
	public E remove() {
		E item;
		
		// Lower limit must still stay as buffer size cannot fall below 0
		
		while (count == 0)
			; // do nothing - nothing to consume

		
		item = elements.get(count-1);
		elements.remove(count-1);
		--count;

		return item;
	}
}



/**
 * An implementation of the Channel interface illustrating message passing.
 *
 * Figure 3.21
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */

import java.util.Vector;

public class MessageQueue<E> implements Channel1<E>
{
	private Vector<E> queue;

	public MessageQueue() {
		queue = new Vector<E>();
	}

	public void send(E item) {
		if (queue.size() <= 5)
			
		queue.addElement(item);
		else
			System.out.println(" Queue too long");
	}

	public E receive() {
		if (queue.size() == 0){
			System.out.println("Queue is empty!");
			return null;
		}else
			return queue.remove(0);
	}
}
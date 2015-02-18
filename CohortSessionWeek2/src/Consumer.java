/**
 * The consumer
 *
 * Figure 4.15
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */



import java.util.Date;
import java.util.*;

class Consumer implements Runnable
{
	private Channel1<Date> queue;

	public Consumer(Channel1<Date> queue) { 
		this.queue = queue;
	}
   
	public void run() {
		Date message;
   
		while (true)
		{
			SleepUtilities.nap();

			// consume an item from the buffer
			System.out.println("Consumer wants to consume.");

			message = queue.receive();
			if (message != null)
				System.out.println("Consumer consumed " + message);
		}
   }
}
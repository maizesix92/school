

import java.util.Date;
public class Factory
{

	public static void main(String args[]) {
		BoundedBuffer<Date> buffer = new BoundedBuffer<Date>();
		// Create the producer and consumer threads
		Thread producer = new Thread(new Producer(buffer));
		// Thread producer1 = new Thread(new Producer1(buffer));
		Thread consumer = new Thread(new Consumer(buffer));
		producer.start();
		// producer1.start();
		consumer.start();
	}
}
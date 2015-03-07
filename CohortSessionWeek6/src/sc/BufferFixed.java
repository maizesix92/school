package sc;

import java.util.Random;

public class BufferFixed {
	public static void main (String[] args) throws Exception {
		BufferSafe buffer = new BufferSafe (10);
		MyProducerFixed prod = new MyProducerFixed(buffer);
		prod.start();
		MyConsumerFixed cons = new MyConsumerFixed(buffer);
		cons.start();
		prod.join();
		cons.join();
		System.out.println(buffer.getCount());
	}
}

class MyProducerFixed extends Thread {
	private BufferSafe buffer;

	public MyProducerFixed (BufferSafe buffer) {
		this.buffer = buffer;
	}

	public void run () {
		Random random = new Random();
		try {
			Thread.sleep(random.nextInt(1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		buffer.addItem(new Object());
	}
}

class MyConsumerFixed extends Thread {
	private BufferSafe buffer;

	public MyConsumerFixed (BufferSafe buffer) {
		this.buffer = buffer;
	}

	public void run () {
		Random random = new Random();
		try {
			Thread.sleep(random.nextInt(1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		buffer.removeItem();
	}
}

class BufferSafe {
	public int SIZE;	
	private Object[] objects;
	private int count = 0;
	
	public int getCount(){
		return count;
	}

	public BufferSafe (int size) {
		SIZE = size;
		objects = new Object[SIZE];
	}

	public synchronized void addItem (Object object) {
		while (true){
			if (count < SIZE) {
				objects[count] = object;
				count++;
				System.out.println("Added");
				notifyAll();
				break;
			}else{
				try {
					System.out.println("addItem waiting");
					wait();
					continue;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public synchronized Object removeItem() {
		while(true){
			if (count > 0) {
				count--;
				notifyAll();
				System.out.println("removed");
				return objects[count];
			}
			else {
				try {
					System.out.println("removeItem waiting");
					wait();
					continue;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
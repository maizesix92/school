package buffer;

import java.util.ArrayList;
import java.util.Random;

public class BufferExercise{
	
	public static ArrayList<Item> elements = new ArrayList<>();
	public static int counter = 0;
	public static Random r;	

	public static void main(String[] args) {
		BufferExercise be = new BufferExercise();
		r = new Random();
		Producer producer = be.new Producer();
		Consumer consumer = be.new Consumer();
		producer.start();
		consumer.start();
	}
	
	class Producer extends Thread implements Buffer{
		public Producer() {
			
		}
		
		@Override
		public void run() {
			while (true){
				insert();
				try {
					sleep(r.nextInt(2000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void insert() {
			Item item = new Item(counter);
			elements.add(item);
			System.out.println("Element " + item.toString() + " added");
			counter++;
		}

		@Override
		public Item remove() {
			
			return null;
		}
	}
	
	class Consumer extends Thread implements Buffer{

		@Override
		public void run() {
			while (true){
				if (counter == 0){
					System.out.println("Cannot remove from empty list");
				}else{
					remove();
					counter--;
				}
				try {
					sleep(r.nextInt(2000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		@Override
		public void insert() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Item remove() {
			Item itemRemoved = elements.get(elements.size()-1);
			elements.remove(elements.size()-1);
			System.out.println("Element " + itemRemoved.toString() + " removed");
			return itemRemoved;
		}
		
	}

	class Item{
		int name;
		public Item(int ID) {
			name = ID;
		}
		
		public String toString(){
			return Integer.toString(name);
		}
	}
	

}

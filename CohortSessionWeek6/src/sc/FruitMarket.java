package sc;

public class FruitMarket {

	public static void main(String[] args) {
		int maxBufferSize = 5;
		Fruits fruitsMarketApple = new Fruits(new Apple(), maxBufferSize);
		Fruits fruitsMarketGrape = new Fruits(new Grape(),  maxBufferSize);
		Fruits fruitsMarketWatermelon = new Fruits(new Watermelon(), maxBufferSize);
		Fruits fruitsMarketOrange = new Fruits(new Orange(), maxBufferSize);

		Thread customerApples = new CustomerThread(fruitsMarketApple);
		Thread customerOrange = new CustomerThread(fruitsMarketOrange);
		Thread customerGrape = new CustomerThread(fruitsMarketGrape);
		Thread customerWatermelon = new CustomerThread(fruitsMarketWatermelon);
		
		Thread farmerApples = new FarmerThread(fruitsMarketApple);
		Thread farmerOrange = new FarmerThread(fruitsMarketOrange);
		Thread farmerGrape = new FarmerThread(fruitsMarketGrape);
		Thread farmerWatermelon = new FarmerThread(fruitsMarketWatermelon);

		customerApples.start();customerGrape.start();customerOrange.start();customerWatermelon.start();
		farmerApples.start();farmerGrape.start();farmerOrange.start();farmerWatermelon.start();
	}

}

class CustomerThread extends Thread{
	private Fruits marketType;
	
	public CustomerThread(Fruits marketType) {
		this.marketType = marketType;
	}
	
	@Override
	public void run() {
		// Uncomment while loop to make it insert forever
//		while(true){
			marketType.remove();
			try {
				sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//		}
	}
	
	
}

class FarmerThread extends Thread{
	private Fruits marketType;

	public FarmerThread(Fruits marketType) {
		this.marketType = marketType;
	}

	@Override
	public void run() {
		// Uncomment while loop to make it insert forever
//		while(true){
		// Uncomment for loop to make it run a fixed number of times
//		for (int i = 0; i < 6; i++) {
			marketType.insert();
			try {
				sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//		}
			
//		}

	}
}
////////////////////////////// Fruits ///////////////////////////////////
class Apple implements Fruit{
	String name;
	public Apple() {
		name = "Apple";
	}

	@Override
	public String toString() {		
		return name;
	}
}

class Orange implements Fruit{
	String name;
	public Orange() {
		name = "Orange";
	}

	@Override
	public String toString() {		
		return name;
	}
}

class Grape implements Fruit{
	String name;
	public Grape() {
		name = "Grape";
	}

	@Override
	public String toString() {		
		return name;
	}
}

class Watermelon implements Fruit{
	String name;
	public Watermelon() {
		name = "Watermelon";
	}

	@Override
	public String toString() {		
		return name;
	}
}

///////////////////////////////////////////////////////////////////
class Fruits implements FruitsBuffer{

	private int count = 0;
	private int maxBufferSize;
	private Fruit fruitType;

	public Fruits(Fruit fruit, int max) {
		maxBufferSize = max;
		fruitType = fruit;
	}

	@Override
	public void insert() {
		while (true){
			synchronized(this){
				if (count < maxBufferSize) {
					count++;
					System.out.println("Inserted " + fruitType.toString());
					System.out.println(count + " " + fruitType.toString() + " remaining");
					notifyAll();
					break;
				}else{
					System.out.println(fruitType.toString() + " is full");
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}


	}

	@Override
	public void remove() {
		while(true){
			synchronized (this) {
				if (count == 0) {
					System.out.println("Not enough " + fruitType.toString());					
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}else{
					count--;
					System.out.println(fruitType.toString() + " bought");
					System.out.println(count + " " + fruitType.toString() + " remaining");
					notifyAll();
					break;
				}
			}
		}

	}

}
//////////////////////////// Interfaces /////////////////////////////
interface FruitsBuffer{
	public void insert();
	public void remove();
}

interface Fruit{
	public String toString();
}
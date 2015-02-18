package SC;

import java.util.ArrayList;


//Represents each Observer that is monitoring changes in the subject

public class StockObserver implements Observer {

	//	private double ibmPrice;
	//	private double aaplPrice;
	//	private double googPrice;
	private ArrayList<iSubject> listOfStocks;

	// Static used as a counter

	private static int observerIDTracker = 0;

	// Used to track the observers

	private int observerID;

	// Will hold reference to the StockGrabber object

//	private iSubject stockGrabber;

	public StockObserver(){

		// List of stocks stores the list of all iSubject objects attached to the observer
		listOfStocks = new ArrayList<>();

//		this.stockGrabber = stockGrabber;

		// Assign an observer ID and increment the static counter

		this.observerID = ++observerIDTracker;

		// Message notifies user of new observer

		System.out.println("New Observer " + this.observerID);

		// Add the observer to the Subjects ArrayList

		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		

	}



	public void update() {
		// Need to access listOfStocks and change the price
		//		this.ibmPrice = ibmPrice;
		//		this.aaplPrice = aaplPrice;
		//		this.googPrice = googPrice;
		
		printThePrices();

	}

	public void printThePrices(){
		System.out.println("=======================");
		System.out.println(String.format("Observer no. %d stocks: ", observerID));
		for (iSubject iSubject : listOfStocks) {
			iSubject.print();
		}
		System.out.println("=======================");

	}

	public void addStocks(iSubject stock){
		listOfStocks.add(stock);
		stock.register(this);
	}


	public void removeStocks(iSubject stock) {
		if (!listOfStocks.remove(stock)){
			System.out.println("Stock not being observed");
		}else
		stock.unregister(this);
	}
	
	@Override
	public int getObserverID() {
		return observerID;
	}

}

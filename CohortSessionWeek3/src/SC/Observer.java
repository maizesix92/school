package SC;

//The Observers update method is called when the Subject changes

public interface Observer {
	public void update();
	public void addStocks(iSubject stock);
	public void removeStocks(iSubject stock);
	public int getObserverID();
}
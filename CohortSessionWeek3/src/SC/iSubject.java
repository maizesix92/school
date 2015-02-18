package SC;

public interface iSubject {
	public void register(Observer o);
	public void unregister(Observer o);
	public void notifyObserver();
	public void setPrice(double price);
	public void print();
}

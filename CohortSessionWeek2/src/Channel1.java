

public interface Channel1<E>
{
	public void send(E item);

	public E receive();
}

public class TestBufferSync
{
	public static void main(String[] args) {
		BoundedBuffer<String> buffer = new BoundedBuffer<String>();

		buffer.insert("Jay");
		buffer.insert("Pat");
		buffer.insert("Tom");
		buffer.insert("Jay");
		System.out.println(buffer.remove());
		buffer.insert("Pat");
	//	buffer.insert("Tom");
	//	buffer.insert("Peter");   infinite loop as buffer_size = 3

	//	String element = buffer.remove();
	//	System.out.println(element);
		System.out.println(buffer.remove());
		System.out.println(buffer.remove());
		System.out.println(buffer.remove());
		System.out.println(buffer.remove());
	//	System.out.println(buffer.remove());
		buffer.insert("Jay");
		System.out.println(buffer.remove());
	}
}
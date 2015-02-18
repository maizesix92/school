/**
 * A simple program which tests the Buffer implementation.
 */

public class Test_Buffer
{
	public static void main(String[] args) {
		Buffer<String> buffer = new BufferImpl<String>();

		buffer.insert("Jay");
		buffer.insert("Pat");
		buffer.insert("Tom");
		buffer.insert("Peter");   //infinite loop as buffer_size = 3

		String element = buffer.remove();
		System.out.println(element);
		System.out.println(buffer.remove());
		System.out.println(buffer.remove());
	}
}

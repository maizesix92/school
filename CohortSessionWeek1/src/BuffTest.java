
public class BuffTest {

	public static void main(String[] args) {
		BufferImpl<String> buf = new BufferImpl<>();
		buf.insert("Bob");
//		buf.insert("Tom");
//		buf.insert("Jerry");
//		buf.insert("Bob1");
//		buf.insert("Tom1");
//		buf.insert("Jerry1");
		
		System.out.println(buf);
		
		buf.remove();
		buf.remove();
		buf.remove();
		
		System.out.println(buf);

	}

}

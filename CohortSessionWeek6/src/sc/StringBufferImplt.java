package sc;

public class StringBufferImplt {

	public static void main(String[] args) {
		StringBuffer stringBuffer = new StringBuffer("A");
		StringBufferThread thread1 = new StringBufferThread(stringBuffer);
		StringBufferThread thread2 = new StringBufferThread(stringBuffer);
		StringBufferThread thread3 = new StringBufferThread(stringBuffer);
		thread1.start();
		thread2.start();
		thread3.start();
//		System.out.println('a');
	}
	
	

}

class StringBufferThread extends Thread{
	StringBuffer stringBuffer;
	public StringBufferThread(StringBuffer buffer) {
		stringBuffer = buffer;
	}
	
	@Override
	public void run() {
		synchronized(stringBuffer){
			for (int i = 0; i < 100; i++) {
				System.out.print(stringBuffer.toString());
			}
		}
	}
}

package sc;

public class StringBufferImplt {
	
//	public static int count = 0;

	public static void main(String[] args) throws InterruptedException {
		StringBuffer stringBuffer = new StringBuffer("A");
		StringBufferThread thread1 = new StringBufferThread(stringBuffer);
		StringBufferThread thread2 = new StringBufferThread(stringBuffer);
		StringBufferThread thread3 = new StringBufferThread(stringBuffer);
		thread1.start();
		thread2.start();
		thread3.start();

//		thread1.join();
//		thread2.join();
//		thread3.join();
//		System.out.println(count);
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
//				StringBufferImplt.count++;
			}				
			char letter = stringBuffer.charAt(0);
			letter++;
			stringBuffer.setCharAt(0, letter);
		}
	}
}

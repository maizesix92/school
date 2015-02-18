
import java.util.Date;
public class Producer implements Runnable
{
private BoundedBuffer<Date> buffer;
public Producer(BoundedBuffer<Date> buffer) {
this.buffer = buffer;
}
public void run() {
Date message;
while (true) {
// nap for awhile
SleepUtilities.nap();
// produce an item & enter it into the buffer
message = new Date();
System.out.println(" Producer thread");
buffer.insert(message);
}
}
}
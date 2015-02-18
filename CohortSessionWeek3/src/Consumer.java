import java.util.Date;
public class Consumer implements Runnable
{
private BoundedBuffer buffer;
public Consumer(BoundedBuffer buffer) {
this.buffer = buffer;
}
public void run() {
Date message;
while (true) {
// nap for awhile
SleepUtilities.nap();
// consume an item from the buffer
System.out.println(" Consumer thread");
message=(Date)buffer.remove();
}
}
}

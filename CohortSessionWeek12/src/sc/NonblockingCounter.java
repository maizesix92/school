package sc;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class NonblockingCounter {
    private AtomicInteger value = new AtomicInteger(); 
    

    public int getValue() {
        return value.get();
    }

    public int increment() {
        AtomicInteger oldValue; 
        AtomicInteger newValue;
        AtomicStampedReference<AtomicInteger> ref = new AtomicStampedReference<AtomicInteger>(value, 0);
        do{
          oldValue = ref.getReference();
          newValue = new AtomicInteger(oldValue.getAndIncrement());
        } while (!ref.compareAndSet(oldValue, newValue, ref.getStamp(), ref.getStamp()+1)); 
        return newValue.get();
    }
}

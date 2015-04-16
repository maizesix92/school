package sc;

public class StripedMap {
	//synchronization policy: buckets[n] guarded by locks[n%N_LOCKS]
	private static final int N_LOCKS = 16;
	private final Node[] buckets;
	private final Object[] locks;
	
	public StripedMap (int numBuckets) {
		buckets = new Node[numBuckets];
		locks = new Object[N_LOCKS];
		
		for (int i = 0; i < N_LOCKS; i++) {
			locks[i] = new Object();
		}
	}
	
	public Object get (Object key) {
		//todo: get the item with the given key in the map
		Node result = null;
		int bucketToConsider = hash(key);
		Node consider = buckets[bucketToConsider];
		synchronized (locks[bucketToConsider%buckets.length]) {			
			while(result == null){
				if (consider.key != key && consider.next != null){
					consider = consider.next;
				}else if (consider.key != key && consider.next == null){
					return null;
				}else{
					result = consider;
					break;
				}
			}
		}
		return result;		
	}
	
	private final int hash (Object key) {
		return Math.abs(key.hashCode() % buckets.length);
	}
	
	public void clear () {
		for (int i = 0; i < buckets.length; i++) {
			synchronized(locks[i%N_LOCKS]){
				buckets[i] = null;
			}
		}		
	}

	public int size () {
		//todo: count how many items are in the map
		int result = 0;
		for (Node node : buckets) {
			while (node.next != null){
				node = node.next;
				result++;
			}
		}
		
		return result;
	}
	
    class Node {
        Node next;
        Object key;
        Object value;
        Node(Object key, Object value, Node next) {
            this.next = next;
            this.key = key;
            this.value = value;
        }
    }
    
    public static void main(String[] args) {
		System.out.println("f".hashCode());
	}
}


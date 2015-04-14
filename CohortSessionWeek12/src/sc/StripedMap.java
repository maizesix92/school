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
		
		return null;		
	}
	
	private final int hash (Object key) {
		return Math.abs(key.hashCode() % buckets.length);
	}
	
	public void clear () {
		//todo: remove all objects in the map
	}

	public int size () {
		//todo: count how many items are in the map
		return 0; //modify this
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
}


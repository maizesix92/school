package sc;

import java.util.HashMap;
import java.util.Map;

//is this class thread-safe?
public class TrackerFixed {
	//@guarded by ???
	private final Map<String, MutablePoint> locations;
	

	//the reference locations, is it going to be an escape?
	public TrackerFixed(Map<String, MutablePoint> locations) {
		
		this.locations = deepCopy(locations);
	}

	//is this an escape?
	public synchronized Map<String, MutablePoint> getLocations () {
		synchronized(locations){
			return deepCopy(locations);
		}
		//		return locations;
	}

	//is this an escape?
	public synchronized MutablePoint getLocation (String id) {
		//		MutablePoint loc = locations.get(id);
		synchronized(locations){
			MutablePoint loc = new MutablePoint(locations.get(id));
			return loc;
		}
	}

	public synchronized void setLocation (String id, int x, int y) {
		synchronized(locations){
			MutablePoint loc = locations.get(id);

			if (loc == null) {
				throw new IllegalArgumentException ("No such ID: " + id);			
			}

			loc.x = x;
			loc.y = y;
		}
	}
	
	public Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> input){
		HashMap<String, MutablePoint> locationClone = new HashMap<>();
		for (String string : locations.keySet()) {
			locationClone.put(string, new MutablePoint(locations.get(string)));
		}
		return locationClone;		
	}

	//this class is not thread-safe (why?) and keep it unmodified.
	//
	// the int are public which makes it thread-unsafe
	class MutablePoint {
		public int x, y;

		public MutablePoint (MutablePoint p) {
			this.x = p.x;
			this.y = p.y;
		}
	}
}
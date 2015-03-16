package sc;

import java.util.Map;

//is this class thread-safe?
public class Tracker {
	//@guarded by ???
	private final Map<String, MutablePoint> locations;
	
	//the reference locations, is it going to be an escape?
	public Tracker(Map<String, MutablePoint> locations) {
		this.locations = locations;
	}
	
	//is this an escape?
	public synchronized Map<String, MutablePoint> getLocations () {
		return locations;
	}
	
	//is this an escape?
	public synchronized MutablePoint getLocation (String id) {
		MutablePoint loc = locations.get(id);
		return loc;
	}
	
	public synchronized void setLocation (String id, int x, int y) {
		MutablePoint loc = locations.get(id);
		
		if (loc == null) {
			throw new IllegalArgumentException ("No such ID: " + id);			
		}
		
		loc.x = x;
		loc.y = y;
	}
	
	//this class is not thread-safe (why?) and keep it unmodified.
	class MutablePoint {
		public int x, y;
		
		public MutablePoint (MutablePoint p) {
			this.x = p.x;
			this.y = p.y;
		}
	}
}
package sc;

import java.util.HashSet;
import java.util.Set;

public class DLExample {
	
}

/**One thread locks on Dispatcher first then Taxi
 * The other locks on Taxi first then Dispatcher
 * causing a deadlock. This could happen when dispatcher.notifyAvailable(this) in Taxi is called
 * and getImage() is called in Dispatcher. When t.getLocation() is trying to get the lock on the Taxi object that is calling
 * dispatcher.notifyAvailable(this), both threads will be stuck as they are waiting for each other.
 * @author User
 *
 */
class Taxi {
    private Point location, destination;
    private final Dispatcher dispatcher;

    public Taxi(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

	public synchronized Point getLocation() {
        return location;
    }

    public synchronized void setLocation(Point location) {
        this.location = location;
        if (location.equals(destination))
            dispatcher.notifyAvailable(this);	// deadlock here
    }

    public synchronized Point getDestination() {
        return destination;
    }
}

class Dispatcher {
    private final Set<Taxi> taxis;
    private final Set<Taxi> availableTaxis;

    public Dispatcher() {
        taxis = new HashSet<Taxi>();
        availableTaxis = new HashSet<Taxi>();
    }

    public synchronized void notifyAvailable(Taxi taxi) {
        availableTaxis.add(taxi);
    }

    public synchronized Image getImage() {
        Image image = new Image();
        for (Taxi t : taxis)
            image.drawMarker(t.getLocation());	// deadlock here
        return image;
    }
}

class Image {
    public void drawMarker(Point p) {
    }
}

class Point {
	
}


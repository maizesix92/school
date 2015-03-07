package cse_mid_term;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class Semaphores<Date> implements SemaphoresBuffer<Date>{

	private ArrayList<Date> elements;
	private int in, out, count;
	private Semaphore sem = new Semaphore(1);

	public Semaphores() {
		count = 0;
		in = 0;
		out = 0;

		elements = new ArrayList<Date>();
	}


	// producers call this method

	public void insert(Date item) {

		// To make it an unbounded buffer, remove the top limit to allow unlimited entries
		if (count == 3){
			try {
				sem.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			
			elements.add(item);
			++count;
		}
	}

	// consumers call this method

	public Date remove() {
		Date item;

		// Lower limit must still stay as buffer size cannot fall below 0

		if (count == 0)
			; // do nothing - nothing to consume
		if (sem.availablePermits() == 0) sem.release();
		item = elements.get(count-1);
		elements.remove(count-1);
		--count;

		return item;
	}



}

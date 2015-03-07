package cse_mid_term;

public interface SemaphoresBuffer <E>{
	// producers call this method
		public void insert(E item);

		// consumers call this method
		public E remove();
}

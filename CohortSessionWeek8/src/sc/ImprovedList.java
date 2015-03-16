package sc;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ImprovedList<E> {
	private final List<E> list;
		
	public ImprovedList (List<E> list) {
		this.list = list;
	}
	
	public synchronized boolean putIfAbsent(E x) {
		boolean contains = list.contains(x);
		
		if (!contains) {
			list.add(x);
		}
		
		return !contains; 
	}

	public synchronized boolean add(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	public synchronized void add(int index, E element) {
		// TODO Auto-generated method stub
		
	}

	public synchronized boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public synchronized boolean addAll(int index, Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public synchronized void clear() {
		// TODO Auto-generated method stub
		
	}

	public synchronized boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	public synchronized boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public synchronized E get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public synchronized int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public synchronized boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public synchronized Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public synchronized int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public synchronized ListIterator<E> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public synchronized ListIterator<E> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public synchronized boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	public synchronized E remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public synchronized boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public synchronized boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public synchronized E set(int index, E element) {
		// TODO Auto-generated method stub
		return null;
	}

	public synchronized int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	public synchronized java.util.List<E> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public synchronized Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	public synchronized <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}
}
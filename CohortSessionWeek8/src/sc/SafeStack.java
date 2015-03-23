package sc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class SafeStack<E>{

	private int size;
	private int counter = -1;
	private List<E> stack;

	public SafeStack(int size) {
		this.size = size;
		stack = Collections.synchronizedList(new ArrayList<>(size));
	}

	public E peek() {
		synchronized(stack){
			return stack.get(counter);
		}
	}

	// This method is made private as the pushing will be done by pushIfNotFull(), which makes use of push()
	private E push(E item) {
		synchronized(stack){
			counter++;
			stack.add(item);
			return item;
		}
	}
	// This method is made private as the popping will be done by popIfNotEmpty(), which makes use of pop()
	private E pop() {
		synchronized(stack){
			E popped = stack.get(counter);
			stack.remove(counter);
			counter--;
			return popped;
		}
	}

	public boolean empty() {
		synchronized(stack){
			if (counter == -1) return true;
			return false;
		}
	}

	public int search(Object o) {
		synchronized(stack){
			int index = -1;
			for (int i = stack.size()-1; i >= 0; i--) {
				if (stack.get(i).equals(o)){
					index = i;
					break;
				}
			}
			if (index == -1) return -1;
			return stack.size() - index;
		}
		
	}

	public E pushIfNotFull(E e){
		synchronized(stack){
			if (size > counter + 1) {
				return push(e);
			}
			return null;
		}
	}

	public E popIfNotEmpty(){
		synchronized(stack){
			if (counter == -1) return null;
			return pop();
		}

	}

	public int getSize(){
		return counter + 1;
	}
	
	// Testing purposes
	public static void main(String[] args) {
		SafeStack<String> stack = new SafeStack<>(10000);
		UserThread1 t1 = new UserThread1(stack);
		UserThread1 t2 = new UserThread1(stack);
		UserThread1 t11 = new UserThread1(stack);
		long timeIn = System.currentTimeMillis();
		t1.start();
		t2.start();
		t11.start();
		try {
			t1.join();
			t2.join();
			t11.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis() - timeIn);
		System.out.println(stack.getSize());
		Stack<String> normalStack = new Stack<>();
		UserThread1Stack t3 = new UserThread1Stack(normalStack);
		UserThread1Stack t4 = new UserThread1Stack(normalStack);
		timeIn = System.currentTimeMillis();
		t3.start();
		t4.start();
		try {
			t3.join();
			t4.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis() - timeIn);
		System.out.println(normalStack.size());
	}
   
}

class UserThread1 extends Thread{
	private SafeStack<String> stack;
	
	public UserThread1(SafeStack s) {
		stack = s;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 2000; i++) {
			stack.pushIfNotFull("Hello");
		}
	}
	
}

class UserThread1Stack extends Thread{
	private Stack<String> stack;
	
	public UserThread1Stack(Stack<String> s) {
		stack = s;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 2000; i++) {
			stack.push("Hello");
		}
	}
	
}


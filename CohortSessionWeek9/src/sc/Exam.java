package sc;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;

public class Exam {
	
	private int numberOfStudents = 44;	
	
	private CyclicBarrier toStart;
	private CountDownLatch toEnd;
	private ArrayList<Student> classList;
	
	private Phaser phaser;
	
	public Exam() {
		//Using barriers
		toStart = new CyclicBarrier(numberOfStudents);	// to wait for all students to be ready
		toEnd = new CountDownLatch(numberOfStudents);
		// Using phaser
//		phaser = new Phaser();
		classList = new ArrayList<>();
		
	}

	public static void main(String[] args) {
		Exam exam = new Exam();
		// Using barriers
		for (int i = 0; i < exam.numberOfStudents; i++) {
			exam.classList.add(new Student(exam.toStart, exam.toEnd));
		}
		System.out.println("Waiting for all students to be ready");
		for (Student student : exam.classList) {
			student.start();
		}
		try {
			exam.toEnd.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("All students completed test");
		/////////////////////////////////////
		
		// Using Phaser
//		exam.phaser.register();
//		for (int i = 0; i < exam.numberOfStudents; i++) {
//			exam.classList.add(new Student(exam.phaser));
//		}
//		
//		System.out.println("Waiting for all students to be ready");
//		for (Student student : exam.classList) {
//			student.start();
//		}
//		try {
//			Thread.sleep(100);	// To ensure that all threads are blocked at the arriveAndAwaitAdvance
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		exam.phaser.arriveAndDeregister(); // to start
//		exam.phaser.register();
//		exam.phaser.arriveAndAwaitAdvance();
//		System.out.println("All students completed test");
	}

}

class Student extends Thread{
	
	private CyclicBarrier toStart;
	private CountDownLatch toEnd;
	private Phaser phaser;
	
	public Student(CyclicBarrier toStart, CountDownLatch toEnd) {
		this.toStart = toStart;
		this.toEnd = toEnd;
	}
	
	public Student(Phaser phaser){
		phaser.register();
		this.phaser = phaser;
	}
	
	@Override
	public void run() {
		
		// Using Barriers
		try {
			System.out.println("Student waiting");
			toStart.await();
			System.out.println("Test started");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 1000000000; i++) {
			//performing tasks
		}
		System.out.println("Finished test");
		toEnd.countDown();
		/////////////////////////////////////////////////////////////
		// Using Phaser
//		System.out.println("Student waiting");
//		phaser.arriveAndAwaitAdvance();
//		System.out.println("Starting test");
//		for (int i = 0; i < 1000000000; i++) {
//			//performing tasks
//		}
//		System.out.println("Done with test");
//		phaser.arrive();
		
		
	}
}
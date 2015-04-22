package sc;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 * Apply SPMD (Single Program, Multiple Data) design pattern for concurrent programming to parallelize the program which 
 * approximates $\pi$ by integrating the following formula $4/(1+x^2 )$. Hint: In the SPMD design pattern, all threads 
 * run the same program, operating on different data.
 */
public class Exercise1 {
	public static double result = 0;
	public static void main(String[] args) throws Exception {
		int NTHREADS = 5;
		ExecutorService exec = Executors.newFixedThreadPool(NTHREADS - 1);
		// todo: complete the program by writing your code below.
		float interval = 1f / (NTHREADS-1);
		ArrayList<Future<Double>> lis = new ArrayList<>();
		float num = 0;
		for (int i = 0; i < NTHREADS - 1; i ++) {
			IntegrateTask task = new IntegrateTask(num, num + interval);
			lis.add(exec.submit(task));	
			num += interval;
		}
		for (Future<Double> future : lis) {
			result += future.get();
		}
		// Comparing the answers, 1 single threaded, 1 multi-threaded
		System.out.println(integrate(0, 1));
		System.out.println(result);
		exec.shutdown();
	}

	public static double f(double x) {
		return 4.0 / (1 + x * x);
	}

	// the following does numerical integration using Trapezoidal rule.
	public static double integrate(double a, double b) {
		int N = 10000; // preciseness parameter
		double h = (b - a) / (N - 1); // step size
		double sum = 1.0 / 2.0 * (f(a) + f(b)); // 1/2 terms

		for (int i = 1; i < N - 1; i++) {
			double x = a + h * i;
			sum += f(x);
		}

		return sum * h;
	}
}

class IntegrateTask implements Callable<Double>{

	double a;
	double b;
	
	public IntegrateTask(double a, double b) {
		this.a = a;
		this.b = b;
	}
	
	@Override
	public Double call() throws Exception {
		double res = Exercise1.integrate(a, b);
//		System.out.println(res);
		return res;
	}
	
}

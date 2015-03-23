package sc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskExample {
	public static void main(String[] args) {
        FutureTask<String> future = new FutureTask<>(new CallableTask());
        //Cancelling code before run
        /*boolean b = future.cancel(true);
        System.out.println("Cancelled="+b);*/
        future.run();
        //Cancelling code after run
        /*boolean b = future.cancel(true);
        System.out.println("Cancelled="+b);*/
        try {
            String result = future.get();
            System.out.println("Result="+result);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("EXCEPTION!!!");
            e.printStackTrace();
        }
    }
}

class CallableTask implements Callable<String>{
	public String call() throws Exception {
		System.out.println("Executing call() !!!");
		/*if(1==1)
			throw new java.lang.Exception("Thrown from call()");*/
        return "success";
    }
}
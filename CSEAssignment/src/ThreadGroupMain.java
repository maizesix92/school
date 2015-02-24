
public class ThreadGroupMain {

	public static void main(String[] args) {
		ThreadGroup alpha = new ThreadGroup("alpha");
		ThreadGroup beta = new ThreadGroup("beta");
		ThreadGroup theta = new ThreadGroup(alpha, "theta");
		ThreadGroup lambda = new ThreadGroup(alpha, "lambda");
		ThreadGroup sigma = new ThreadGroup(beta, "sigma");

		alpha.list();
		
		
		
		
	}

}

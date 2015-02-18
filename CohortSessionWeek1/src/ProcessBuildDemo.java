
import java.io.*;
import java.util.*;

public class ProcessBuildDemo {

	public static void main(String [] args) throws IOException {
		String[] command = {"CMD", "/C", "dir"};
		ProcessBuilder probuilder = new ProcessBuilder( command );

		//You can set up your work directory
		File workDir = new File("C:\\demo\\test");
		probuilder.directory(workDir);// changing directory to demo\test
		boolean res = new File("C:\\demo\\test\\test1").mkdir();// creating the subdirectory "test1" in "test"
		if (res) System.out.println("test1 created");// if action completed print statement
		Process process = probuilder.start();



		// obtain the input stream
		//Read out dir output
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;

		//read the output of the process
		System.out.printf("Output of running %s is:\n",
				Arrays.toString(command));
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}

		//Wait to get exit value
		try {
			int exitValue = process.waitFor();
			System.out.println("\n\nExit Value is " + exitValue);
		} catch (InterruptedException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
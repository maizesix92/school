package lab_1;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Marcus
 * 1000500
 *
 */
public class SimpleShell {
	static Process process;
	static File PathDirectory = new File(System.getProperty("user.dir"));
	static ArrayList<String> history = new ArrayList<String>();
	static ProcessBuilder probuilder;
	static File backUpPathDir;		// to store the previously known working PathDirectory
	static String broughtOverCmd;	// to store the command in history and pass it into processbuilder in the next iteration

	public static void main(String[] args) throws java.io.IOException {
		String commandLine;
		BufferedReader console = new BufferedReader
				(new InputStreamReader(System.in));
		// we break out with ctrl + C
		while (true) {
			
			// read what the user entered
			if (broughtOverCmd == null){	// to test if user has to input command or command has been brought from history
				System.out.print("jsh>");
				commandLine = console.readLine();
			}else{
				commandLine = broughtOverCmd;
				broughtOverCmd = null;
			}
			
			// if the user entered a return, just loop again
			if (commandLine.equals("")) {
				continue;
			}
			
			// Adding typed commands into history
			if (!(commandLine.equals("!!") || commandLine.equals("history"))){
				history.add(commandLine);
			}
			
			try{
				ArrayList<String> command = new ArrayList<String>();
				String[] cmdAry = commandLine.split(" ");
				for (int i = 0; i<cmdAry.length; i++){
					command.add(cmdAry[i]);
				}

				probuilder = new ProcessBuilder(command);
				probuilder.directory(PathDirectory);

				if (command.get(0).equals("cd")){
					if (command.get(1).equals("..")){
						try{
							backUpPathDir = PathDirectory;	// in case cd .. fails, revert back to previous working directory
							PathDirectory = PathDirectory.getParentFile();
							System.out.println(PathDirectory.getAbsolutePath());
						}catch(NullPointerException nul){
							PathDirectory = backUpPathDir;
							System.out.println("Unable to perform action");
							System.out.println(PathDirectory.getAbsolutePath());
							history.remove(history.size()-1);
							continue;
						}
						continue;
					}else{
						backUpPathDir = PathDirectory;
						PathDirectory = new File(PathDirectory.getAbsolutePath() + "\\" + command.get(1));
						if (PathDirectory.exists()){
//							probuilder.directory(PathDirectory);	repeated statement...
							System.out.println(PathDirectory.getAbsolutePath());
							continue;
						}
						else{
							System.out.println("File directory not found");
							PathDirectory = backUpPathDir;
							history.remove(history.size()-1);
							continue;
						}
					}
				}else if (command.get(0).equals("history")){
					for (String s : history){
						System.out.println(s);
					}
					continue;
				}else if(command.get(0).charAt(0) == '!'){
					Pattern pat = Pattern.compile("![0-9]*");
					Matcher m = pat.matcher(command.get(0));
					if (m.matches()){
						int no = Integer.parseInt(command.get(0).substring(1));
						history.remove(history.size()-1);
						try{
							if (history.size() == 0){
								System.out.println("No previously runned command");
								history.remove(history.size()-1);
								continue;
							}
							broughtOverCmd = history.get(no);
							continue;
						}catch(Exception nul){
							history.remove(history.size()-1);
							System.out.println(String.format("No such index exists. Please key in an integer from %d to %d", 0, history.size()-1));							
							continue;
						}
					}else {
						if (history.size() == 0){
							System.out.println("No previously runed command");
							history.remove(history.size()-1);
							continue;
						}else{
							broughtOverCmd = history.get(history.size() - 1);
							continue;
						}
					}
				}
				process = probuilder.start();

			}catch(Exception e){
				System.out.println("Command not recognised. Re-enter command.");
				continue;
			}


			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		}
	}
}
package cselab;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
public class SimpleShellv2 {
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
			// if exit or quit
			if (commandLine.equalsIgnoreCase("exit") | commandLine.equalsIgnoreCase("quit")) {
				System.out.println("See you.");
				System.exit(0);
			}
			

			try{
				ArrayList<String> command = new ArrayList<String>();
				String[] cmdAry = commandLine.split(" ");
				for (int i = 0; i<cmdAry.length; i++){
					command.add(cmdAry[i]);
				}
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
				// TODO: implement code to handle create here
				if (command.get(0).equals("create")){
					String fileName = command.get(1);
					File file = new File(fileName);
					file.createNewFile();
				}
				// TODO: implement code to handle delete here
				else if (command.get(0).equals("delete")){
					String fileName = command.get(1);
					File file = new File(fileName);
					if(file.exists()){
						file.delete();
					}else{
						System.out.println("No such file exists!");
					}
				}
				// TODO: implement code to handle display here
				else if (command.get(0).equals("display")){
					String fileName = command.get(0);
					File file = new File(fileName);
					FileReader fr = new FileReader(file);
					BufferedReader reader = new BufferedReader(fr);
					String input;
					while((input = reader.readLine()) != null) System.out.println(input);
					reader.close();
				}
				// TODO: implement code to handle list here

				// TODO: implement code to handle find here

				// TODO: implement code to handle tree here

				// other commands
				probuilder = new ProcessBuilder(command);
				probuilder.directory(PathDirectory);

				
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
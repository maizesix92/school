package cselab;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileOperation {
	private static File currentDirectory = new File(System.getProperty("user.dir"));
	static File backUpPathDir;		// to store the previously known working PathDirectory
	static String broughtOverCmd;	// to store the command in history and pass it into processbuilder in the next iteration
	static ArrayList<String> history = new ArrayList<String>();


	public static void main(String[] args) throws java.io.IOException {

		String commandLine;

		BufferedReader console = new BufferedReader
				(new InputStreamReader(System.in));

		while (true) {
			// read what the user entered
			System.out.print("jsh>");
			commandLine = console.readLine();

			// clear the space before and after the command line
			commandLine = commandLine.trim();

			// if the user entered a return, just loop again
			if (commandLine.equals("")) {
				continue;
			}
			// if exit or quit
			else if (commandLine.equalsIgnoreCase("exit") | commandLine.equalsIgnoreCase("quit")) {
				System.out.println("See you.");
				System.exit(0);
			}
			try{
				// check the command line, separate the words
				String[] commandStr = commandLine.split(" ");
				ArrayList<String> command = new ArrayList<String>();
				for (int i = 0; i < commandStr.length; i++) {
					command.add(commandStr[i]);
				}

				// handle create here
				if (command.get(0).equals("create")){
					String fileName = command.get(1);
					Java_create(currentDirectory, fileName);
					continue;
				}
				// handle delete here
				else if (command.get(0).equals("delete")){
					String fileName = command.get(1);
					Java_delete(currentDirectory, fileName);
					continue;
				}
				// handle display here
				else if (command.get(0).equals("display")){
					String fileName = command.get(1);
					try{
						Java_cat(currentDirectory, fileName);		
					}catch(FileNotFoundException e){
						System.out.println("This file does not exists!");
					}
					continue;
				}
				// handle list here
				else if (command.get(0).equals("list")){
					String display_method = "";
					String sort_method = "";
					try{
						display_method = command.get(1);
						sort_method = command.get(2);
					}catch(IndexOutOfBoundsException e){

					}
					Java_ls(currentDirectory, display_method, sort_method);
					continue;
				}
				// handle find here
				else if (command.get(0).equals("find")){
					String target = command.get(1);
					Java_find(currentDirectory, target);
					continue;
				}
				// handle tree here
				else if (command.get(0).equals("tree")){
					int depth = 99; // An arbitary large number
					int count = -1;
					String sort_method = "name"; // Default sorting method
					try{
						depth = Integer.parseInt(command.get(1));
						sort_method = command.get(2);
					}catch(IndexOutOfBoundsException e){

					}
					Java_tree(currentDirectory, depth, sort_method, count);
					continue;
				}
				else if (command.get(0).equals("cd")){
					if (command.get(1).equals("..")){
						try{
							backUpPathDir = currentDirectory;	// in case cd .. fails, revert back to previous working directory
							currentDirectory = currentDirectory.getParentFile();
							System.out.println(currentDirectory.getAbsolutePath());
						}catch(NullPointerException nul){
							currentDirectory = backUpPathDir;
							System.out.println("Unable to perform action");
							System.out.println(currentDirectory.getAbsolutePath());
							history.remove(history.size()-1);
							continue;
						}
						continue;
					}else{
						backUpPathDir = currentDirectory;
						currentDirectory = new File(currentDirectory.getAbsolutePath() + "\\" + command.get(1));
						if (currentDirectory.exists()){
							//							probuilder.directory(PathDirectory);	repeated statement...
							System.out.println(currentDirectory.getAbsolutePath());
							continue;
						}
						else{
							System.out.println("File directory not found");
							currentDirectory = backUpPathDir;
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



				// other commands
				ProcessBuilder pBuilder = new ProcessBuilder(command);
				pBuilder.directory(currentDirectory);
				try{
					Process process = pBuilder.start();
					// obtain the input stream
					InputStream is = process.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);

					// read what is returned by the command
					String line;
					while ( (line = br.readLine()) != null)
						System.out.println(line);

					// close BufferedReader
					br.close();
				}
				// catch the IOexception and resume waiting for commands
				catch (IOException ex){
					System.out.println(ex);
					continue;
				}
			}catch(Exception e){
				System.out.println("Command not recognised. Re-enter command.");
				continue;
			}
		}
	}

	/**
	 * Create a file
	 * @param dir - current working directory
	 * @param command - name of the file to be created
	 * @throws IOException 
	 */
	public static void Java_create(File dir, String name) throws IOException {
		File file = new File(name);
		file.createNewFile();
		System.out.println("File created");
	}

	/**
	 * Delete a file
	 * @param dir - current working directory
	 * @param name - name of the file to be deleted
	 */
	public static void Java_delete(File dir, String name) {
		File file = new File(name);
		if(file.exists()){
			file.delete();
			System.out.println("File deleted");
		}else{
			System.out.println("No such file exists!");
		}
	}

	/**
	 * Display the file
	 * @param dir - current working directory
	 * @param name - name of the file to be displayed
	 * @throws FileNotFoundException 
	 */
	public static void Java_cat(File dir, String name) throws FileNotFoundException {
		File file = new File(name);
		FileReader fr = new FileReader(file);
		BufferedReader reader = new BufferedReader(fr);
		String input;
		try {
			while((input = reader.readLine()) != null) System.out.println(input);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * Function to sort the file list
	 * @param list - file list to be sorted
	 * @param sort_method - control the sort type
	 * @return sorted list - the sorted file list
	 */
	private static File[] sortFileList(File[] list, String sort_method) {
		// sort the file list based on sort_method
		// if sort based on name
		if (sort_method.equalsIgnoreCase("name")) {
			Arrays.sort(list, new Comparator<File>() {
				public int compare(File f1, File f2) {
					return (f1.getName()).compareTo(f2.getName());
				}
			});
		}
		else if (sort_method.equalsIgnoreCase("size")) {
			Arrays.sort(list, new Comparator<File>() {
				public int compare(File f1, File f2) {
					return Long.valueOf(f1.length()).compareTo(f2.length());
				}
			});
		}
		else if (sort_method.equalsIgnoreCase("time")) {
			Arrays.sort(list, new Comparator<File>() {
				public int compare(File f1, File f2) {
					return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
				}
			});
		}
		return list;
	}

	/**
	 * List the files under directory
	 * @param dir - current directory
	 * @param function - control the list type
	 * @param sort_method - control the sort type
	 */
	public static void Java_ls(File dir, String display_method, String sort_method) {
		File[] list = dir.listFiles();
		sortFileList(list, sort_method);
		if (display_method.equals("property")){
			for (File file : list) {
				String name = file.getName();
				String size = Long.toString(file.length());
				Date date = new Date(file.lastModified());
				System.out.println(String.format("%s\t%s\tLast Modified: %s", name, size, date.toString()));
			}
		}else{
			for (File file : list) {
				String name = file.getName();				
				System.out.println(String.format("%s", name));
			}
		}
	}

	/**
	 * Find files based on input string
	 * @param dir - current working directory
	 * @param name - input string to find in file's name
	 * @return flag - whether the input string is found in this directory and its subdirectories
	 */
	public static boolean Java_find(File dir, String name) {
		boolean flag = false;
		File[] listOfFiles = dir.listFiles();
		for (File file : listOfFiles) {
			if (file.isDirectory()){
				Java_find(file, name);
			}else{
				if (file.getAbsolutePath().contains(name)){
					System.out.println(file.getAbsolutePath());
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * Print file structure under current directory in a tree structure
	 * @param dir - current working directory
	 * @param depth - maximum sub-level file to be displayed
	 * @param sort_method - control the sort type
	 */
	public static void Java_tree(File dir, int depth, String sort_method, int count) {
		count++;
		if (depth != 0){
			File[] listOfFiles = dir.listFiles();
			sortFileList(listOfFiles, sort_method);
			for (File file : listOfFiles) {
				// Checks all the files in the directory
				if (file.isDirectory()){
					// if the file is a directory, recurse call to go deeper into the directory
					// But print out the name as well
					if (count == 0) System.out.println(file.getName());
					else{
						for (int i = 0; i < count; i++) {
							System.out.print("  ");
						}
						System.out.println("|-" + file.getName());
					}
					Java_tree(file, depth-1, sort_method, count);
				}else{
					// If it is a non-directory, print out name
					// with appropriate indentation
					if (count == 0) System.out.println(file.getName());
					else{
						for (int i = 0; i < count; i++) {
							System.out.print("  ");
						}
						System.out.println("|-" + file.getName());
					}
				}
			}
		}
	}
}
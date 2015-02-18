package filenames;

import java.io.IOException;
import java.util.ArrayList;

public class FilenamesMain {

	public static void main(String[] args) {
		// Adding filenames
		ArrayList<String> fileNames = new ArrayList<>();
		fileNames.add("D:\\SUTD\\Term 5\\Software Construction\\week 3\\file1.txt");
		fileNames.add("D:\\SUTD\\Term 5\\Software Construction\\week 3\\file2.txt");
		fileNames.add("D:\\SUTD\\Term 5\\Software Construction\\week 3\\file3.txt");
		fileNames.add("D:\\SUTD\\Term 5\\Software Construction\\week 3\\file4.txt");
		fileNames.add("D:\\SUTD\\Term 5\\Software Construction\\week 3\\file5.txt");
		fileNames.add("D:\\SUTD\\Term 5\\Software Construction\\week 3\\file6.txt");
		//
		
		for (String string : fileNames) {
			FileLooper loop = new FileLooper(string);
			try {
				System.out.println(String.format("'%s' contains %d line(s)", string, loop.getFileListings()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

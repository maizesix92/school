package filenames;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileLooper {
	
	private String pathDirectory;
	private int lineCount = 0;
	
	public FileLooper(String folderPathName) {
		pathDirectory = folderPathName;
	}
	
	public int getFileListings() throws IOException{
		FileReader fr = new FileReader(pathDirectory);
		BufferedReader br = new BufferedReader(fr);
		while (br.readLine() != null){
			lineCount++;
		}
		return lineCount;
	}
	
	
}

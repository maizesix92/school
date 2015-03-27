package sc;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**When queue was used, there will be an instance whereby the queue can be modified while another thread is still traversing it.
 * Hence, Queue is changed to BlockingQueue and the LInkedList is changed to LinkedBlockingQueue
 * @author User
 *
 */
public class GDesktopProb {
	private final static int N_CONSUMERS = 4;
	
	//it starts here
	public static void startIndexing (File[] roots) {
		BlockingQueue<File> queue = (BlockingQueue<File>) new LinkedBlockingQueue<File>();
		FileFilter filter = new FileFilter() {
			public boolean accept(File file) {return true;}
		};
		
		for (File root : roots) {
			(new FileCrawlerProb(queue, filter, root)).start();;
		}
		
		for (int i = 0; i < N_CONSUMERS; i++) {
			(new IndexerProb(queue)).start();
		}
	}
}

class FileCrawlerProb extends Thread {
	private final BlockingQueue<File> fileQueue; 
	private final FileFilter fileFilter; 	
	private final File root;
	
	FileCrawlerProb (BlockingQueue<File> queue, FileFilter filter, File root) {
		this.fileQueue = queue;
		this.fileFilter = filter;
		this.root = root;
	}
	
	public void run() {
		try {
			crawl(root);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	private void crawl(File root) throws InterruptedException {
		File[] entries = root.listFiles(fileFilter);
		
		if (entries != null) {
			for (File entry : entries) {
				if (entry.isDirectory()) {
					crawl(entry);
				}
				else {
					fileQueue.offer(entry);	
				}
			}
		}
	}
}

class IndexerProb extends Thread {
	private final Queue<File> queue;
	
	
	public IndexerProb (BlockingQueue<File> queue) {
		this.queue = queue;
	}
	
	public void run() {
		while (true) {
			indexFile(queue.poll());
		}
	}

	private void indexFile(File file) {
		// code for analyzing the context of the file is skipped for simplicity	
	}
}
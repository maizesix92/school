package sc;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class GDesktopWithThreadPool {
	private final static int BOUND = 20;
	private final static int N_CONSUMERS = 4;
	
	public static void startIndexing (File[] roots) {
		ConcurrentLinkedQueue<File> queue = new ConcurrentLinkedQueue<File>();
		FileFilter filter = new FileFilter() {
			public boolean accept(File file) {return true;}
		};
		
		for (File root : roots) {
			(new FileCrawler(queue, filter, root)).start();;
		}
		
		for (int i = 0; i < N_CONSUMERS; i++) {
			(new Indexer(queue)).start();
		}
	}
}

class FileCrawler extends Thread {
	private final ConcurrentLinkedQueue<File> fileQueue; 
	private final FileFilter fileFilter; 	
	private final File root;
	private final ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(10); 
	
	FileCrawler (ConcurrentLinkedQueue<File> queue, FileFilter filter, File root) {
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
					Runnable task = new Runnable() {						
						@Override
						public void run() {
							try {
								crawl(entry);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					};
					exec.execute(task);
				}
				else {
					fileQueue.add(entry);	
				}
			}
		}
	}
}

class Indexer extends Thread {
	private final ConcurrentLinkedQueue<File> queue;
	
	public Indexer (ConcurrentLinkedQueue<File> queue) {
		this.queue = queue;
	}
	
	public void run() {
//		try {
			while (true) {
				indexFile(queue.remove());
			}
//		} catch (InterruptedException e) {
//			Thread.currentThread().interrupt();
//		}
	}

	private void indexFile(File file) {
		// TODO Auto-generated method stub	
	}
}
package sc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class SlidingGameParallel {
	//The following models the class sliding game.
	//The following is the board setting.
	// 0 	1 	2 	
	// 3    4 	5 	
	// 6 	7   8 	
	private static volatile Set<String> seen = new HashSet<String>();
	public static void main (String[] args) throws Exception {
		//		int[] initialBoardConfig = new int[] {3,5,6,0,2,7,8,4,1}; 
		//		int[] initialBoardConfig = new int[] {5,0,6,4,7,3,2,8,1}; 
		//		int[] initialBoardConfig = new int[] {2,3,8,4,6,0,1,5,7}; 
		int[] initialBoardConfig = new int[] {2,1,5,3,6,0,7,8,4}; 
		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(10);
		PuzzleNode init = new PuzzleNode(initialBoardConfig, null);
		List<int[]> results = new LinkedList<>();
		BFSSearchParallel(exec, init, results);

		while(!exec.isShutdown()){
		}

		if (results.isEmpty()) {
			System.out.println ("No solution");
		}
		else {
			System.out.println ("Solution Found");
			for (int[] i: results) {
				System.out.println (toString(i));    			
			}
		}
	}


	public static void BFSSearchParallel(ScheduledThreadPoolExecutor exec, PuzzleNode node, List<int[]> results) throws InterruptedException, ExecutionException { 	
		// result will be in a List<int[]>, which contains the trace of the configs to get the answer
		// Stops thread
		if (exec.isShutdown()){
			return;
		}
		// Gets the current node's config
		String stringConfig = toString(node.config);
		//		System.out.println("exploring " + stringConfig);
		boolean check;
		// Need to check if this config has been explored before
		// If false, check if it is the correct config
		// else if true, skip node
		synchronized(seen){
			check = seen.contains(stringConfig);
		}
		if (!check) {
			// Add this config to the set of explored configs
			synchronized(seen){
				seen.add(stringConfig);
			}
			if (isGoal(node.config)){
				// Assign results to this List<int[]> and stop all other threads
				results.addAll(node.getTrace());
				exec.shutdown();
			}else{
				// Find the children nodes
				List<int[]> children = nextPositions(node.config);
				if (children.isEmpty()){
					// If there are no more possible configs
					return;
				}
				// children now contains the list of all possible next configurations
				// Need to start a thread for each possible one
				for (int[] is : children) {
					// Create a new PuzzleNode with config is, linking the current node's pointer to this new node
					PuzzleNode childNode = new PuzzleNode(is, node);
					// Start the thread that will now treat this childNode as a root node and continue the search from there
					exec.execute(new Runnable() {

						@Override
						public void run() {
							try {
								BFSSearchParallel(exec, childNode, results);
							} catch (InterruptedException | ExecutionException e) {
								e.printStackTrace();
							}

						}
					});
					// Now I need to find a way to get the results
					// 1) pass the List<int[]> results down to the child to store the results if found (passing the reference) or
					// 2) return a List<int[]>
				}
			}
		}
	}

	private static boolean isGoal (int[] boardConfig) {
		return boardConfig[0] == 1 && boardConfig[1] == 2 && boardConfig[2] == 3 && boardConfig[3] == 4 &&
				boardConfig[4] == 5 && boardConfig[5] == 6 && boardConfig[6] == 7 && boardConfig[7] == 8 && boardConfig[8] == 0;
	}

	private static List<int[]> nextPositions (int[] boardConfig) {
		int emptySlot = -1;

		for (int i = 0; i < boardConfig.length; i++) {
			if (boardConfig[i] == 0) {
				emptySlot = i;
				break;
			}
		}

		List<int[]> toReturn = new ArrayList<int[]>();

		//the empty slot goes right
		if (emptySlot != 2 && emptySlot != 5 && emptySlot != 8) {
			int[] newConfig = boardConfig.clone(); 
			newConfig[emptySlot]= newConfig[emptySlot+1];
			newConfig[emptySlot+1]=0;
			toReturn.add(newConfig);
		}
		//the empty slot goes left    	
		if (emptySlot != 0 && emptySlot !=3 && emptySlot != 6) {
			int[] newConfig = boardConfig.clone();     		
			newConfig[emptySlot]=newConfig[emptySlot-1];
			newConfig[emptySlot-1]=0;
			toReturn.add(newConfig);
		}
		//the empty slot goes down   
		if (emptySlot != 6 && emptySlot != 7 && emptySlot != 8) {
			int[] newConfig = boardConfig.clone();     		    		
			newConfig[emptySlot]=newConfig[emptySlot+3];
			newConfig[emptySlot+3]=0;
			toReturn.add(newConfig);
		}
		//the empty slot goes up 
		if (emptySlot != 0 && emptySlot != 1 && emptySlot != 2) {
			int[] newConfig = boardConfig.clone();     		    		
			newConfig[emptySlot] = newConfig[emptySlot-3];
			newConfig[emptySlot-3] = 0;
			toReturn.add(newConfig);
		}

		return toReturn;
	}

	private static String toString(int[] config) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < config.length; i++) {
			sb.append(config[i]);
		}

		return sb.toString();
	}
}


class PuzzleNode {
	final int[] config;
	final PuzzleNode prev;

	PuzzleNode(int[] config, PuzzleNode prev) {
		this.config = config;
		this.prev = prev;
	}

	List<int[]> getTrace() {
		List<int[]> solution = new LinkedList<int[]> ();
		for (PuzzleNode n = this; n.prev != null; n = n.prev) {
			solution.add(0, n.config);
		}

		return solution;
	}
}
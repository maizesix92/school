

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import javax.swing.text.StyledEditorKit.BoldAction;

public class ThreadLister {

	ArrayList<ThreadInstance> threadInstanceList = new ArrayList<ThreadInstance>();
	ArrayList<Node<ThreadGroup>> TGNodeList = new ArrayList<Node<ThreadGroup>>();

	// threadInstances hold all active threads
	public static void main(String[] args) {
		ThreadGroup alpha = new ThreadGroup("alpha"); 
		ThreadGroup beta = new ThreadGroup("beta"); 
		ThreadGroup theta = new ThreadGroup(alpha, "theta");
		ThreadLister tl = new ThreadLister();

//		 Uncomment the following line to show Part A
//		 tl.printPartA();

//		 Uncomment the following line to show Part B
//		 tl.showTableGUI();

//		 Uncomment the following line to show Part C
//		tl.searchWithRecurse();

	}

	public ThreadLister() {
		constructGraph();
	}

	public void searchWithRecurse() {
		while (true) {
			System.out
					.println("Please enter full or partial name or id of a thread");
			Scanner s = new Scanner(System.in);
			String threadName = s.nextLine();
			boolean found = false;
			for (int i = 0; i < threadInstanceList.size(); i++) {
				ThreadInstance ti = threadInstanceList.get(i);

				if (ti.getName().indexOf(threadName) != -1
						|| ti.getId().toString().indexOf(threadName) != -1) {
					found = true;
					// find the thread

					String recRepr = "";
					Thread t = ti.getT();
					ThreadGroup tg = t.getThreadGroup();
					recRepr += tg.getName();
					while (tg.getParent() != null) {
						tg = tg.getParent();
						recRepr = tg.getName() + " => " + recRepr;
					}

					System.out.println(String.format(
							"Your thread: %s:%s:%s:%s Priority:%s",
							ti.getName(), ti.getId(), ti.getState(),
							ti.isDaemon(), ti.getPriority()));
					System.out.println(recRepr);
					System.out.println();
				}

			}
			if (found == false) {
				System.out.println("Thread not found.");
			}
		}
	}

	public void printPartA() {
		for (Iterator<Node<ThreadGroup>> iterator = TGNodeList.iterator(); iterator
				.hasNext();) {
			Node<ThreadGroup> node = iterator.next();
			String name = node.getE().getName();
			String parentName;
			if (node.getParent() != null) {
				parentName = node.getParent().getE().getName();
			} else {
				parentName = "null";
			}

			int priority = node.getE().getMaxPriority();
			System.out.println(String.format(
					"Group: %s, Parent: %s, Priority:%s", name, parentName,
					priority));
			for (ThreadInstance ti : getThreadsInTheGroup(node.getE())) {
				// for each threadGroup, get threads in it, but not in its
				// subgroup
				System.out.println(ti);
			}
		}

	}

	public void showTableGUI() {
		// show table GUI

		Object[][] data = new Object[threadInstanceList.size()][6];
		for (int i = 0; i < threadInstanceList.size(); i++) {
			ThreadInstance ti = threadInstanceList.get(i);
			data[i][0] = ti.getGroupname();
			data[i][1] = ti.getName();
			data[i][2] = ti.getId();
			data[i][3] = ti.getState();
			data[i][4] = ti.isDaemon();
			data[i][5] = ti.getPriority();
		}

		new ThreadListerTable(data).run();
	}

	public void constructGraph() {
		ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
		ThreadGroup parentGroup;
		while ((parentGroup = rootGroup.getParent()) != null) {
			rootGroup = parentGroup;
		}

		Node<ThreadGroup> rootNode = new Node<ThreadGroup>(rootGroup);
		// get Root Node: "system"

		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		// get all threads in one go

		Set<ThreadGroup> threadGroupSet = new HashSet<ThreadGroup>();

		for (Iterator<Thread> iterator = threadSet.iterator(); iterator
				.hasNext();) {
			Thread thread = iterator.next();

			threadGroupSet.add(thread.getThreadGroup());
			// get all the threadGroups from threadSet, put them into
			// threadGroupSet
		}

		// turn threadGroups into a list of Node<ThreadGroup>
		TGNodeList.add(rootNode);
		int currentNodeIndex = 0;

		while (TGNodeList.size() != threadGroupSet.size()) {
			// keep turning TG into TGNode
			Node<ThreadGroup> parentNode = TGNodeList.get(currentNodeIndex);

			ThreadGroup parentTG = parentNode.getE();
			// System.out.println("Current ParentTG: " + parentTG.getName());

			for (Iterator<ThreadGroup> iterator = threadGroupSet.iterator(); iterator
					.hasNext();) {
				ThreadGroup tg = iterator.next();
				// System.out.println(tg.getName());

				if (tg.getParent() != null) {
					if (tg.getParent().getName().equals(parentTG.getName())) {
						// traverse threadGroup set, find the child of current
						// parentNode

						Node<ThreadGroup> newNode = new Node<ThreadGroup>(tg);
						newNode.setParent(parentNode);

						if (!TGNodeList.contains(newNode)) {
							TGNodeList.add(newNode);
							// add to TGNodeList
						}
					}
				}
			}
			currentNodeIndex++;
		}
		for (Iterator<Node<ThreadGroup>> iterator = TGNodeList.iterator(); iterator
				.hasNext();) {
			Node<ThreadGroup> node = iterator.next();

			for (ThreadInstance ti : getThreadsInTheGroup(node.getE())) {
				// for each threadGroup, get threads in it, but not in its
				// subgroup
				this.threadInstanceList.add(ti);
			}
		}

	}

	public static ArrayList<ThreadInstance> getThreadsInTheGroup(ThreadGroup tg) {
		// return an arraylist of ThreadInstance for threads in the given
		// threadGroup

		ArrayList<ThreadInstance> al = new ArrayList<ThreadInstance>();
		Thread[] threads = new Thread[tg.activeCount()];
		tg.enumerate(threads, false);
		for (int i = 0; i < threads.length; i++) {
			if (threads[i] != null) {
				Thread thread = threads[i];
				String groupname = thread.getThreadGroup().getName();
				Long identifier = thread.getId();
				String name = thread.getName();
				State state = thread.getState();
				boolean isDaemon = thread.isDaemon();
				int priority = thread.getPriority();
				al.add(new ThreadInstance(thread, groupname, name, identifier,
						state, isDaemon, priority));
			}
		}

		return al;

	}

}

class ThreadInstance {
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public boolean isDaemon() {
		return isDaemon;
	}

	public void setDaemon(boolean isDaemon) {
		this.isDaemon = isDaemon;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Thread getT() {
		return t;
	}

	public void setT(Thread t) {
		this.t = t;
	}

	Thread t;
	Long id;
	String name;
	String groupname;
	State state;
	boolean isDaemon;
	int priority;

	public ThreadInstance(Thread t, String groupname, String name, Long id,
			State state, boolean isDaemon, int priority) {
		this.t = t;
		this.id = id;
		this.name = name;
		this.groupname = groupname;
		this.state = state;
		this.isDaemon = isDaemon;
		this.priority = priority;
	}

	public String toString() {
		return String.format("	%s:%s:%s:%s Priority:%s", name, id, state,
				isDaemon, priority);
	}

}

class Node<E> {
	Node<E> parent;
	E e;
	ArrayList<Node<E>> childs;
	boolean isLeaf;
	boolean isBlack;

	public Node(E e) {
		this.e = e;
		this.parent = null;
		this.childs = new ArrayList<Node<E>>();
		this.isLeaf = true;
		this.isBlack = false;
	}

	public E getE() {
		return this.e;
	}

	public void addChild(Node<E> child) {
		if (!this.childs.contains(child)) {
			this.childs.add(child);
			child.setParent(this);
			this.isLeaf = false;
		}
	}

	public Node<E> getParent() {
		return parent;
	}

	public void setParent(Node<E> parent) {
		this.parent = parent;
		this.parent.childs.add(this);
	}

	public boolean hasChild() {
		return this.childs.size() != 0 ? true : false;
	}

	public void makeBlack() {
		this.isBlack = true;
	}

}
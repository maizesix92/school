

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ThreadListerTable extends JPanel implements Runnable{
	
	Object[][] data;
	public void run() {
		createAndShowGUI(this.data);
	}

	public ThreadListerTable(Object[][] data){

        String[] columnNames = {"Group Name",
                                "Name",
                                "Id",
                                "State",
                                "isDaemon",
                                "Priority"};
        
        
        this.data = data;
        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
//		Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
 
//		Add the scroll pane to this panel.
        add(scrollPane);
        
        
	}
	private static void createAndShowGUI(Object[][] data) {
        //Create and set up the window.
        JFrame frame = new JFrame("SimpleTableDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        ThreadListerTable newContentPane = new ThreadListerTable(data);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}

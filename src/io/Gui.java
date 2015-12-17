package io;

import javax.swing.JFrame;
/**
 * Holds the main panel.  probably could have added main panel here but whatever
 * @author wechtera
 *
 */
public class Gui extends JFrame {
	
	public Gui() {
		
		setTitle("Movie_Manager");
		setSize(1000,600); // default size is 0,0
		setLocation(100,100); // default is 0,0 (top left corner)
		setResizable(false);
		add(new MainPanel());
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}

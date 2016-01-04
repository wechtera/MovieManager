package io;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import util.AddMovie;
import core.Main;
/**
 * Pop up window to add movie
 * @author wechtera
 *
 */
public class AddMovieOption extends JFrame {
	JPanel jp;
	JTextField tf;
	JTextField nameField;
	JButton browse;
	JButton ok;
	JFileChooser jf;
	JFrame f;
	MainPanel mp;
	
	public AddMovieOption(MainPanel mpa) {
		f=this;
		mp = mpa;
		jp = new JPanel();
		//textfield with random location
		tf = new JTextField(new File(".").getAbsolutePath());
		nameField = new JTextField("         Movie Title         ");
		browse = new JButton("Browse");
		ok = new JButton("OK...");

		jp.add(nameField);
		jp.add(tf);
		jp.add(browse);
		jp.add(ok);

		
		//choose some movie
		browse.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				jf = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter("Movie Files", "mpg", "m4v", "mp4", "wmv", "mkv", "avi");
			    jf.setFileFilter(filter);
			    int returnVal = jf.showOpenDialog(getParent());
			    if(returnVal == JFileChooser.APPROVE_OPTION)
			        tf.setText(jf.getSelectedFile().getAbsolutePath());
			}	
		});
		//TODO: Make this a seperate class??
		//pump out file to be added and worked on
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList <String> arr = new ArrayList<String>();
				arr.add(nameField.getText());
				AddMovie.init(tf.getText(), arr, false);
				f.dispose();
				mp.updateDML(mp.getSrbState());
			}
		});
		//Add and show
		add(jp);
		setSize(600,200);
		setLocation(200,200);
		setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}

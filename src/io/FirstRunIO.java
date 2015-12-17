package io;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import core.FirstRun;
import util.AddMovie;

public class FirstRunIO extends JFrame {
	/**
	 * Make Window to choose database location
	 * TODO: Make window stick to very front / top
	 */
	JFrame f;
	JPanel jp;
	JLabel jl;
	JButton ok;
	JButton browse;
	JTextField tf;
	JFileChooser dc;
	
	public FirstRunIO() {
		
		f = this;  
		jp = new JPanel();
		jl = new JLabel("First run:  Select home for media directory:  ");
		ok = new JButton("OK");
		browse = new JButton("Browse");
		tf = new JTextField(new File(".").getAbsolutePath());
		jp.add(jl);
		jp.add(tf);
		jp.add(browse);
		jp.add(ok);
		
		//Choose a dir, any dir
		browse.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dc = new JFileChooser();
			    dc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    int returnVal = dc.showOpenDialog(getParent());
			    if(returnVal == JFileChooser.APPROVE_OPTION)
			        tf.setText(dc.getSelectedFile().getAbsolutePath());
			}	
		});
		
		//select directory
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FirstRun.dBSetup(tf.getText());
				f.dispose();
			}
		});
		
		
		add(jp);
		setVisible(true);
		setSize(400,400);
		setLocation(200,200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
	}
}

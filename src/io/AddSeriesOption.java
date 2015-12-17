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
/**
 * Creates a window prompting for television episode information
 * Should probaly be cleaned up
 * @author wechtera
 *
 */
public class AddSeriesOption extends JFrame {
	
	JFrame f;
	JPanel jp;
	MainPanel mp;
	JTextField tf;
	JTextField seriesName;
	JTextField seasonNum;
	JTextField episodeNum;
	JButton browse;
	JButton ok;
	JFileChooser jf;

	public AddSeriesOption(final MainPanel mp) {
		//Semi important? TODO: check importance see if I can consolidate
		f=this;
		this.mp = mp;
		jp = new JPanel();
		
		//Input and ux stuff
		tf = new JTextField(new File(".").getAbsolutePath());
		seriesName = new JTextField("         Series Name         ");
		seasonNum = new JTextField("    #    ");
		episodeNum = new JTextField("    #    ");
		browse = new JButton("Browse");
		ok = new JButton("OK...");
		
		//add to frame, ordering doesnt matter really
		jp.add(seriesName);
		jp.add(seasonNum);
		jp.add(episodeNum);
		jp.add(tf);
		jp.add(browse);
		jp.add(ok);
		
		//File Browser
		browse.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				jf = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter("Movie Files", "mpg", "m4v", "mp4", "wmv");
			    jf.setFileFilter(filter);
			    int returnVal = jf.showOpenDialog(getParent());
			    if(returnVal == JFileChooser.APPROVE_OPTION)
			        tf.setText(jf.getSelectedFile().getAbsolutePath());
			}	
		});
		
		//Select a movie
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> arr = new ArrayList<String>();
				arr.add(seriesName.getText());
				arr.add(seasonNum.getText());
				arr.add(episodeNum.getText());
				AddMovie.init(tf.getText(), arr, true);
				f.dispose();
				mp.fullDMLUpdate(mp.getSrbState());
			}
		});
		
		//Add and show ui/ux
		add(jp);
		setSize(600,200);
		setLocation(200,200);
		setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

}

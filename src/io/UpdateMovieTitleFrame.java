package io;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.AddMovie;

public class UpdateMovieTitleFrame extends JFrame {
	JPanel jp;
	JTextField title;
	JTextField episode;
	JTextField season;
	JButton jb;
	int id;
	boolean isSeries;
	MainPanel mp;
	
	public UpdateMovieTitleFrame(int ids, boolean isSerie, MainPanel mpa) {
		jp = new JPanel();
		title = new JTextField("     Movie Title    ");
		episode = new JTextField("##");
		season = new JTextField("##");
		jb = new JButton("OK");
		id = ids;
		isSeries = isSerie;
		mp = mpa;
		
		//TODO: Sizing and stuff here and jlabels
		
		
		jp.add(title);
		if(isSeries) {
			jp.add(season);
			jp.add(episode);
		}
		jp.add(jb);
		
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> info = new ArrayList<String>();
				info.add(title.getText());
				if(isSeries) {
					info.add(season.getText());
					info.add(episode.getText());
				}
				AddMovie.correctInfo(info, id, isSeries);
				dispose();
				mp.updateDML(mp.getSrbState());
			}
		});
		
		add(jp);
		setSize(600,200);
		setLocation(200,200);
		setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}

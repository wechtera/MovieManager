package io;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import io.MainPanel.AddMListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;

import util.DatabaseBaseFnc;
import util.FileComs;
import util.SearchEvent;

public class SearchBar extends JPanel {
	
	JTextField searchField;
	JButton search;
	JButton clear;
	JRadioButton title;
	JRadioButton genre;
	JRadioButton plot;
	JRadioButton series;
	MainPanel mp;
	ButtonGroup group;
	
	
	public SearchBar(MainPanel mp) {
		this.mp = mp;
		
		setLayout(null);
		setSize(500, 250);
		
		searchField = new JTextField();
		search = new JButton("Search");
		clear = new JButton("Clear");
		clear.addActionListener(new ClearSearch());
		search.addActionListener(new UpdateSearch());
		
		group = new ButtonGroup();
		title = new JRadioButton("Title");
		title.setActionCommand("TITLE");
		genre = new JRadioButton("Genre");
		genre.setActionCommand("GENRE");
		plot = new JRadioButton("Plot");
		plot.setActionCommand("PLOT");
		series = new JRadioButton("Series");
		series.setActionCommand("SERIES_INFO");
		title.setSelected(true);
		
		group.add(title);
		group.add(genre);
		group.add(plot);
		group.add(series);
		
		searchField.setBounds(5,5,250,35);
		search.setBounds(265,5, 80,35);
		clear.setBounds(350,5, 80, 35);
		title.setBounds(5, 45, 75, 50);
		genre.setBounds(85, 45, 75, 50);
		plot.setBounds(165, 45, 75, 50);
		series.setBounds(240, 45, 75, 50);		
		
		add(searchField);
		add(search);
		add(clear);
		add(title);
		add(genre);
		add(plot);
		add(series);	
	}
	
	
	/**
	 * Revert list to normal
	 * @author wechtera
	 *
	 */
	class ClearSearch implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			mp.updateDML(mp.getSrbState());
		}	
	}
	
	/**
	 * Searches and updates list
	 * @author wechtera
	 *
	 */
	class UpdateSearch implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(group.getSelection().getActionCommand());
			
			SearchEvent.search(group.getSelection().getActionCommand(), searchField.getText(), mp);
			searchField.setText("");
			
		}	
	}

}

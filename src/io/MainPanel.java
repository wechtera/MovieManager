package io;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import core.Main;
import util.AddMovie;
import util.DatabaseBaseFnc;
import util.FileComs;
import util.Title;

public class MainPanel extends JPanel {
	
	MainPanel mp;
	InfoPanel ip;
	private JList<String> jl;
	DefaultListModel<String> dlm;
	HashMap<Integer, Integer> ids;
	AddMovieButton amb;
	AddSeriesButton asb;
	DeleteFromDatabaseButton rmb;
	CleanLocalButton clb;
	CorrectInfoButton cib;
	JRadioButton mrb;
	JRadioButton srb;
	ButtonGroup radioGroup;
	MoveMovie mmb;
	SearchBar sb;
	static int idSelected;
	
	public MainPanel() {
		mp= this;
		ip = new InfoPanel();
		setLayout(null);
		setSize(1000, 600);
		
		//add movie button
		amb = new AddMovieButton();
		amb.addActionListener(new AddMListener());
		//add series button
		asb = new AddSeriesButton();
		asb.addActionListener(new AddSListener());
		//remove movie
		rmb = new DeleteFromDatabaseButton();
		rmb.addActionListener(new DeleteListener());
		//move movie
		mmb = new MoveMovie();
		mmb.addActionListener(new MoveListener());
		//clean local
		clb = new CleanLocalButton();
		clb.addActionListener(new CleanLocalListener());
		//Correct info
		cib = new CorrectInfoButton();
		cib.addActionListener(new CorrectInfoAct());
		
		//RadioButtonMovieSeries
		radioGroup = new ButtonGroup();
		mrb = new JRadioButton("Movies");
		mrb.setActionCommand("Movie");
		mrb.setSelected(true);
		srb = new JRadioButton("Series");
		srb.setActionCommand("Series");
		mrb.addActionListener(new SMListener());
		srb.addActionListener(new SMListener());
		radioGroup.add(mrb);
		radioGroup.add(srb);
		
		
		//getTitle list List
		//TODO: Move to a seperate thing, maybe call full dml update
		idSelected = 1;
		
		dlm = new DefaultListModel<String>();
		ids = new HashMap<Integer, Integer>();
		
		// JList information
		jl = new JList<String>();
		updateDML(false);
		jl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jl.setLayoutOrientation(JList.VERTICAL);
		jl.setSelectedIndex(0);
		
		//Add search Bar
		sb = new SearchBar(this);
		
		jl.addListSelectionListener(new SelectionChangedListener());
		
		
		//Positions/sizes
		amb.setBounds(10,10, 100, 50);
		asb.setBounds(120, 10, 100, 50);
		rmb.setBounds(230, 10, 100, 50);
		mmb.setBounds(340, 10, 100, 50);
		clb.setBounds(450, 10, 100, 50);
		sb.setBounds(560, 10, 500, 200);
		jl.setBounds(10, 70, 320, 460);
		mrb.setBounds(10, 525, 100, 50);
		srb.setBounds(120, 525, 100, 50);
		cib.setBounds(235, 535, 100, 30);
		ip.setBounds(340, 100, 660, 500);
		
		
		//add to panel...
		add(amb);
		add(asb);
		add(rmb);
		add(mmb);
		add(clb);
		add(jl);
		add(mrb);
		add(srb);
		add(cib);
		add(ip);
		add(sb);
	}
	


	/**
	 * Updates our Default lost model completely form scratch
	 * May slow down as we add more entries? TODO: STRESS TEST and alphabetize
	 * @param isSeries
	 */
	public void updateDML(boolean isSeries) {
		
		ArrayList<Title> titles = DatabaseBaseFnc.getTitles(isSeries);
		
		dlm = new DefaultListModel<String>();
		ids.clear();
		int i =0;

		for(int j = 0; j < titles.size(); j++) { //Values added at 1 less than database id!!!
			dlm.add(i,titles.get(j).getDispName());
			ids.put(i, titles.get(j).getId());
			i++;
		}
		jl.setModel(dlm);
	}

	/**
	 * Updates list for search titles
	 * @param titles
	 */
	public void setSearchModel(ArrayList<Title> titles) {
		dlm = new DefaultListModel<String>();
		ids.clear();
		int i =0;

		for(int j = 0; j < titles.size(); j++) { //Values added at 1 less than database id!!!
			dlm.add(i,titles.get(j).getDispName());
			ids.put(i, titles.get(j).getId());
			i++;
		}
		jl.setModel(dlm);	
	}


	/**
	 * Gets and returns if series radio button
	 * is pushed
	 * @return - is Series List?
	 */
	public boolean getSrbState() {
		return srb.isSelected();
	}
	
	/**
	 * Delete entry button listener
	 * @author wechtera
	 */
	class DeleteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean isSeries = srb.isSelected();
			FileComs.removeFile(ids.get(jl.getSelectedIndex()));
			System.out.println("Hello There");
			DatabaseBaseFnc.removeMovieFromDB(ids.get(jl.getSelectedIndex()));
			updateDML(isSeries);
		}
	}
	
	/**
	 * Add movie button listener
	 * @author wechtera
	 */
	class AddMListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Main.addNewMovie(mp);	
		}
	}
	
	/**
	 * Add series button listener
	 * @author wechtera
	 */
	class AddSListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Main.addNewSeries(mp);	
		}
	}
	
	class CleanLocalListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FileComs.cleanLocal();
		}
	}
	
	/**
	 * Series vs movie action listener, switches back and forth
	 * between series list and movie list
	 * @author wechtera
	 */
	class SMListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Movie"))
				updateDML(false);
			else
				updateDML(true);	
		}
	}
	
	/**
	 * Moving file button listener
	 * @author wechtera
	 *
	 */
	class MoveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String path = DatabaseBaseFnc.getPath(ids.get(jl.getSelectedIndex()));
			FileComs.copyMovie(path);
		}	
	}
	
	class CorrectInfoAct implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new UpdateMovieTitleFrame(ids.get(jl.getSelectedIndex()), mp.getSrbState(), mp);
		}
		
	}
	
	/**
	 * This is the JList listener
	 * sets current id
	 * @author wechtera
	 *
	 */
	class SelectionChangedListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			idSelected = jl.getSelectedIndex();
			try {
				ip.updateDisplay(ids.get(jl.getSelectedIndex()));
			} catch(NullPointerException e1) {
				//catches a silly exception of null pointing for sanity's sake
				System.out.println("shhhhh");
			}
		}
	}
	
	
}

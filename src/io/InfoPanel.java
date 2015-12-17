package io;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import util.DatabaseBaseFnc;

public class InfoPanel extends JPanel {
	private final JLabel titleL = new JLabel("Title: ");
	private final JLabel genreL = new JLabel("Genres: ");
	private final JLabel releaseL = new JLabel("Release Date: ");
	private final JLabel runtimeL = new JLabel("Runtime: ");
	private final JLabel ratingL = new JLabel("Rating: ");
	private final JLabel plotL = new JLabel("Plot Summary: ");
	
	private JLabel titleF;
	private JLabel genreF;
	private JLabel releaseF;
	private JLabel runtimeF;
	private JLabel ratingF;
	private JTextArea plotA;
	
	public InfoPanel() {
		//creation and sizing
		setSize(660, 500);
		setLayout(null);
		//this.setBackground(Color.RED);  //to be removed!
		//instntiate
		titleF = new JLabel();
		genreF = new JLabel();
		releaseF = new JLabel();
		runtimeF = new JLabel();
		ratingF = new JLabel();
		plotA = new JTextArea();
		
		
		//adding labels and positioning for peace of mind

		titleL.setBounds(10,50, 50, 10);
		titleF.setBounds(70, 40, 300, 30);
		
		releaseL.setBounds(360, 50, 100, 10);
		releaseF.setBounds(450, 40, 300, 30);
		
		genreL.setBounds(10, 100, 60, 10);
		genreF.setBounds(80, 90, 300, 30);
		
		runtimeL.setBounds(360, 100, 100, 10);
		runtimeF.setBounds(450, 90, 300, 30);
		
		ratingL.setBounds(360, 150, 100, 20);
		ratingF.setBounds(450, 145, 300, 30);
		
		plotL.setBounds(10, 190, 100, 20);
		plotA.setBounds(30, 220, 560, 220);
		plotA.setBorder(null);
		plotA.setOpaque(false);
		plotA.setEditable(false);
		plotA.setLineWrap(true);
			
		add(titleL);
		add(titleF);
		add(genreL);
		add(genreF);
		add(releaseL);
		add(releaseF);
		add(runtimeL);
		add(runtimeF);
		add(ratingL);
		add(ratingF);
		add(plotL);
		add(plotA);	
	}
	
	public void updateDisplay(int id) {
		ArrayList<String> arr = DatabaseBaseFnc.getRow(id);
		titleF.setText(arr.get(0));
		releaseF.setText(arr.get(1));
		genreF.setText(arr.get(2));
		runtimeF.setText(arr.get(3));
		ratingF.setText(arr.get(4));
		plotA.setText(arr.get(5));
	}

}

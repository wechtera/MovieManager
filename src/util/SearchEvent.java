package util;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import io.MainPanel;

public class SearchEvent {

	public static void search(String actionCommand, String text, MainPanel mp) {
		//if string text empty, no search boundaries, display all
		if(text.equals(""))
			mp.updateDML(mp.getSrbState());
		else {
			if(actionCommand.equals("Series"))
				text = text.replace(" ", "-");
			ArrayList<Title> titles = DatabaseBaseFnc.getTitlesSearch(mp.getSrbState(), actionCommand, text);
			mp.setSearchModel(titles);
		}
	}
}

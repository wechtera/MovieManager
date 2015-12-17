package util;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

/**
 * Movie path data passed in through init
 * Sets up movie storage 
 * @author wechtera
 *
 */
public class AddMovie {
	
	/**
	 * Controller, do checks, do submits, ask for data, etc.
	 * @param moviePath
	 */
	public static void init(String moviePath, ArrayList<String> movieInfo, boolean isSeries) {
		//check valid file extension
		
		System.out.println("IS SERIES:  " + isSeries);

		//TODO:  This should probably be put into methods for readibility sake
		if(checkFileExtension(moviePath)) {
			/** It's a movie, not a television episode **/
			if(!isSeries) {
				JOptionPane.showMessageDialog(null, "Successful Transport!");  //TODO: SET ICONS
				
				//grab file name and path prepare to move to other disk
				String fileloc = "";
				fileloc = FileComs.changeMoveMovie(moviePath, movieInfo.get(0));
				
				//pull data from OMDb JSON format
				ArrayList<String> arr = OMDbLookup.getMovie(movieInfo.get(0));
				
				//Put in db.  if no info returned from omdb, do what needs to be done
				//Add additional data as needed for series fields
				arr.add(fileloc);
				arr.add("x");
				arr.add(arr.get(0));
				arr.add("false");
				
				//If no database information found (Incorrect title given)
				if(arr.get(0).equals("")) {
					JOptionPane.showMessageDialog(null, "File move, incorrect information, added to database under your name");
					ArrayList<String> arrF = new ArrayList<String>(Arrays.asList(movieInfo.get(0), "x","x","x","x","x",fileloc,"x",movieInfo.get(0),"false")); //dummy listwith jsut name
					arrF.add(fileloc);
					DatabaseBaseFnc.storeMovie(arrF);
				}
				//Correct data given
				else {
					DatabaseBaseFnc.storeMovie(arr);//add movie to db
				}
			}
			
			/** Television Series **/
			else {
				JOptionPane.showMessageDialog(null, "Successful Transport!");  //TODO: SET ICONS
				
				String fileloc = "";
				String episodeInfo = "";
				episodeInfo += movieInfo.get(0) + " Season " + movieInfo.get(1).replace(" ", "") + " Episode " + movieInfo.get(2).replace(" ", "");
				System.out.println("Episode Info: " + episodeInfo);
				fileloc = FileComs.changeMoveSeries(moviePath, movieInfo);
				
				ArrayList<String> arr = OMDbLookup.getSeries(movieInfo);
				arr.add(fileloc);
				arr.add(movieInfo.get(0));
				arr.add(episodeInfo);
				arr.add("true");
				for(String s : arr)
					System.out.println(s);
				
				if(arr.get(0).equals("")) {
					JOptionPane.showMessageDialog(null, "File move, incorrect information, added to database under your name");
					ArrayList<String> arrF = new ArrayList<String>(Arrays.asList(episodeInfo, "x","x","x","x","x",fileloc,episodeInfo,episodeInfo, "true")); //dummy listwith jsut name
					for(String s : arrF)
						System.out.println(s);
					arrF.add(fileloc);
					DatabaseBaseFnc.storeMovie(arrF);
				}
				else
					DatabaseBaseFnc.storeMovie(arr);//add movie information here
			}
		}
		/** Invalid file extension type **/
		else {
			JOptionPane.showMessageDialog(null, "Invalid File Type!");  //TODO: SET ICONS
		}
			
	}
	
	/**
	 * File extension check if movie extension type
	 * @param s - file name
	 * @return true if valid
	 */
	public static boolean checkFileExtension(String s) {
		String ext = s.substring(s.lastIndexOf(".") + 1, s.length()).toLowerCase();
		//if correct file extension return true
		if(ext.equals("mp4") || ext.equals("wmv") || ext.equals("DivX") || ext.equals(".mov")) {
			return true;
		}
		else
			return false;
	}
}

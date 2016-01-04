package util;

import io.UpdateMovieTitleFrame;

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
		movieInfo.set(0, capitalize(movieInfo.get(0)));
		//TODO:  This should probably be put into methods for readibility sake
		if(checkFileExtension(moviePath)) {
			/** It's a movie, not a television episode **/
			if(!isSeries) {
				JOptionPane.showMessageDialog(null, "Successful Transport!");  //TODO: SET ICONS
				
				//grab file name and path prepare to move to other disk
				String fileloc = "";
				fileloc = FileComs.changeMoveMovie(moviePath, movieInfo.get(0));
				
				storeInDBMovie(movieInfo, fileloc);
			}
			
			/** Television Series **/
			else {
				JOptionPane.showMessageDialog(null, "Successful Transport!");  //TODO: SET ICONS
				
				String fileloc = "";
				movieInfo.set(1, movieInfo.get(1).replace(" ", ""));
				movieInfo.set(2, movieInfo.get(2).replace(" ", ""));
				fileloc = FileComs.changeMoveSeries(moviePath, movieInfo);
				
				storeInDBSeries(movieInfo, fileloc);
			}
		}
		/** Invalid file extension type **/
		else {
			JOptionPane.showMessageDialog(null, "Invalid File Type!");  //TODO: SET ICONS
		}
			
	}
	
	
	private static void storeInDBSeries(ArrayList<String> movieInfo, String fileLocation) {
		String episodeInfo = movieInfo.get(0) + " Season " + movieInfo.get(1)+ " Episode " + movieInfo.get(2);
		
		Movie episode = OMDbLookup.getSeries(movieInfo);
		episode.setLocation(fileLocation);
		episode.setSeriesInfo(movieInfo.get(0));
		episode.setDisplayInfo(episodeInfo);
		episode.setIsSeries("true");
		if(episode.getTitle().equals("")) {
			JOptionPane.showMessageDialog(null, "File move, incorrect information, added to database under your name");
			
			DatabaseBaseFnc.storeMovie(episode);
		}
		else
			DatabaseBaseFnc.storeMovie(episode);//add movie information here
	}
	
	private static void storeInDBMovie(ArrayList<String> movieInfo, String filelocation) {
		//pull data from OMDb JSON format
		Movie movie = OMDbLookup.getMovie(movieInfo.get(0));
		
		//Put in db.  if no info returned from omdb, do what needs to be done
		//Add additional data as needed for series fields
		movie.setLocation(filelocation);
		movie.setSeriesInfo("x");
		movie.setDisplayInfo(movie.getTitle());
		movie.setIsSeries("false");
		
		//If no database information found (Incorrect title given)
		if(movie.getTitle().equals("")) {
			JOptionPane.showMessageDialog(null, "File move, incorrect information, added to database under your name");
			movie.setTitle(movieInfo.get(0));
			movie.setDisplayInfo(movie.getTitle());
			DatabaseBaseFnc.storeMovie(movie);
		}
		//Correct data given
		else {
			DatabaseBaseFnc.storeMovie(movie);//add movie to db
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
		if(ext.equals("mp4") || ext.equals("wmv") || ext.equals("DivX") || ext.equals(".mov") || ext.equals("m4v") || ext.equals("mkv") || ext.equals("avi")) {
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Capitalizes because sql is stupid and sees a difference between upper and lowercases...
	 * @param s
	 * @return
	 */
	private static String capitalize(String s) {
		StringBuilder title = new StringBuilder(s);
		if(Character.isLetter(s.charAt(0))) {
				title.setCharAt(0, Character.toUpperCase(s.charAt(0)));
		}
		else;
		boolean lastWasSpace = false;
		for(int i = 0; i < s.length(); i++) {
			if(!lastWasSpace && Character.isWhitespace(s.charAt(i)))
				lastWasSpace = true;
			else if(lastWasSpace && ! Character.isWhitespace(s.charAt(i)) && Character.isLetter(s.charAt(i))) {
				title.setCharAt(i, Character.toUpperCase(s.charAt(i)));
				lastWasSpace = false;
				
			}
		}
		return title.toString();
	}

	
	
	/**
	 * Correcting Movie Information Here
	 * 
	 * @param id
	 * @param isSeries
	 */
	public static void correctInfo(ArrayList<String> correctInfo, int id, boolean isSeries){
		String currentPath = DatabaseBaseFnc.getPath(id);
		correctInfo.set(0, capitalize(correctInfo.get(0)));
		String newLocation;
		
		if(!isSeries) {
			newLocation = FileComs.changeMoveMovie(currentPath, correctInfo.get(0));
			//get new series info
			storeInDBMovie(correctInfo, newLocation);
			FileComs.removeFile(id);
			DatabaseBaseFnc.removeMovieFromDB(id);
			
			
			
		}
		else {
			correctInfo.set(1, correctInfo.get(1).replace(" ", ""));
			correctInfo.set(2, correctInfo.get(2).replace(" ", ""));
			newLocation = FileComs.changeMoveSeries(currentPath, correctInfo);
			storeInDBSeries(correctInfo, newLocation);
			FileComs.removeFile(id);
			DatabaseBaseFnc.removeMovieFromDB(id);
			
		}
		
	}	
}

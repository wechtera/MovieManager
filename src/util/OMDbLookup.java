package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class OMDbLookup {

	public static ArrayList<String>getMovie(String movieTitle) {
		movieTitle = movieTitle.replace(" ", "-");  //format for the db
		JSONObject jobj = getJSON(movieTitle);
		ArrayList<String> arr = getInfo(jobj);
		return arr;
	}
	/**
	 * Gets information from json
	 * Returns empty string for first value Movie not found for descript if it did not return / find a movie
	 * Otherwise returns in return order:
	 * Title, plot, release, runtime, genre, imdbrating
	 * @param jobj
	 * @return 
	 */
	private static ArrayList<String> getInfo(JSONObject jobj) {
		ArrayList<String> arr = new ArrayList<String>();
		try {
			//check if inserted title was correct
			if(!Boolean.valueOf(jobj.get("Response").toString())) {
				arr.add("");
				arr.add("No Movie Found");
				return arr;
			}
			arr.add(jobj.get("Title").toString());
			arr.add(jobj.get("Plot").toString());
			arr.add(jobj.get("Released").toString());
			arr.add(jobj.get("Runtime").toString());
			arr.add(jobj.get("Genre").toString());
			arr.add(jobj.get("imdbRating").toString());

			return arr;
			
		} catch(JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Gets a json object file from url for MOVIES
	 * @param movieTitle
	 * @return
	 */
	private static JSONObject getJSON(String movieTitle) {
		String url = "http://www.omdbapi.com/?t="+movieTitle+"&y=&plot=full&r=json"; 
		try {
			InputStream is = new URL(url).openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(br);
			if(jsonText.equals(null))
				return null;
			JSONObject json = new JSONObject(jsonText);
		      return json;
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} 	
	}
	

	/**
	 * Gets a json object file from url for MOVIES
	 * @param movieTitle
	 * @return
	 */
	private static JSONObject getJSONSeries(ArrayList<String> arr) {
		String url = "http://www.omdbapi.com/?t="+arr.get(0)+"&season="+arr.get(1)+"&episode="+arr.get(2)+"&y=&plot=full&r=json"; 
		try {
			InputStream is = new URL(url).openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(br);
			if(jsonText.equals(null))
				return null;
			JSONObject json = new JSONObject(jsonText);
		      return json;
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} 	
	}
	/**
	 * parses reader for json format
	 * @param r
	 * @return
	 */
	private static String readAll(Reader r) {
		try {
			StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = r.read()) != -1) 
		      sb.append((char) cp);
		    return sb.toString();
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<String> getSeries(ArrayList<String> seriesInfo) {
		//format for the db
		seriesInfo.set(0, seriesInfo.get(0).replace(" ", "-"));
		seriesInfo.set(1, seriesInfo.get(1).replace(" ", ""));
		seriesInfo.set(2, seriesInfo.get(2).replace(" ", ""));
		JSONObject jobj = getJSONSeries(seriesInfo);
		ArrayList<String> arr = getInfo(jobj);
		return arr;
	}
}

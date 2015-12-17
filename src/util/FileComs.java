package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption.*;
import java.util.ArrayList;
import java.util.Properties;

import core.Main;

public class FileComs {
	
	/**
	 *  Check if db file exists
	 * @return true if file is found and exists, false if no db file exists
	 */
	public static boolean pingDbFile() {
		File file = new File("Movies.db");
		if(file.exists() && !file.isDirectory()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Creates database file
	 * @return true if file created correctly
	 */
	public static boolean createDbFile() {
		File file = new File("Movies.db");
		try {
			file.createNewFile();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * creates a config file
	 * @return true if created successfully
	 */
	public static boolean createConfigFile() {	
		File file = new File(".logs/config.properties");
		try {
			System.out.println("File Made");
			file.getParentFile().mkdirs();
			file.createNewFile();
			return true;
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	/**
	 * Make files under s to hold movies
	 * @param s
	 */
	public static void makeMovieDirs(String s) {
		File f = new File(s+"/Media");
		File fm = new File(f.getAbsolutePath()+ "/Movies");
		File fs = new File(f.getAbsolutePath()+ "/Series");
		File fh = new File(System.getProperty("user.home")+"/Desktop/LocalMedia");
		f.mkdir();
		fm.mkdir();
		fs.mkdir();
		fh.mkdir();
	}
	
	/**
	 * Updates Property in config file (.logs/config.properties)
	 * @param key
	 * @param value
	 */
	public static void updateProperties(String key, String value) {
		Properties conf = new Properties();
		OutputStream out = null;
		try {
			out = new FileOutputStream(".logs/config.properties");
			conf.setProperty(key, value);
			conf.store(out, null);
		} catch(IOException io) {
			io.printStackTrace();
		}
	}
	
	/**
	 * Pulls Peroperty from config file (.logs/config.properties)
	 * null if problem
	 * @param key
	 * @return Value of key from peroperties
	 */
	public static String getProperties(String key) {
		Properties conf = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream(".logs/config.properties");
			conf.load(in);
			return conf.getProperty(key);
		} catch(IOException io) {
			io.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Shift series episode from download location to storage space
	 * @param moviePath
	 * @param movieName
	 */
	public static String changeMoveSeries(String moviePath, ArrayList<String> seriesInfo) {	
		File f = new File(moviePath);
		String seriesFile = setFileNameSeries(seriesInfo, moviePath);
		File f2 = new File(Main.DB_FILE_LOCATION+"/Series/" + seriesFile);
		f.renameTo(f2);	
		return f2.getAbsolutePath();
	}
	
	/**
	 * Shift movie from download location to storage space
	 * @param moviePath
	 * @param movieName
	 */
	public static String changeMoveMovie(String moviePath, String movieName){
		File f = new File(moviePath);
		String movieFile = setFileName(movieName, moviePath);
		File f2 = new File(Main.DB_FILE_LOCATION+"/Movies/" + movieFile);
		f.renameTo(f2);
		return f2.getAbsolutePath();
	}
	
	/**
	 * Rename movie to something nice
	 * @param s
	 * @param mp
	 * @return String movie name based off of title
	 */
	public static String setFileName(String s, String mp) {
		String moviefile = "";
		moviefile += s.replace(" ", "");
		moviefile += ".";
		moviefile += mp.substring(mp.lastIndexOf(".") + 1, mp.length()).toLowerCase();
		return moviefile;		
	}
	
	/**
	 * Makes series filename
	 * @param seriesInfo - episode info:  series name, series season, series episodeNumber
	 * @param sp - series path
	 * @return
	 */
	public static String setFileNameSeries(ArrayList<String> seriesInfo, String sp) {
		String seriesFile = "";
		seriesFile += seriesInfo.get(0).replace(" ", "");
		seriesFile += "-" + seriesInfo.get(1);
		seriesFile += "-" + seriesInfo.get(2);
		seriesFile += ".";
		seriesFile += sp.substring(sp.lastIndexOf(".") + 1, sp.length()).toLowerCase();
		return seriesFile;
	}

	public static void removeFile(int id) {
		String fp = DatabaseBaseFnc.getPath(id);
		
		File f = new File(fp);
		if(f.exists())
			f.delete();
		else
			System.out.println("No Such File: " + fp);
	}

	/**
	 * copies media from storage to local access in 
	 * Local Media file
	 * @param path
	 */
	public static void copyMovie(String path) {
		// TODO Auto-generated method stub
		if(new File(path).exists()) {
			Path p = Paths.get(path);
			String stringPath1 = System.getProperty("user.home") + "/Desktop/LocalMedia/";		
			Path p1 = Paths.get(stringPath1+p.getFileName().toString());
			try {
				new File(stringPath1).createNewFile();
				if(!p1.toFile().exists())
					Files.copy(p, p1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * TODO: FINISH IT
	 * Add method to clear out local media folder
	 * clears whole thing
	 */
	public static void cleanLocal() {
		for(File f : new File(System.getProperty("user.home")+"/Desktop/LocalMedia").listFiles()) {
			f.delete();
		}	
	}
	
}

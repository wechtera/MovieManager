package core;
import io.FirstRunIO;
import util.DatabaseBaseFnc;
import util.FileComs;

/**
 * TODO: Populate config file with options 
 *
 */
public class FirstRun {
	/**
	 * check db file exists and table exits
	 * @return true if not first run
	 */
	public static boolean checkInit() {
		if(FileComs.pingDbFile()) {
			if(DatabaseBaseFnc.pingDb()) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * @return true if database initialized correctly
	 */
	public static boolean initDb() {
		if(DatabaseBaseFnc.initDb())
			return true;
		return false;
	}
	/**
	 * Creates configuration file 
	 */
	public static void initConfig() {
		FileComs.createConfigFile();
	}
	/**
	 * Lets find / make the folders we will point to
	 * Point to main folder, make main folder and we'll add Two folders under, a Series and a Movies folder
	 * Make window to choose
	 * Create two folders
	 * write to config file .logs/config.properties
	 */
	public static void setDBLocation() {
		new FirstRunIO();
	}
	public static void dBSetup(String s) {
		//make files
		FileComs.makeMovieDirs(s);
		//save file name to config file
		FileComs.updateProperties("dbLocation", s+"/Media");
		
	}
}

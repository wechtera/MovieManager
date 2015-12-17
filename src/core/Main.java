package core;

import util.FileComs;
import io.AddMovieOption;
import io.AddSeriesOption;
import io.Gui;
import io.MainPanel;
import core.FirstRun;

/**
 * Start of new program
 * Check if first run
 * Launch gui
 * Connect to db / check connection
 * @author wechtera
 *
 */
public class Main {
	
	public static String DB_FILE_LOCATION;
	
	public static void main(String [] args) {
		
		if(!FirstRun.checkInit()) {
		//if(true) {  //For testing only!
			FirstRun.setDBLocation();
        	FirstRun.initDb();
        	FirstRun.initConfig();
        }
		else {
			//get configs here
			DB_FILE_LOCATION = FileComs.getProperties("dbLocation");
		}
        
        //Initiate window
        runPrimary();

    }
    
    public static void runPrimary() {
    	new Gui();
	}
    
    public static void addNewMovie(MainPanel jp) {
    	new AddMovieOption(jp);
    }
    
    public static void addNewSeries(MainPanel mp) {
    	new AddSeriesOption(mp);
    }
	
}

package util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class DatabaseBaseFnc {
	static Connection conn = null;
	final static String dbName = "Movies.db";
	
	/**
	 * Check if database table exists
	 * @return True if Movies table exists, false otherwise
	 */
	public static boolean pingDb() {
		try {
			conn = connect(conn);
			DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, "MOVIES", null);
            conn.close();
            return rs.next();
   
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;  //should never reach
		
	}
	/**
	 * Connect to database
	 */
    public static Connection connect(Connection conn){
        try{
        	Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            return conn;
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            return null;
        }
        
    }
    /**
     * Initializes database table.  Create stmt
     * @return true if table created sucessfully
     */
    public static boolean initDb() {
    	//creates file for db
    	FileComs.createDbFile();
    	try {
        	conn = connect(conn);
    		Statement stmt = conn.createStatement();
    		String sqlInit = "CREATE TABLE MOVIES (ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT NOT NULL, RELEASE TEXT NOT NULL, GENRE TEXT NOT NULL, PLOT TEXT NOT NULL, LENGTH INT NOT NULL, RATING INT NOT NULL, LOCATION TEXT NOT NULL, TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP, SERIES_INFO TEXT NOT NULL, DISPLAY_INFO TEXT NOT NULL, IS_EPISODE BOOLEAN NOT NULL);";
    		stmt.executeUpdate(sqlInit);
    		stmt.close();
    		conn.close();
    		return true;
    	} catch(SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    /**
     * Stores movie data in database
     * @param arr: movie information Title,Plot,Length,Release date,genre,imbd rating 
     * @return
     */
    
    public static boolean storeMovie(ArrayList<String> arr) {
    	String s = "INSERT into MOVIES "
				+ "(TITLE, RELEASE, GENRE, PLOT, LENGTH, RATING, LOCATION, SERIES_INFO, DISPLAY_INFO, IS_EPISODE) "
				+ " VALUES (?,?,?,?,?,?,?,?,?,?);"; //need to work
    	try {
			conn = connect(conn);
			PreparedStatement stmt = conn.prepareStatement(s);
			stmt.setString(1, arr.get(0));
			stmt.setString(2, arr.get(2));
			stmt.setString(3, arr.get(4));
			stmt.setString(4, arr.get(1));
			stmt.setString(5, arr.get(3));
			stmt.setString(6, arr.get(5));
			stmt.setString(7, arr.get(6));
			stmt.setString(8, arr.get(7));
			stmt.setString(9, arr.get(8));
			stmt.setBoolean(10, Boolean.valueOf(arr.get(9)));
			stmt.executeUpdate();
			stmt.close();
			conn.close();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}	
    }
   
    /**
     * Gets movies or series based on boolean series
     * @param series - Gets eries titles?
     * @return
     */
	public static HashMap<Integer, String> getTitles(boolean isSeries) {
		//Decide what we're getting
		String s = "";
		if(!isSeries)
			s = "SELECT ID, DISPLAY_INFO from Movies where IS_EPISODE = 0 ORDER BY DISPLAY_INFO DESC";
		else
			s = "SELECT ID, DISPLAY_INFO from MOVIES where IS_EPISODE = 1 ORDER BY DISPLAY_INFO DESC";
		
		HashMap <Integer, String> m = new HashMap<Integer, String>();
		
		try {
			conn = connect(conn);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(s);
			while(rs.next())
				m.put(rs.getInt(1), rs.getString(2));
			rs.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return m;
	}
	
	/**
	 * Removes a row from the database
	 * @param id
	 */
	public static void removeMovieFromDB(int id) {
		String s = "DELETE from MOVIES where ID = " + id;
		
		try {
			conn = connect(conn);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(s);
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets most recent entry
	 * @return
	 */
	public static HashMap<Integer, String> getRecent() {
		String s = "SELECT ID, TITLE from Movies ORDER BY TIMESTAMP DESC LIMIT 1";
		HashMap<Integer, String> m = new HashMap<Integer, String>();
		try {
			conn = connect(conn);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(s);
			while(rs.next()) {
				m.put(rs.getInt(1), rs.getString(2));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return m;
	}
	
	/**
	 * Gets a full row from the database
	 * @param id
	 * @return
	 */
	public static ArrayList<String> getRow(int id) {
		ArrayList<String> arr = new ArrayList<String>();
		String s = "SELECT TITLE, RELEASE, GENRE, LENGTH, RATING, PLOT from MOVIES where id = " + Integer.toString(id);
		try {
			conn = connect(conn);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(s);
			while(rs.next()) {
				arr.add(rs.getString(1));
				arr.add(rs.getString(2));
				arr.add(rs.getString(3));
				arr.add(rs.getString(4));
				arr.add(rs.getString(5));
				arr.add(rs.getString(6));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}
	
	/**
	 * Gets path of file with id associated with it
	 * @param id
	 * @return
	 */
	public static String getPath(int id) {
		String fp = "";
		String s = "SELECT LOCATION from MOVIES where ID = " + Integer.toString(id);
		try {
			conn = connect(conn);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(s);
			while(rs.next()) {
				fp = rs.getString(1);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return fp;
	}
}

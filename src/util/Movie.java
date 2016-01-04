package util;
/**
 * obsolete at the moment poor planning probablyshould have used 
 * and just moved lists around but im dumb
 * @author wechtera
 *
 */
public class Movie {
	private int id;
	private String title;
	private String release;
	private String genre;
	private String plot;
	private String length;
	private String rating;
	private String location;
	private String seriesInfo;
	private String displayInfo;
	private String isSeries;
	
	public Movie() {
	}
	public Movie(int id, String title, String release, String genre, String plot, String length, String rating, String location, String seriesInfo, String displayInfo, String isSeries) {
		this.id = id;
		this.setTitle(title);
		this.setGenre(genre);
		this.setPlot(plot);
		this.setLength(length);
		this.setRating(rating);
		this.setLocation(location);
		this.setSeriesInfo(seriesInfo);
		this.setDisplayInfo(displayInfo);
		this.setIsSeries(isSeries);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getPlot() {
		return plot;
	}
	public void setPlot(String plot) {
		this.plot = plot;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getSeriesInfo() {
		return seriesInfo;
	}
	public void setSeriesInfo(String seriesInfo) {
		this.seriesInfo = seriesInfo;
	}
	public String getDisplayInfo() {
		return displayInfo;
	}
	public void setDisplayInfo(String displayInfo) {
		this.displayInfo = displayInfo;
	}
	public String getIsSeries() {
		return isSeries;
	}
	public boolean getIsSeriesBool() {
		return Boolean.valueOf(isSeries);
	}
	public void setIsSeries(String isSeries) {
		this.isSeries = isSeries;
	}
}

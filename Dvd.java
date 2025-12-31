public class Dvd extends LibraryItem {
	private String director;
	private String category;
	private String runtime;
	
	
	public Dvd(String ID, String title, String director, String category, String runtime, String type) {
		super(ItemClass.Dvd, ID, title, Type.valueOf(type.toLowerCase()));
		this.director = director;
		this.category = category;
		this.runtime = runtime;
	}
	
	public String getDirector() {
		return director;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getRunTime() {
		return runtime;
	}
	
}

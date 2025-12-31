public class Book extends LibraryItem {
	private String author;
	private String genre;
	
	public Book(String ID,String title, String  author , String genre, String type) {
		super(ItemClass.Book,ID,title,Type.valueOf(type.toLowerCase()));
		this.author = author;
		this.genre = genre;
	}

	public String getAuthor() {
		return author;
	}

	public String getGenre() {
		return genre;
	}
		
	
}

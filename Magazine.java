public class Magazine extends LibraryItem {
	private String publisher;
	private String category;
	
	public Magazine(String ID, String title, String publisher, String category, String type) {
		super(ItemClass.Magazine, ID, title, Type.valueOf(type.toLowerCase()));
		this.publisher = publisher;
		this.category = category;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public String getCategory() {
		return category;
	}
}

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Abstract base class representing a library item (Book, Magazine, dvd)
 * Contains common properties and methods shared by all item types
 */
abstract class LibraryItem {
	private ItemClass itemClass;
	private String ID;
	private String title;
	private Type type;
	private boolean borrowed = false;
	
	private static Map<String,LibraryItem> itemMap = new HashMap<>();
	
	enum Type {normal,reference,rare,limited};
	enum ItemClass {Book,Magazine,Dvd};
	
	public LibraryItem(ItemClass itemClass,String ID, String title, Type type) {
		this.itemClass = itemClass;
		this.ID = ID;
		this.title = title;
		this.type = type;
		
		}
	
	protected String getType() {
		return type.name();
	}
	
	protected String getTitle() {
		return title;
	}
	
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	//Indicates that the item has been borrowed
	public void borrowed() {
		borrowed = true;
	}
	//Indicates that the item has been returned
	public void returned() {
		borrowed = false;
	}
	
	public boolean getBorrowed() {
		return borrowed;
	}

	//Initializes the item map by inserting all items from lists into a central map
	public static void initializeItemMap(List<Book> books, List<Dvd> dvds , List<Magazine> magazines ) {
		 for (Book book : books) {
	            itemMap.put(book.getID(), book);
	        }
	        for (Dvd dvd : dvds) {
	            itemMap.put(dvd.getID(), dvd);
	        }
	        for (Magazine magazine : magazines) {
	            itemMap.put(magazine.getID(), magazine);
	        }
	}
	
	//finds the library item by its unique ID
   public static LibraryItem findItemById(String ID) {
       return itemMap.get(ID);
   }
	
}

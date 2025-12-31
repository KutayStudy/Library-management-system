import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
/**
 * Abstract base class representing a library user (Student, AcademicMember, GuestUser).
 * Contains shared fields and behavior for all user types.
 */
abstract class LibraryUser {
	private String name;
	private String ID;
	private String phoneNumber;
	private int BorrowedItem = 0, penalty = 0;
	private UserType userType;
	private static Map<String, LibraryUser> userMap = new HashMap<>();
	private int allowedDay;
	
	enum UserType {Student,AcademicStaff,GuestUser};
	
	
	public LibraryUser(UserType userType,String name, String ID, String phoneNumber) {
		this.userType = userType;
		this.name = name;
		this.ID = ID;
		this.phoneNumber = phoneNumber;
	}
	
	//Abstract method to be implemented by subclasses to define own special borrow behavior.
	public abstract boolean BorrowItem(LibraryItem item);
	
	
	public int getBorrowedItem() {
		return BorrowedItem;
	}

	public void setBorrowedItem(int borrowedItem) {
		BorrowedItem = borrowedItem;
	}

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	//Initializes the user map with students,academics, and guest users.
	public static void initializeUserMap(List<Student> students, List<AcademicMember> academicMembers , List<GuestUser> guestUsers ) {
		 for (Student student : students) {
	            userMap.put(student.getID(), student);
	        }
	        for (AcademicMember academic : academicMembers) {
	            userMap.put(academic.getID(), academic);
	        }
	        for (GuestUser guest : guestUsers) {
	            userMap.put(guest.getID(), guest);
	        }
	}
	
	//Returns a user object by their ID
    public static LibraryUser findUserById(String ID) {
        return userMap.get(ID);
    }
    
    //Clears the user's penalty to zero
    public void clearDebt() {
    	this.penalty = 0;
    }
	//Returns the allowed number of borrowing days.(Must be implemented by subclasses)
    abstract public int getAllowedDay();
	
	
	
	
}

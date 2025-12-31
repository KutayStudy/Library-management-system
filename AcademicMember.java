public class AcademicMember extends LibraryUser {
	private String department;
	private String faculty;
	private String title;
	private static final int allowedDay = 15; //Maximum number of days an academic member can borrow an item
	
	public AcademicMember( String name, String ID, String phoneNumber, String department,
			String faculty, String title) {
		super(UserType.AcademicStaff, name, ID, phoneNumber);
		this.department = department;
		this.faculty = faculty;
		this.title = title;
	}
	
	//Handles borrowing logic for academic members(checks borrow rules and updates system state if successful)
	@Override
	public boolean BorrowItem(LibraryItem item) {
		if (getBorrowedItem() < 3 && getPenalty()  < 6 && item.getBorrowed() == false) {
			Main.getOutput().write(getName() + " successfully borrowed! " + item.getTitle());
			setBorrowedItem(getBorrowedItem() + 1);
			item.borrowed();
			return true;
		}
		else if (getPenalty()  >= 6) {
			Main.getOutput().write(getName() + " cannot borrow " + item.getTitle() + ", you must first pay the penalty amount! " + getPenalty() +"$");
		}
		else if (item.getBorrowed()) {
			Main.getOutput().write(getName() + " cannot borrow " + item.getTitle() + ", it is not available!");
		}
		else {
			Main.getOutput().write(getName() + " cannot borrow " + item.getTitle() + ", since the borrow limit has been reached!");
		}
		return false;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int getAllowedDay() {
		return allowedDay;
	}
	
	
}

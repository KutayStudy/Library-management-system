public class Student extends LibraryUser{
	private String department;
	private String faculty;
	private int grade;
	private static final int allowedDay = 30; //Maximum number of days an academic member can borrow an item
	
	
	public Student(String name, String ID, String phoneNumber, String department, String faculty,
			int grade) {
		super(UserType.Student, name, ID, phoneNumber);
		this.department = department;
		this.faculty = faculty;
		this.grade = grade;
	}
	
	//Handles borrowing logic for Students(checks borrow rules and updates system state if successful)
	@Override
	public boolean BorrowItem(LibraryItem item) {
		if (getBorrowedItem() < 5 && getPenalty() < 6 && !item.getType().equals("reference") && item.getBorrowed() == false) {
			Main.getOutput().write(getName() + " successfully borrowed! " + item.getTitle());
			setBorrowedItem(getBorrowedItem() + 1);
			item.borrowed();
			return true;
		}
		else if (getPenalty() >= 6) {
			Main.getOutput().write(getName() + " cannot borrow " + item.getTitle() + ", you must first pay the penalty amount! " + getPenalty() +"$");
		}
		else if (item.getBorrowed()) {
			Main.getOutput().write(getName() + " cannot borrow " + item.getTitle() + ", it is not available!");
		}
		else if (item.getType().equals("reference")) {
			Main.getOutput().write(getName() + " cannot borrow " + item.getType() + " item!" );
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

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	@Override
	public int getAllowedDay() {
		return allowedDay;
	}
	
	
}

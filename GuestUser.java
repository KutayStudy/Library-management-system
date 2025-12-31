public class GuestUser extends LibraryUser{
	private String occupation;
	private static final int allowedDay = 7; //Maximum number of days an academic member can borrow an item

	public GuestUser(String name, String ID, String phoneNumber, String occupation) {
		super(UserType.GuestUser, name, ID, phoneNumber);
		this.occupation = occupation;
	}
	
	//Handles borrowing logic for guest Users(checks borrow rules and updates system state if successful)
	@Override
	public boolean BorrowItem(LibraryItem item) {
		if (getBorrowedItem() < 1 && getPenalty() < 6 && !item.getType().equals("rare") && !item.getType().equals("limited") && item.getBorrowed() == false) {
			Main.getOutput().write(getName() + " successfully borrowed! " + item.getTitle());
			setBorrowedItem(getBorrowedItem() + 1);
			item.borrowed();
			return true;
		}
		else if (getPenalty() >= 6) {
			Main.getOutput().write(getName() + " cannot borrow " + item.getTitle() + ", you must first pay the penalty amount! " + getPenalty()  +"$");
		}
		else if (item.getBorrowed()) {
			Main.getOutput().write(getName() + " cannot borrow " + item.getTitle() + ", it is not available!");
		}
		else if (item.getType().equals("limited") || item.getType().equals("rare")) {
			Main.getOutput().write(getName() + " cannot borrow " + item.getType() + " item!" );
		}
		else {
			Main.getOutput().write(getName() + " cannot borrow " + item.getTitle() + ", since the borrow limit has been reached!");
		}
		return false;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	@Override
	public int getAllowedDay() {
		return allowedDay;
	}
	
	
}

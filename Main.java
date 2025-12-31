import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class Main {	
	//Lists to store different types of library items and users and necessary variables. 
	private static ArrayList<Book> books = new ArrayList<Book>();
	private static ArrayList<Magazine> magazines = new ArrayList<>();
	private static ArrayList<Dvd> dvds = new ArrayList<>();
	private static ArrayList<Student> students = new ArrayList<>();
	private static ArrayList<AcademicMember> academicMembers = new ArrayList<>();
	private static ArrayList<GuestUser> guestUsers = new ArrayList<>();
	private static Map<String, BorrowRecord> borrowedItems = new HashMap<>();
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
	private static LocalDate systemDate = LocalDate.now();
	private static OutputManager output;
	


	
	public static void main(String[] args) {
		if (args.length != 4) {
		
			System.out.println("You have to write in this syntax: java Main items.txt users.txt commands.txt output.txt");
		}
		
		String itemsFile = args[0];
		String usersFile = args[1];
		String commandsFile = args[2];
		String outputFile = args[3];
		
		
		try {
			output = new OutputManager(outputFile);
			
			// Read and process the data
			InputReader.createItems(itemsFile);
	        LibraryItem.initializeItemMap(books, dvds, magazines);
	        InputReader.createUsers(usersFile);
	        LibraryUser.initializeUserMap(students, academicMembers, guestUsers);
	        InputReader.analyseCommands(commandsFile);
	        
	        output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static OutputManager getOutput() {
	    return output;
	}
		 

	 
	 public static class OutputManager {
		 // Class for managing output operations
		 private PrintWriter outputWriter;
		 
		 public OutputManager(String fileName) throws IOException {
			 this.outputWriter = new PrintWriter(new FileWriter(fileName));
		 }
		 
		 //Writes a line with newline
		 public void write(String msg) {
			 outputWriter.println(msg);
		 }
		 
		 //Writes a line without adding newline
		 public void writewln(String msg) {
			 outputWriter.print(msg);
		 }
		 
		 
		 public void close() {
			 outputWriter.close();
		 }
		 
		 
	 }
	 public static class BorrowRecord {
		 //Class to store user and borrowDate together
			private LibraryUser user;
			private LocalDate borrowDate;
			
			public BorrowRecord(LibraryUser user, LocalDate borrowDate) {
				this.user = user;
				this.borrowDate = borrowDate;
			}
			
			public LibraryUser getUser() {
				return user;
			}
			
			public LocalDate getBorrowDate() {
				return borrowDate;
			}
		}
	 
	 public static class InputReader {
		 //Class for reading input and executing commands in it
		 
		 //Reads a text file and returns list of string arrays
		 public static List<String[]> readTxt(String input) {
		        List<String[]> data = new ArrayList<>();
		        
		        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
		            String line;
		            while ((line = br.readLine()) != null) {
		                String[] parts = line.split(","); 
		                data.add(parts);  
		            }
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        
		        return data;
		 }
		 
		 //Creates items from input file
		 public static void createItems(String filename) {
			 List<String[]> items = readTxt(filename);
		        for (String[] item : items) {
		        	switch(item[0]) {
		        	case "B":
		        		books.add(new Book(item[1],item[2],item[3],item[4],item[5]));
		        		break;
		        	case "D":
		        		dvds.add(new Dvd(item[1],item[2],item[3],item[4],item[5],item[6]));
		        		break;
		        	case "M":
		        		magazines.add(new Magazine(item[1],item[2],item[3],item[4],item[5])); 
		        		break;
		        	}
		        }
		 }
		 
		 //Creates users from input file
		 public static void createUsers(String filename) {
			 List<String[]> users = readTxt(filename);
		        for (String[] user : users) {
		        	switch(user[0]) {
		        	case "S":
		        		students.add(new Student(user[1],user[2],user[3],user[4],user[5],(int)Integer.valueOf(user[6])));
		        		break;
		        	case "A":
		        		academicMembers.add(new AcademicMember(user[1],user[2],user[3],user[4],user[5],user[6]));
		        		break;
		        	case "G":
		        		guestUsers.add(new GuestUser(user[1],user[2],user[3],user[4]));
		        		break;
		        	}
		        }
		        
		 }
		 
		 //Reads and processes each command from the command file
		 public static void analyseCommands(String filename) {
		        List<String[]> commands = readTxt(filename);
		        for (String[] command : commands) {
		        	switch(command[0]) {
		        	case "borrow":
		        		LibraryUser userB = LibraryUser.findUserById(command[1]);
		        		LibraryItem itemB = LibraryItem.findItemById(command[2]);
		        		LocalDate date1 = LocalDate.parse(command[3],formatter);
		        		if (userB.BorrowItem(itemB))  {
		        			borrowedItems.put(itemB.getID(),new BorrowRecord(userB, date1));  //Store the item's id with who borrows and when
		        			checkPenalty(userB,itemB,date1); 
		        		}
		        		break;
		        	case "return":
		        		LibraryUser userR = LibraryUser.findUserById(command[1]);
		        		LibraryItem itemR = LibraryItem.findItemById(command[2]);
		        		if (itemR.getBorrowed()) {
		        			output.write(userR.getName() + " successfully returned " + itemR.getTitle());
		        			borrowedItems.remove(itemR.getID()); //items returned so clear the borrowedItem information
		        			itemR.returned(); // Set the item available
			        		userR.setBorrowedItem(userR.getBorrowedItem() - 1);
		        		}
		        		else {
		        			output.write(userR.getName() + " cannot return " + itemR.getTitle() + " because he/she doesn't have this item");
		        		}
		        		break;
		        	case "pay":
		        		LibraryUser userP = LibraryUser.findUserById(command[1]);
		        		if (userP.getPenalty() > 0) {
		        			userP.clearDebt();
		        			output.write(userP.getName() + " has paid penalty");
		        		}
		        		else {
		        			output.write(userP.getName() + " has no debt");
		        		}
		        		break;
		        	case "displayUsers":
		        		displayUsers();
		        		break;
		        	case "displayItems":
		        		displayItems();
		        		break;
		        	}
		        }
		 }
		 
		 //Displays all user information
		 public static void displayUsers() {
			 output.write("");
			 for (Student student : students) {
				 output.write("\n------ User Information for " + student.getID() +" ------");
				 output.write("Name: "+ student.getName()+ " Phone: " + student.getPhoneNumber());
				 output.write("Faculty: "+ student.getFaculty()+" Department: "+ student.getDepartment()+ " Grade: " + student.getGrade() +"th");
				 if (student.getPenalty() > 0) {
					 output.write("Penalty: " + student.getPenalty() + "$");
				 }
			 }
			 for (AcademicMember academicMember: academicMembers) {
				 output.write("\n------ User Information for " + academicMember.getID() +" ------");
				 output.write("Name: "+ academicMember.getTitle() + " " +academicMember.getName()+ " Phone: " + academicMember.getPhoneNumber());
				 output.write("Faculty: "+ academicMember.getFaculty()+" Department: "+ academicMember.getDepartment());
				 if (academicMember.getPenalty() > 0) {
					 output.write("Penalty: " + academicMember.getPenalty() + "$");
				 }
			 }
			 for (GuestUser guestUser : guestUsers) {
				 output.write("\n------ User Information for " + guestUser.getID() +" ------");
				 output.write("Name: "+ guestUser.getName()+ " Phone: " + guestUser.getPhoneNumber());
				 output.write("Occupation: " + guestUser.getOccupation());
				 if (guestUser.getPenalty() > 0) {
					 output.write("Penalty: " + guestUser.getPenalty() + "$");
				 }
			 }
			 output.write("");
			 
		 }
		 
		 //Displays all user informations
		 public static void displayItems() {
			 output.write("");
			 for (Book book : books) {
				 output.write("------ Item Information for " + book.getID() +" ------");
				 output.writewln("ID: " + book.getID() + " Name: " + book.getTitle() + " Status: ");
				 if (book.getBorrowed()) {
					 output.write("Borrowed " + "Borrowed Date: " + findBorrowInfosByItemId(book.getID()).getBorrowDate().format(formatter) + " Borrowed by: " + findBorrowInfosByItemId(book.getID()).getUser().getName());
				 }
				 else {
					 output.write("Available");
				 }
				 output.write("Author: "+ book.getAuthor() + " Genre: " + book.getGenre() + "\n");
			 }
			 for (Magazine magazine : magazines) {
				 output.write("------ Item Information for " + magazine.getID() +" ------");
				 output.writewln("ID: " + magazine.getID() + " Name: " + magazine.getTitle() + " Status: ");
				 if (magazine.getBorrowed()) {
					 output.write("Borrowed " + "Borrowed Date: " + findBorrowInfosByItemId(magazine.getID()).getBorrowDate().format(formatter) + " Borrowed by: " + findBorrowInfosByItemId(magazine.getID()).getUser().getName());
				 }
				 else {
					 output.write("Available");
				 }
				 output.write("Publisher: "+ magazine.getPublisher() + " Category: " + magazine.getCategory() + "\n");
			 }
			for (Dvd dvd : dvds) {
				output.write("------ Item Information for " + dvd.getID() +" ------");
				output.writewln("ID: " + dvd.getID() + " Name: " + dvd.getTitle() + " Status: ");
				 if (dvd.getBorrowed()) {
					 output.write("Borrowed " + "Borrowed Date: " + findBorrowInfosByItemId(dvd.getID()).getBorrowDate().format(formatter) + " Borrowed by: " + findBorrowInfosByItemId(dvd.getID()).getUser().getName());
				 }
				 else {
					 output.write("Available");
				 }
				 output.write("Director: "+ dvd.getDirector() + " Category: " + dvd.getCategory() + " Runtime: " + dvd.getRunTime() + "\n");
			}
		 }
		 
		 //Returns the borrow record for a given item ID
		 public static BorrowRecord findBorrowInfosByItemId(String itemId) {
			    return borrowedItems.get(itemId);
			}
		 
		 //Checks if a user has exceeded their borrow time and applies penalty
		 public static void checkPenalty(LibraryUser user, LibraryItem item, LocalDate borrowDate) {
			 
			 int allowedDay = user.getAllowedDay();
			 int daysBorrowed = (int) ChronoUnit.DAYS.between(borrowDate,systemDate);
			 if (daysBorrowed > allowedDay) {
				 user.setPenalty(user.getPenalty() + 2);
				 item.returned();
				 user.setBorrowedItem(user.getBorrowedItem() - 1);
				 borrowedItems.remove(item.getID());
				 }
			 }
	}
}
	 

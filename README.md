# Library Management System (Java)

This is a command-based library management system written in Java.
It reads **items**, **users**, and **commands** from text files, processes operations like **borrow/return/pay**, and writes all results to an output file.

I built this project mainly to practice **OOP design** (abstraction, inheritance, polymorphism), file parsing, and rule-based command execution.

---

## How it works

The program takes **4 file paths** as arguments:

- `items.txt` (library items)
- `users.txt` (library users)
- `commands.txt` (operations to execute)
- `output.txt` (where results are written)

Items and users are created from the input files, then commands are executed in order.

### Item types
- `Book`
- `Magazine`
- `Dvd`

### User types
- `Student`
- `AcademicMember`
- `GuestUser`

### Borrowing rules (summary)
- Borrow limit:
  - Student: up to **5** items
  - AcademicMember: up to **3** items
  - GuestUser: up to **1** item
- If a user has **penalty >= 6$**, they must **pay** before borrowing again.
- Guests cannot borrow **rare/limited/reference** items.
- Students cannot borrow **reference** items.
- An item cannot be borrowed if it is already borrowed.

### Allowed borrowing days
- Student: **30** days  
- AcademicMember: **15** days  
- GuestUser: **7** days  

> Returning after the allowed period increases the penalty based on late days.

---

## Features

- ✅ File-based initialization (items/users)
- ✅ Command-based execution (borrow/return/pay/display)
- ✅ OOP structure with abstract base classes (`LibraryItem`, `LibraryUser`)
- ✅ Different borrowing rules per user type (polymorphic behavior)
- ✅ Penalty tracking & payment
- ✅ Output logging to a single file

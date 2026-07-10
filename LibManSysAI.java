import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

// --- USER CLASSES ---
abstract class LibMem {
    private String memName;
    private String memID;

    public LibMem(String memName, String memID) {
        this.memName = memName;
        this.memID = memID;
    }

    public String getName() { return memName; }
    public String getID() { return memID; }

    public abstract void displayInfo();
}

class Librarian extends LibMem {
    public Librarian(String memName, String memID) {
        super(memName, memID);
    }

    @Override
    public void displayInfo() {
        System.out.println("Librarian Name: " + getName() + " | ID: " + getID());
    }
}

class Student extends LibMem {
    public Student(String memName, String memID) {
        super(memName, memID);
    }

    @Override
    public void displayInfo() {
        System.out.println("Student Name: " + getName() + " | ID: " + getID());
    }
}

// --- BOOK CLASS ---
class Book {
    private String bookName;
    private int bookID;
    private boolean isBorrowed;

    public Book(String bookName, int bookID) {
        this.bookName = bookName;
        this.bookID = bookID;
        this.isBorrowed = false;
    }

    public String getBookName() { return bookName; }
    public int getBookID() { return bookID; }
    public boolean isBorrowed() { return isBorrowed; }

    public String getAvailabilityStatus() {
        return isBorrowed ? "Not Available" : "Available";
    }

    public boolean borrowBook() {
        if (!isBorrowed) {
            isBorrowed = true;
            return true;
        }
        return false;
    }

    public boolean returnBook() {
        if (isBorrowed) {
            isBorrowed = false;
            return true;
        }
        return false;
    }

    public void displayInfo() {
        System.out.println("Book ID: " + bookID + " | Title: \"" + bookName + "\" | Status: " + getAvailabilityStatus());
    }
}

// --- MAIN MANAGEMENT SYSTEM ---
public class LibManSysAI {
    private static final ArrayList<Book> bookDatabase = new ArrayList<>();
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        // Seed database with sample books
        bookDatabase.add(new Book("The Hobbit", 101));
        bookDatabase.add(new Book("1984", 102));

        boolean exitSystem = false;

        while (!exitSystem) {
            System.out.println("\n=== WELCOME TO THE LIBRARY MANAGEMENT SYSTEM ===");
            System.out.println("Identify your role:");
            System.out.println("1. Librarian");
            System.out.println("2. Student");
            System.out.println("3. Exit Application");
            System.out.print("Choice: ");

            int roleChoice = getSafeIntInput();

            switch (roleChoice) {
                case 1:
                    handleLibrarianMenu();
                    break;
                case 2:
                    handleStudentMenu();
                    break;
                case 3:
                    exitSystem = true;
                    System.out.println("Thank you for using the Library Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
        input.close();
    }

    // --- SUB-MENUS ---
    private static void handleLibrarianMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Librarian Menu ---");
            System.out.println("1. Add a Book");
            System.out.println("2. Remove a Book");
            System.out.println("3. View All Books");
            System.out.println("4. Back to Main Menu");
            System.out.print("Action: ");

            int choice = getSafeIntInput();
            switch (choice) {
                case 1:
                    addBookFlow();
                    break;
                case 2:
                    removeBookFlow();
                    break;
                case 3:
                    viewBooks();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid action.");
            }
        }
    }

    private static void handleStudentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Student Menu ---");
            System.out.println("1. View Available Books");
            System.out.println("2. Borrow a Book");
            System.out.println("3. Return a Book");
            System.out.println("4. Back to Main Menu");
            System.out.print("Action: ");

            int choice = getSafeIntInput();
            switch (choice) {
                case 1:
                    viewBooks();
                    break;
                case 2:
                    borrowBookFlow();
                    break;
                case 3:
                    returnBookFlow();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid action.");
            }
        }
    }

    // --- SERVICE FLOW IMPLEMENTATIONS ---
    private static void addBookFlow() {
        System.out.print("Enter Book Title: ");
        String name = input.nextLine();
        System.out.print("Assign an ID (Integer): ");
        int id = getSafeIntInput();

        // Check for duplicates
        if (findBookById(id) != null) {
            System.out.println("Error: A book with ID " + id + " already exists.");
            return;
        }

        bookDatabase.add(new Book(name, id));
        System.out.println("Success: \"" + name + "\" has been added to the database.");
    }

    private static void removeBookFlow() {
        System.out.print("Enter Book ID to remove: ");
        int id = getSafeIntInput();
        Book target = findBookById(id);

        if (target != null) {
            bookDatabase.remove(target);
            System.out.println("Success: \"" + target.getBookName() + "\" removed.");
        } else {
            System.out.println("Error: Book ID not found.");
        }
    }

    private static void viewBooks() {
        if (bookDatabase.isEmpty()) {
            System.out.println("The library repository is currently empty.");
            return;
        }
        System.out.println("\n--- Library Catalog ---");
        for (Book b : bookDatabase) {
            b.displayInfo();
        }
    }

    private static void borrowBookFlow() {
        System.out.print("Enter Book ID to borrow: ");
        int id = getSafeIntInput();
        Book target = findBookById(id);

        if (target == null) {
            System.out.println("Error: Book not found with that ID.");
        } else if (target.borrowBook()) {
            System.out.println("Success: You have borrowed \"" + target.getBookName() + "\"! Enjoy reading.");
        } else {
            System.out.println("Error: This book is already checked out by someone else.");
        }
    }

    private static void returnBookFlow() {
        System.out.print("Enter Book ID to return: ");
        int id = getSafeIntInput();
        Book target = findBookById(id);

        if (target == null) {
            System.out.println("Error: Incorrect Book ID.");
        } else if (target.returnBook()) {
            System.out.println("Success: Thank you for returning \"" + target.getBookName() + "\".");
        } else {
            System.out.println("Notice: This book is already present in the library repository.");
        }
    }

    // --- HELPER METHODS ---
    private static Book findBookById(int id) {
        for (Book b : bookDatabase) {
            if (b.getBookID() == id) {
                return b;
            }
        }
        return null;
    }

    private static int getSafeIntInput() {
        while (true) {
            try {
                int value = input.nextInt();
                input.nextLine(); // Clear scanner buffer
                return value;
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
                input.nextLine(); // Discard the invalid entry
            }
        }
    }
}
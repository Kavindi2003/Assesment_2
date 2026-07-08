import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

// ==========================================
// 1. SYSTEM MEMBERS (ENCAPSULATION & INHERITANCE)
// ==========================================
abstract class UserProfile {
    private String name;
    private String id;
    private int itemsBorrowedCount = 0;
    private final int MAX_BORROW_LIMIT = 3;

    public UserProfile(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() { return name; }
    public String getId() { return id; }
    public int getBorrowedCount() { return itemsBorrowedCount; }

    public boolean canBorrow() { return itemsBorrowedCount < MAX_BORROW_LIMIT; }
    public void incrementBorrowed() { itemsBorrowedCount++; }
    public void decrementBorrowed() { if (itemsBorrowedCount > 0) itemsBorrowedCount--; }

    public abstract void showDashboard();
}

class LibrarianProfile extends UserProfile {
    public LibrarianProfile(String name, String id) { super(name, id); }

    @Override
    public void showDashboard() {
        System.out.println("\n[LIBRARIAN SESSION] Active Admin: " + getName() + " (ID: " + getId() + ")");
    }
}

class StudentProfile extends UserProfile {
    public StudentProfile(String name, String id) { super(name, id); }

    @Override
    public void showDashboard() {
        System.out.println("\n[STUDENT SESSION] Active Student: " + getName() + " (ID: " + getId() + ")");
        System.out.println("Items currently checked out: " + getBorrowedCount() + "/3");
    }
}

// ==========================================
// 2. MEDIA CATALOG (POLYMORPHISM)
// ==========================================
abstract class MediaItem {
    private int itemID;
    private String title;
    private boolean isCheckedOut = false;
    private LocalDate dueDate = null;

    public MediaItem(int itemID, String title) {
        this.itemID = itemID;
        this.title = title;
    }

    public int getItemID() { return itemID; }
    public String getTitle() { return title; }
    public boolean isCheckedOut() { return isCheckedOut; }
    public LocalDate getDueDate() { return dueDate; }

    public void checkOut() {
        this.isCheckedOut = true;
        this.dueDate = LocalDate.now().plusDays(14); // Due in 2 weeks
    }

    public void checkIn() {
        this.isCheckedOut = false;
        this.dueDate = null;
    }

    public abstract void displayDetails();
}

class PhysicalBook extends MediaItem {
    private String shelfLocation;

    public PhysicalBook(int itemID, String title, String shelfLocation) {
        super(itemID, title);
        this.shelfLocation = shelfLocation;
    }

    @Override
    public void displayDetails() {
        String status = isCheckedOut() ? "[Borrowed | Due: " + getDueDate() + "]" : "[Available]";
        System.out.println("ID: " + getItemID() + " | (Book) \"" + getTitle() + "\" | Location: Section " + shelfLocation + " " + status);
    }
}

class Audiobook extends MediaItem {
    private double runTimeHours;

    public Audiobook(int itemID, String title, double runTimeHours) {
        super(itemID, title);
        this.runTimeHours = runTimeHours;
    }

    @Override
    public void displayDetails() {
        String status = isCheckedOut() ? "[Digital License In Use]" : "[Available for Download]";
        System.out.println("ID: " + getItemID() + " | (Audiobook) \"" + getTitle() + "\" | Length: " + runTimeHours + " hours " + status);
    }
}

// ==========================================
// 3. MAIN CONTROLLER SYSTEM
// ==========================================
public class LibManSysAI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<MediaItem> catalog = new ArrayList<>();

        // Mock Data Seeds
        catalog.add(new PhysicalBook(501, "The Hobbit", "A-3"));
        catalog.add(new PhysicalBook(502, "Dune", "F-1"));
        catalog.add(new Audiobook(701, "Atomic Habits (Unabridged)", 5.5));

        // Setup Mock Users
        LibrarianProfile admin = new LibrarianProfile("Sarah Jenkins", "L-99");
        StudentProfile student = new StudentProfile("Alex Rivera", "S-4402");

        boolean masterExit = false;

        while (!masterExit) {
            System.out.println("\n=============================================");
            System.out.println("      MEDIA HUB LIBRARY MANAGEMENT SYSTEM     ");
            System.out.println("=============================================");
            System.out.println("Select User Session:");
            System.out.println("1. Login as Librarian (" + admin.getName() + ")");
            System.out.println("2. Login as Student (" + student.getName() + ")");
            System.out.println("3. Shutdown System");
            System.out.print("Selection: ");

            int sessionChoice = grabIntInput(scanner);

            if (sessionChoice == 3) {
                masterExit = true;
                System.out.println("System offline. Goodbye!");
                break;
            }

            if (sessionChoice == 1) { // LIBRARIAN WORKFLOW
                boolean exitSession = false;
                while (!exitSession) {
                    admin.showDashboard();
                    System.out.println("1. Catalog a Physical Book");
                    System.out.println("2. Catalog an Audiobook");
                    System.out.println("3. View Entire Media Inventory");
                    System.out.println("4. Switch User Session");
                    System.out.print("Action: ");

                    int action = grabIntInput(scanner);
                    if (action == 4) { exitSession = true; break; }

                    switch (action) {
                        case 1:
                            System.out.print("Enter Book Title: ");
                            String bTitle = scanner.nextLine();
                            System.out.print("Assign Unique ID Number: ");
                            int bId = grabIntInput(scanner);
                            System.out.print("Assign Shelf Location (e.g., B-4): ");
                            String loc = scanner.nextLine();

                            catalog.add(new PhysicalBook(bId, bTitle, loc));
                            System.out.println("System updated: Physical book registered.");
                            break;

                        case 2:
                            System.out.print("Enter Audiobook Title: ");
                            String aTitle = scanner.nextLine();
                            System.out.print("Assign Unique ID Number: ");
                            int aId = grabIntInput(scanner);
                            System.out.print("Enter Runtime Duration (Hours): ");
                            double time = scanner.nextDouble();
                            scanner.nextLine(); // Clear scanner buffer

                            catalog.add(new Audiobook(aId, aTitle, time));
                            System.out.println("System updated: Audiobook stream initialized.");
                            break;

                        case 3:
                            displayInventory(catalog);
                            break;
                        default:
                            System.out.println("Invalid selection.");
                    }
                }
            } else if (sessionChoice == 2) { // STUDENT WORKFLOW
                boolean exitSession = false;
                while (!exitSession) {
                    student.showDashboard();
                    System.out.println("1. Browse Inventory & Check Availability");
                    System.out.println("2. Search Media By Title Keyword");
                    System.out.println("3. Borrow Item");
                    System.out.println("4. Return Item");
                    System.out.println("5. Switch User Session");
                    System.out.print("Action: ");

                    int action = grabIntInput(scanner);
                    if (action == 5) { exitSession = true; break; }

                    switch (action) {
                        case 1:
                            displayInventory(catalog);
                            break;

                        case 2: // Keyword Search Service
                            System.out.print("Enter search keyword: ");
                            String query = scanner.nextLine().toLowerCase();
                            System.out.println("\n--- Search Results ---");
                            boolean matchingMatches = false;
                            for (MediaItem item : catalog) {
                                if (item.getTitle().toLowerCase().contains(query)) {
                                    item.displayDetails();
                                    matchingMatches = true;
                                }
                            }
                            if (!matchingMatches) System.out.println("No items match your search term.");
                            break;

                        case 3: // Advanced Borrowing
                            if (!student.canBorrow()) {
                                System.out.println("Hold request denied! You have reached your max checkout cap of 3 items.");
                                break;
                            }
                            System.out.print("Enter Media ID to borrow: ");
                            int targetBorrow = grabIntInput(scanner);
                            boolean targetFound = false;

                            for (MediaItem item : catalog) {
                                if (item.getItemID() == targetBorrow) {
                                    targetFound = true;
                                    if (item.isCheckedOut()) {
                                        System.out.println("Item is currently unavailable.");
                                    } else {
                                        item.checkOut();
                                        student.incrementBorrowed();
                                        System.out.println("Success! Verified check out. Please return by: " + item.getDueDate());
                                    }
                                    break;
                                }
                            }
                            if (!targetFound) System.out.println("Error: System ID match failed.");
                            break;

                        case 4: // Advanced Returning
                            System.out.print("Enter Media ID to return: ");
                            int targetReturn = grabIntInput(scanner);
                            boolean returnFound = false;

                            for (MediaItem item : catalog) {
                                if (item.getItemID() == targetReturn) {
                                    returnFound = true;
                                    if (!item.isCheckedOut()) {
                                        System.out.println("This item is not marked as borrowed.");
                                    } else {
                                        item.checkIn();
                                        student.decrementBorrowed();
                                        System.out.println("Success! Core log updated. Thank you for returning your media.");
                                    }
                                    break;
                                }
                            }
                            if (!returnFound) System.out.println("Error: System ID match failed.");
                            break;

                        default:
                            System.out.println("Invalid selection.");
                    }
                }
            } else {
                System.out.println("Invalid input choice.");
            }
        }
        scanner.close();
    }

    // Static Utility Method to completely isolate nextInt() scanner skip bugs
    private static int grabIntInput(Scanner scan) {
        int val = scan.nextInt();
        scan.nextLine();
        return val;
    }

    private static void displayInventory(ArrayList<MediaItem> inventory) {
        System.out.println("\n--- CENTRAL CATALOG ARCHIVE ---");
        if (inventory.isEmpty()) {
            System.out.println("Archive is completely bare.");
        } else {
            for (MediaItem item : inventory) {
                item.displayDetails();
            }
        }
    }
}

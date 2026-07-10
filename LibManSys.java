import java.util.ArrayList;
import java.util.Scanner;

abstract class LibMem{
    public String MemName;
    public String MemID;

    public LibMem(String MemName, String MemID){
        this.MemName=MemName;
        this.MemID= MemID;
    }

    public String getName() {return MemName;}
    public String getID() {return MemID;}
    abstract void displayinfo();

}
class Librarian extends LibMem{
    public Librarian(String MemName, String MemID) {
        super(MemName, MemID);
    }

    @Override
    void displayinfo() {
        System.out.println("Librarian Name: " + getName());
    }
}


class Student extends LibMem {
    public Student(String MemName, String MemID) {
        super(MemName, MemID);
    }

    @Override
    void displayinfo() {
        System.out.println("Student Name: " + getName());
    }
}

class Book {
    public String BookName;
    public int BookID;
    Boolean Borrow_Status = false;



    public Book(String BookName, int BookID, Boolean BorrowStatus) {
        this.BookName = BookName;
        this.BookID = BookID;
        this.Borrow_Status = BorrowStatus;
    }

    public String getBookName() {
        return BookName;
    }
    public int getBookID() {
        return BookID;
    }
    public String getBorrow_Status() {
        String availability ;
        if (Borrow_Status==false){
            availability="Available";
        }else{
            availability="Not Available";
        }
        return availability;
    }
    public void displayinfo(){
        System.out.println("Book Name: " + getBookName());
        System.out.println("Book ID: " + getBookID());
        System.out.println("Book Availability: " + getBorrow_Status());

}

}
/*class FictionBook extends Book{
    public String BookGenre;
    public Book(String BookName, int BookID, String BookGenre){
        this.BookName=BookName;
        this.BookID=BookID;
        this.BookGenre=BookGenre;
    }


}

 */


public class LibManSys {
    public static void main(String[] args) {
        int n1, n2 = 0,n3;
        Boolean stop = false;
        Scanner input = new Scanner(System.in);
        String BookName;
        int BookID;
        Boolean Borrow_Status = false;
        ArrayList<Book> list = new ArrayList<Book>();

        while (stop != true) {
            System.out.println("WELCOME TO THE LIBRARY MANAGEMENT SYSTEM");
            System.out.println("What is your Role? (Enter the number infront of your role)");
            System.out.println("1.Librarian");
            System.out.println("2.Student");
            System.out.println("3.Exit");

            n1 = input.nextInt();
            input.nextLine();
            switch (n1) {
                case 1:
                    System.out.println("What Action do you want to carry out?");
                    System.out.println("1.Add Book");
                    System.out.println("2.View Books");
                    n2 = input.nextInt();
                    input.nextLine();
                    break;
                case 2:
                    System.out.println("What Action do you want to carry out?");
                    System.out.println("1.Borrow Book");
                    System.out.println("2.View Books");
                    n3 = input.nextInt();
                    input.nextLine();
                    if (n3==1)  n2=3;
                    if (n3 == 2) n2 = 2;
                    break;
                case 3:
                    stop=true;
                    break;
                default:
                    System.out.println("Invalid Input");
                    continue;


            }

            switch (n2) {
                case 1:
                    System.out.println("Enter Book Name: ");
                    BookName = input.nextLine();
                    System.out.println("Enter Book ID(int): ");
                    BookID = input.nextInt();
                    input.nextLine();

                    list.add(new Book(BookName, BookID, false));
                    System.out.println("Book added successfully");
                    break;
                case 2:
                    if (list.isEmpty()) {
                        System.out.println("No Books in the library yet");
                    } else {
                        for (Book b : list) {
                            b.displayinfo();
                            System.out.println("-------------------");
                        }
                    }
                    break;
                case 3:
                    System.out.println("Enter Book ID ");
                    BookID = input.nextInt();
                    input.nextLine();
                    boolean found = false;

                    for (Book b : list) {
                        if (BookID == b.getBookID()) {
                            if (b.Borrow_Status) {
                                System.out.println("This book is already borrowed by someone else.");
                            } else {
                                b.Borrow_Status = true;
                                System.out.println("You have Borrowed the book ^_^");
                            }
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Book is unavailable (Invalid ID).");
                    }
                    break;


            }
        }
    }

}

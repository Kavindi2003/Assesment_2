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

}
class Student extends LibMem {
}

abstract class Book {
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
    public String getBookID() {
        return BookID;
    }
    public Boolean getBorrow_Status() {
        return Borrow_Status;
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
}

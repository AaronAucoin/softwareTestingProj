import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        Main method so maunal testing can be done
    }
}

class Library {
    private List<Book> allBooksInLibrary;
    private List<Book> loanedBooks;
    private List<Double> memberIDs;

    public Library() {
        this.allBooksInLibrary = new ArrayList<>();
    }

    public void addBook(Book book) {
        allBooksInLibrary.add(book);
    }

}

class Book {
    private String name;
    private String author;
    private int year;
    private int ISBN;
    private int bookID;
    private boolean isAvailable;
    private String genre;

    public Book(String name, String author, int year, int ISBN, int bookID, boolean isAvailable, String genre) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.ISBN = ISBN;
        this.bookID = bookID;
        this.isAvailable = isAvailable;
        this.genre = genre;
    }

    public boolean checkAvailability() {
        return isAvailable;
    }

    public void updateBook(String name, String author, int year, int ISBN, int bookID, boolean isAvailable, String genre) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.ISBN = ISBN;
        this.bookID = bookID;
        this.isAvailable = isAvailable;
        this.genre = genre;
    }

    public void getBookInfo(){
        System.out.println("Name: " + name);
        System.out.println("Author: " + author);
        System.out.println("Year: " + year);
        System.out.println("ISBN: " + ISBN);
        System.out.println("Book ID: " + bookID);
        System.out.println("Available: " + isAvailable);
        System.out.println("Genre: " + genre);
    }
}

class Member{
    private String name;
    private String email;
    private String memberID;
    private List<Book> borrowedBookList;

    public Member(String name, String email, String memberID) {
        this.name = name;
        this.email = email;
        this.memberID = memberID;
        this.borrowedBookList = new ArrayList<>();
    }

    void printMemberInfo(){
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("MemberID: " + memberID);
        getBorrowedBookList();
    }

    private void getBorrowedBookList() {
        if(borrowedBookList == null || borrowedBookList.isEmpty()){
            System.out.println("user " + name + " has not borrowed books");
        }
        else{
            System.out.println(name + "'s borrowed book list:");
            for(Book book : borrowedBookList){
                System.out.print(book + ", ");
            }
            System.out.println();
        }
    }

    private void addBorrowedBook(Book book) {
        borrowedBookList.add(book);
    }

    private void updateMemberInfo(String name, String email, String memberID) {
        this.name = name;
        this.email = email;
        this.memberID = memberID;
    }

    private void removeBorrowedBook(Book book) {
        if(borrowedBookList.contains(book)){
            borrowedBookList.remove(book);
        }
        else{
            System.out.println("user " + name + " has not borrowed " + book);
        }
    }
}


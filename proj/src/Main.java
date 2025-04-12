import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
//        Main method so manual testing can be done
    }
}

class Library {
    // Not sure what this is supposed to be
    // possible a list of unloaned book IDs, that seems redundant though
    private List<Integer> AvailableBookIDs;

    // list of all books possessed by the library
    // i think this should include currently loaned books
    private List<Book> allBooksInLibrary;

    // i think we should implement this as a hashmap of books and the member checking it out
    // this makes it easier to access these logically linked values
    private HashMap<Book, Member> loanedBooks;

    private List<Integer> memberIDs; // list of Unique Integer values denoting each member

    // constructor for a library object
    public Library() {
        this.allBooksInLibrary = new ArrayList<>();
        this.loanedBooks = new HashMap<>();
        this.memberIDs = new ArrayList<>();
    }

    // adds a Book to allBooksInLibrary
    public void addBook(Book book) {
        allBooksInLibrary.add(book);
    }

    // removes a Book from allBooksInLibrary
    public void removeBook(Book book) {allBooksInLibrary.remove(book);}

    // adds a Book, Member pair to loanedBooks HashMap
    // adds a Book to the Member's BorrowedBooksList
    public void checkoutBook(Book book, Member member) {
        loanedBooks.put(book,member);
        member.addBorrowedBook(book);
    }

    // creates a member object wih specified name and unique member ID
    // adds new member's ID to memberID list
    public void addMember(String name, String email) {
        Member newMember = new Member(name, email, (int) (memberIDs.size() + 1));
        memberIDs.add(newMember.getMemberId());
    }

    // removes a member's ID from the memberID list
    // not sure what else to do for this
    // maybe there will be a list of members stored somewhere to remove this from??
    public void removeMember(Member member) {
        memberIDs.remove(member.getMemberId());
    }

    // returns if a book object is available
    // I think in the long run this should be modified to
    // return availability of a book given only its title
    public boolean bookAvailability(Book book) {
        return book.checkAvailability();
    }

    // returns which member checked out a book
    // references the loanedBooks hashmap for the member object and prints their info
    // we could change this at some point to only return the name
    // also MAYBE be changed to only need a books name instead of the object
    public void whoHasBook(Book book) {
        loanedBooks.get(book).printMemberInfo();
    }

    // Not sure what this is intended to do
    // does it like print all member info, or just names
    // or does it return a list of Member objects, idk
    public void getAllMembers() {}

    // takes a book's name and returns the corresponding Book object
    public void findBookIdByName(String bookName) {
        // idk how to implement rn
        // might involve using the list of all books and iterating
        // through it until finding the object with a matching name
    }

    // removes a Book, Member pair from the loaned books HashMap
    // removes a Book from the member's LoanedBooksList
    public void returnBook(Book book) {
        Member member = loanedBooks.remove(book);
        member.removeBorrowedBook(book);
    }
}

class Book {
    private String name; // title of the book
    private String author; // author of the book
    private int year; // books year of publication
    private int ISBN; // idk what this is??
    private Integer bookID; // unique ID number for book in this library

    // true means a book is not currently loaned and is available to be loaned
    // false means a book is currently loaned to a member and is not available to be loaned
    private boolean isAvailable;
    private String genre; //genre of the book

    // constructor for a Book object
    public Book(String name, String author, int year, int ISBN, int bookID, boolean isAvailable, String genre) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.ISBN = ISBN;
        this.bookID = bookID;
        this.isAvailable = isAvailable;
        this.genre = genre;
    }

    // returns if a book is available given a Book object
    // could MAYBE be modified to only require a book's name instead of object
    public boolean checkAvailability() {return isAvailable;}

    // changes a book's values to the new values
    // could be changed to have nulls for parameters to not update those things
    public void updateBook(String name, String author, int year, int ISBN, int bookID, boolean isAvailable, String genre) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.ISBN = ISBN;
        this.bookID = bookID;
        this.isAvailable = isAvailable;
        this.genre = genre;
    }

    // creates a String of a Book's info and prints it
    // returns a list of book info points
    public List<String> getBookInfo(){
        String bookInfoString = "";
        bookInfoString += "Name: " + name + "\n";
        bookInfoString+= "Author: " + author + "\n";
        bookInfoString += "Year: " + year + "\n";
        bookInfoString += "ISBN: " + ISBN + "\n";
        bookInfoString += "Book ID: " + bookID + "\n";
        bookInfoString += "Available: " + isAvailable + "\n";
        bookInfoString += "Genre: " + genre + "\n";
        System.out.println(bookInfoString);
        List<String> bookInfo = new ArrayList<>();
        bookInfo.add(this.name);
        bookInfo.add(this.author);
        bookInfo.add(Integer.toString(this.year));
        bookInfo.add(Integer.toString(this.ISBN));
        bookInfo.add(Integer.toString(this.bookID));
        bookInfo.add(Boolean.toString(this.isAvailable));
        bookInfo.add(this.genre);
        return bookInfo;
    }
}

class Member{
    private String name; // member's Legal first and last name ("Reed, Steven")
    private String email; // member's preferred email address ("sreed43@lsu.edu")
    private Integer memberID; // unique integer ID for each member object
    private List<Book> borrowedBookList; // list of books this Member has borrowed

    // constructor for the Member object
    public Member(String name, String email, Integer memberID) {
        this.name = name;
        this.email = email;
        this.memberID = memberID;
        this.borrowedBookList = new ArrayList<>();
    }

    // prints the info of a Member
    // I think this should be modified to print a member's info and then return it
    public void printMemberInfo(){
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("MemberID: " + memberID);
        getBorrowedBookList();
    }

    // returns a Member's Identification number
    public Integer getMemberId() {
        return this.memberID;
    }

    // prints then returns a Member's borrowed book list
    private List<Book> getBorrowedBookList() {
        if(borrowedBookList == null) {
            return null;
        }
        else{
            System.out.println(name + "'s borrowed book list:");
            for(Book book : borrowedBookList){
                book.getBookInfo();
            }
            System.out.println();
            return borrowedBookList;
        }
    }

    // adds a book to a Member's borrowed book list
    // used when Library class checks out a book
    public void addBorrowedBook(Book book) {
        borrowedBookList.add(book);
    }

    // updates a member's info to the specified values
    // could be modified to accept nulls for unchanging values
    public void updateMemberInfo(String name, String email, Integer memberID) {
        this.name = name;
        this.email = email;
        this.memberID = memberID;
    }

    // removes a book from a member's borrowed book list
    // used when Library class returns a book
    public void removeBorrowedBook(Book book) {
        if(borrowedBookList.contains(book)){
            borrowedBookList.remove(book);
        }
        else{
            System.out.println("user " + name + " has not borrowed " + book);
        }
    }
}


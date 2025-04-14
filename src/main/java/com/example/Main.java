package com.example;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Main method so manual testing can be done
        Library library = new Library(); //Create a library
        Interface ui = new Interface();
        ui.doInterface(library);
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
    private List<Member> allMembers;//list of all members in the Library
//
    // constructor for a library object
    public Library() {
        this.allBooksInLibrary = new ArrayList<>();
        this.loanedBooks = new HashMap<>();
        this.memberIDs = new ArrayList<>();
        this.allMembers = new ArrayList<>();
    }

    // adds a Book to allBooksInLibrary
    public void addBook(Book book) {
        allBooksInLibrary.add(book);
    }

    // removes a Book from allBooksInLibrary
    public void removeBook(Book book) {
        allBooksInLibrary.remove(book);
    }

    // generates a new unique bookID to add a book to the library
    public int generateBookID () {
        return allBooksInLibrary.size() + 1;
    }

    // adds a Book, Member pair to loanedBooks HashMap
    // adds a Book to the Member's BorrowedBooksList
    public void checkoutBook(Book book, Member member) {
        loanedBooks.put(book, member);
        member.addBorrowedBook(book);
    }

    // removes a Book, Member pair from the loaned books HashMap
    // removes a Book from the member's LoanedBooksList
    public void returnBook(Book book) {
        Member member = loanedBooks.remove(book);
        member.removeBorrowedBook(book);
    }

    // creates a member object wih specified name and unique member ID
    // adds new member's ID to memberID list
    public Member addMember(String name, String email) {
        Member newMember = new Member(name, email, (int) (memberIDs.size() + 1));
        memberIDs.add(newMember.getMemberId());
        allMembers.add(newMember);
        return newMember;
    }

    // removes a member's ID from the memberID list
    // not sure what else to do for this
    // maybe there will be a list of members stored somewhere to remove this from??
    // we should add logging or some verification type for this
    public void removeMember(Member member) {
        if(member == null) {
            System.out.println("Attempted to remove a null Member");
            return;
        }
        memberIDs.remove(member.getMemberId());
        allMembers.remove(member);
    }

    // finds member object from allMember list given name (last, first) and memberID
    public Member getMember(String memberName, int memberID) {
        Member member = allMembers.get(memberID);
        if(member.getName().equals(memberName)) {
            return member;
        }
        System.out.println("Member not found");
        return null;
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
    public void getAllMembers() {
    }

    // takes a book's name and returns the corresponding Book object
    public Book findBookIdByName(String title) {
        for (Book book : allBooksInLibrary) {
            if(book.getBookInfo().get(0).equals(title)) {
                return(book);
            }
        }
        System.out.println("Book" + title + "not found");
        return null;
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
    // creates its own unique bookID so librarian doesn't need to worry about that
    public Book(String name, String author, int year, int ISBN, boolean isAvailable, String genre, Library library) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.ISBN = ISBN;
        this.isAvailable = isAvailable;
        this.genre = genre;
        this.bookID = library.generateBookID();
    }

    // returns if a book is available given a Book object
    // could MAYBE be modified to only require a book's name instead of object
    public boolean checkAvailability() {
        return isAvailable;
    }

    // changes a book's values to the new values
    // could be changed to have nulls for parameters to not update those things
    // bookID should not be manually changeable
    public void updateBook(String name, String author, int year, int ISBN, boolean isAvailable, String genre) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.ISBN = ISBN;
        this.isAvailable = isAvailable;
        this.genre = genre;
    }

    // creates a String of a Book's info and prints it
    // returns a list of book info points
    public List<String> getBookInfo() {
        String bookInfoString = "";
        bookInfoString += "Name: " + name + "\n";
        bookInfoString += "Author: " + author + "\n";
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

class Member {
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
    public void printMemberInfo() {
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("MemberID: " + memberID);
        getBorrowedBookList();
    }

    // returns a Member's Identification number
    public Integer getMemberId() {
        return this.memberID;
    }

    // returns a member's name
    public String getName() {
        return this.name;
    }

    // prints then returns a Member's borrowed book list
    private List<Book> getBorrowedBookList() {
        if (borrowedBookList == null) {
            return null;
        } else {
            System.out.println(name + "'s borrowed book list:");
            for (Book book : borrowedBookList) {
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
    // member ID should not be changeable
    public void updateMemberInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // removes a book from a member's borrowed book list
    // used when Library class returns a book
    public void removeBorrowedBook(Book book) {
        if (borrowedBookList.contains(book)) {
            borrowedBookList.remove(book);
        } else {
            System.out.println("user " + name + " has not borrowed " + book);
        }
    }
}

class Interface {
    // only one function that has a loop of looking for commands and executing them
    public void doInterface(Library library) {
        Scanner scanner = new Scanner(System.in);
        String input;
        String bookAuthor;
        String bookName;
        String bookGenre;
        int bookYear;
        int bookISBN;
        Book book;
        String memberName;
        String memberEmail;
        Member member;

        System.out.println("Welcome to the Library CLI! Type 'exit' to quit.");
        System.out.println("Type 'help' to see all commands.");

        while (true) {
            System.out.print("> ");
            input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("10") || input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            switch (input.toLowerCase()) {
                case "hello":
                    System.out.println("Hi there!");
                    break;
                case "9":
                    System.out.println("choose number to select option");
                    System.out.println("1. Add Book");
                    System.out.println("2. Remove Book");
                    System.out.println("3. Update Book");
                    System.out.println("4. Add Member");
                    System.out.println("5. Remove Member");
                    System.out.println("6. Checkout Book");
                    System.out.println("7. Return Book");
                    System.out.println("8. Print Member Info");
                    System.out.println("9. Help");
                    System.out.println("10. Exit");
                    break;
                case "1":
                    // asks for all info about a book ands adds it to library book list
                    // always sets new book to available and bookID is auto-generated by Book constructor
                    System.out.println("Enter Book Title");
                    input = scanner.nextLine().trim();
                    bookName = input.toLowerCase();
                    System.out.println("Enter Book Author");
                    input = scanner.nextLine().trim();
                    bookAuthor = input.toLowerCase();
                    System.out.println("Enter Book Year");
                    input = scanner.nextLine().trim();
                    bookYear = Integer.parseInt(input);
                    System.out.println("Enter Book ISBN");
                    input = scanner.nextLine().trim();
                    bookISBN = Integer.parseInt(input);
                    System.out.println("Enter Book Genre");
                    input = scanner.nextLine().trim();
                    bookGenre = input.toLowerCase();
                    book = new Book(bookName, bookAuthor, bookYear,bookISBN, true, bookGenre, library);
                    library.addBook(book);
                    break;
                case "2":
                    // removes a book with a given title from the library
                    System.out.println("Enter Book Title");
                    input = scanner.nextLine().trim();
                    String title = input.toLowerCase();
                    book = library.findBookIdByName(title);
                    library.removeBook(book);
                    break;
                case "3":
                    // updates a given books info in the library
                    System.out.println("Enter Book Title");
                    String updateTitle = scanner.nextLine().trim().toLowerCase();
                    book = library.findBookIdByName(updateTitle);
                    if (book != null) {
                        System.out.println("Enter new Title:");
                        bookName = scanner.nextLine();
                        System.out.println("Enter new Author:");
                        bookAuthor = scanner.nextLine();
                        System.out.println("Enter new Year:");
                        bookYear = Integer.parseInt(scanner.nextLine());
                        System.out.println("Enter new ISBN:");
                        bookISBN = Integer.parseInt(scanner.nextLine());
                        System.out.println("Enter new Genre:");
                        bookGenre = scanner.nextLine();
                        System.out.println("Is the book available? (true/false)");
                        boolean isAvailable = Boolean.parseBoolean(scanner.nextLine());
                        book.updateBook(bookName, bookAuthor, bookYear, bookISBN, isAvailable, bookGenre);
                    }

                    break;
                case "4":
                    // asks for all necessary member info (name, email)
                    // constructor auto-generates memberID and empty borrowedBookList
                    System.out.println("Enter Member Name (Last, First)");
                    memberName = scanner.nextLine().trim();
                    System.out.println("Enter Member email");
                    memberEmail = scanner.nextLine().trim(); // we could maybe do a check for legit emails later?
                    member = library.addMember(memberName, memberEmail);
                    member.printMemberInfo();
                    break;
                case "5":
                    // asks for a member's name and ID
                    // removes that member from the memberID list and allMembers list
                    System.out.println("Enter Member Name (Last, First)");
                    memberName = scanner.nextLine().trim();
                    System.out.println("Enter Member ID");
                    int memberID = Integer.parseInt(scanner.nextLine().trim());
                    member = library.getMember(memberName,memberID);
                    library.removeMember(member);
                    break;
                case "6":
                    break;
                case "7":
                    break;
                case "8":
                    break;
                default:
                    System.out.println("Unknown command: " + input);
            }
        }

        scanner.close();
    }
}

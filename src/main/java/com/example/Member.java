package com.example;
import java.util.*;

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
        printBorrowedBooks();
    }

    // returns a Member's Identification number
    public Integer getMemberId() {
        return this.memberID;
    }

    // returns a member's name
    public String getName() {
        return this.name;
    }


    // checks if a member has borrowed a book
    public boolean hasBorrowed(Book book) {
        return borrowedBookList.contains(book);
    }
    public void printBorrowedBooks() {
        System.out.println(name + "'s borrowed book list:");
        for (Book book : borrowedBookList) {
            book.getBookInfo();
        }
        System.out.println();
    }
    public List<Book> getBorrowedBooks() {
        return new ArrayList<>(borrowedBookList); 
    }
    // adds a book to a Member's borrowed book list
    // used when Library class checks out a book
    public List<Book> addBorrowedBook(Book book) {
        borrowedBookList.add(book);
        return borrowedBookList;
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

    public String getEmail() {
        return this.email;
    }
}
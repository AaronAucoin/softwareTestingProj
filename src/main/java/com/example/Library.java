package com.example;

import java.util.*;


public class Library {
    // list of all books possessed by the library
    // i think this should include currently loaned books
    private List<Book> allBooksInLibrary;

    // i think we should implement this as a hashmap of books and the member
    // checking it out
    // this makes it easier to access these logically linked values
    private HashMap<Book, Member> loanedBooks;
    private List<Integer> memberIDs; // list of Unique Integer values denoting each member
    private List<Member> allMembers;// list of all members in the Library
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

    // gets all books in a library
    public List<Book> getAllBooks() {
        return allBooksInLibrary;
    }

    // generates a new unique bookID to add a book to the library
    public int generateBookID() {
        return allBooksInLibrary.size() + 1;
    }

    // adds a Book, Member pair to loanedBooks HashMap
    // adds a Book to the Member's BorrowedBooksList
    public void checkoutBook(Book book, Member member) {
        loanedBooks.put(book, member);
        member.addBorrowedBook(book);
        book.updateBook(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(false),
                Optional.empty());
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
        if (member == null) {
            System.out.println("Attempted to remove a null Member");
            return;
        }
        memberIDs.remove(member.getMemberId());
        allMembers.remove(member);
    }

    // finds member object from allMember list given name (last, first) and memberID
    public Member getMember(String memberName, int memberID) {
        if (memberName == null) {
            for (Member member : allMembers) {
                if (member.getMemberId() == memberID) {
                    return member;
                }
            }
        } else {
            for (Member member : allMembers) {
                if (member.getMemberId() == memberID && member.getName().equalsIgnoreCase(memberName)) {
                    return member;
                }
            }
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
    // references the loanedBooks hashmap for the member object and prints their
    // info
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
            if (book.getBookInfo().get(0).equalsIgnoreCase(title)) {
                return (book);
            }
        }
        System.out.println("Book" + title + "not found");
        return null;
    }
}
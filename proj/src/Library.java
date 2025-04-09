import com.sun.source.tree.LambdaExpressionTree;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Library {
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
    private String title;
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
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

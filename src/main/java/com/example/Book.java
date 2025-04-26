package com.example;
import java.util.*;

public class Book {
    private String name; // title of the book
    private String author; // author of the book
    private int year; // books year of publication
    private String ISBN; // unique ID for every publication of every book
    private Integer bookID; // unique ID number for book in this library

    // true means a book is not currently loaned and is available to be loaned
    // false means a book is currently loaned to a member and is not available to be loaned
    private boolean isAvailable;
    private String genre; //genre of the book

    // constructor for a Book object
    // creates its own unique bookID so librarian doesn't need to worry about that
    public Book(String name, String author, int year, String ISBN, String genre, Library library) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.ISBN = ISBN;
        this.isAvailable = true;
        this.genre = genre;
        this.bookID = library.generateBookID();
    }

    // returns if a book is available given a Book object
    // could MAYBE be modified to only require a book's name instead of object
    public boolean checkAvailability() {
        return isAvailable;
    }

    // Returns a book objects title
    public String getName() {
        return name;
    }

    // changes a book's values to the new values
    // could be changed to have nulls for parameters to not update those things
    // bookID should not be manually changeable
    public void updateBook(Optional<String> name,
                           Optional<String> author,
                           Optional<Integer> year,
                           Optional<String> ISBN,
                           Optional<Boolean> isAvailable,
                           Optional<String> genre) {
        name.ifPresent(n -> this.name = n);
        author.ifPresent(a -> this.author = a);
        year.ifPresent(y -> this.year = y);
        ISBN.ifPresent(i -> this.ISBN = i);
        isAvailable.ifPresent(avail -> this.isAvailable = avail);
        genre.ifPresent(g -> this.genre = g);
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
        bookInfo.add(this.ISBN);
        bookInfo.add(Integer.toString(this.bookID));
        bookInfo.add(Boolean.toString(this.isAvailable));
        bookInfo.add(this.genre);
        return bookInfo;
    }
}
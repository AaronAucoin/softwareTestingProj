package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.*;
import java.util.stream.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;

public class MemberTest {
    private Library library;
    private Book book;
    private Member member;

    @BeforeEach
    void setup() {
        library = mock(Library.class);
        // its just as easy for me to use a real book object
        book = new Book(
                "The Lion, The Witch, and The Wardrobe",
                "CS Lewis",
                1950,
                "9780001831803",
                "Fantasy",
                library
        );
        member = new Member("Reed, Steven", "sreed43@lsu.edu", 0);
    }

    // Test getting member ID and name
    @Test
    void getMemberIdAndName() {
        assertEquals(0, member.getMemberId());
        assertEquals("Reed, Steven", member.getName());
    }

    // Testing adding and removing a book from the member's borrowedBooksList
    @Test
    void addAndRemoveBook() {
        List<Book> borrowedList = member.addBorrowedBook(book);
        assertEquals(1, borrowedList.size());
        assertEquals(book, borrowedList.get(0));
    }

    // Testing removing a book from a member's borrowedBookList
    @Test
    void removeBook() {
        List<Book> borrowedList = member.addBorrowedBook(book);
        assertEquals(1, borrowedList.size());
        member.removeBorrowedBook(book);
        assertEquals(0, borrowedList.size());
    }

    // Testing updateMemberInfo
    @Test
    void updateMemberInfo() {
        member.updateMemberInfo("Miles, Joshua", "jmiles16@lsu.edu");
        assertEquals("Miles, Joshua", member.getName());
        assertEquals("jmiles16@lsu.edu", member.getEmail());
    }

    // Testing get borrowed Book List
    @Test
    void getBorrowedBooks() {
        Book book1 = new Book(
                "The Hobbit",
                "J.R.R. Tolkien",
                1937,
                "9780547928227",
                "Fantasy",
                library
        );

        Book book2 = new Book(
                "To Kill a Mockingbird",
                "Harper Lee",
                1960,
                "9780060935467",
                "Fiction",
                library
        );

        Book book3 = new Book(
                "1984",
                "George Orwell",
                1949,
                "9780451524935",
                "Dystopian",
                library
        );

        Book book4 = new Book(
                "Pride and Prejudice",
                "Jane Austen",
                1813,
                "9780141439518",
                "Romance",
                library
        );

        Book book5 = new Book(
                "A Brief History of Time",
                "Stephen Hawking",
                1988,
                "9780553380163",
                "Science",
                library
        );

        member.addBorrowedBook(book);
        member.addBorrowedBook(book1);
        member.addBorrowedBook(book2);
        member.addBorrowedBook(book3);
        member.addBorrowedBook(book4);
        member.addBorrowedBook(book5);

        List<Book> borrowedBookList = member.getBorrowedBooks();
        assertEquals(book, borrowedBookList.get(0));
        assertEquals(book1, borrowedBookList.get(1));
        assertEquals(book2, borrowedBookList.get(2));
        assertEquals(book3, borrowedBookList.get(3));
        assertEquals(book4, borrowedBookList.get(4));
        assertEquals(book5, borrowedBookList.get(5));
    }

}

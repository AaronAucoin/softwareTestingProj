package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private Library library;
    private Book book;
    private Member member;

    @BeforeEach
    public void setUp() {
        library = new Library();
        book = new Book("Test Book", "Author Name", 2020, "123456",  "Fiction", library);
        member = new Member("Doe, John", "john.doe@example.com", 1);
    }

    @Test
    public void testAddBook() {
        library.addBook(book);
        Book foundBook = library.findBookIdByName("Test Book");
        assertNotNull(foundBook);
        assertEquals("Test Book", foundBook.getBookInfo().get(0));
    }

    @Test
    public void testRemoveBook() {
        library.addBook(book);
        library.removeBook(book);
        assertNull(library.findBookIdByName("Test Book"));
    }

    @Test
    public void testAddMember() {
        Member addedMember = library.addMember("Doe, John", "john.doe@example.com");
        assertEquals("Doe, John", addedMember.getName());
        assertEquals(1, addedMember.getMemberId());
    }

    @Test
    public void testRemoveMember() {
        Member addedMember = library.addMember("Doe, John", "john.doe@example.com");
        library.removeMember(addedMember);
        assertNull(library.getMember("Doe, John", 1));
    }

    @Test
    public void testCheckoutAndReturnBook() {
        library.addBook(book);
        Member addedMember = library.addMember("Doe, John", "john.doe@example.com");
        library.checkoutBook(book, addedMember);

        assertFalse(book.checkAvailability()); // book availability not updated in code
        // To improve design: set `isAvailable = false` in `checkoutBook()` and `true` in `returnBook()`

        library.returnBook(book);
        // Again, `isAvailable` should be updated but isn't. Fix this in your main logic.
        // whoever wrote this comment could have fixed this lol
    }
}


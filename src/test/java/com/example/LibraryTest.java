package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private Library library;
    private Book book;
    private Member member;

    @BeforeEach
    public void setUp() {
        library = new Library();
        book = new Book("Test Book", "Author Name", 2020, "123456", "Fiction", library);
        member = library.addMember("Doe, John", "john.doe@example.com");
    }

    @Test
    public void testAddBook() {
        library.addBook(book);
        Book foundBook = library.findBookIdByName("test book");
        assertNotNull(foundBook);
        assertEquals("test book", foundBook.getBookInfo().get(0).toLowerCase());
    }

    @Test
    public void testRemoveBook() {
        library.addBook(book);
        library.removeBook(book);
        Book foundBook = library.findBookIdByName("test book");
        assertNull(foundBook);
    }

    @Test
    public void testAddMember() {
        assertEquals("Doe, John", member.getName());
        assertTrue(member.getMemberId() > 0);
    }

    @Test
    public void testRemoveMember() {
        int id = member.getMemberId();
        library.removeMember(member);
        Member removed = library.getMember("Doe, John", id);
        assertNull(removed);
    }

    @Test
    public void testCheckoutBook() {
        library.addBook(book);
        library.checkoutBook(book, member);
        // simulate marking unavailable (same as CLI)
        book.updateBook(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(false), Optional.empty());
        assertFalse(book.checkAvailability());

        // member should have this book
        assertTrue(memberHasBook(member, book));
    }

    @Test
    public void testReturnBook() {
        library.addBook(book);
        library.checkoutBook(book, member);
        book.updateBook(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(false), Optional.empty());

        library.returnBook(book);
        // simulate marking available (same as CLI)
        book.updateBook(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(true), Optional.empty());

        assertTrue(book.checkAvailability());
        assertFalse(memberHasBook(member, book));
    }

    @Test
    public void testFindBookIdByNameCaseInsensitive() {
        library.addBook(book);
        Book found = library.findBookIdByName("TEST BOOK");
        assertNotNull(found);
        assertEquals("Test Book", found.getBookInfo().get(0)); // getBookInfo().get(0) = name
    }

    // Helper method to verify if member borrowed the book
    private boolean memberHasBook(Member member, Book book) {
        return member.getBorrowedBooks().contains(book);
    }
    @Test
    public void testBookAvailabilityFlow() {
        library.addBook(book);
        assertTrue(library.bookAvailability(book));

        // Checkout the booookkkkkkkkkkkkkkkkkkkkkkk
        library.checkoutBook(book, member);
        book.updateBook(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(false), Optional.empty());
        assertFalse(library.bookAvailability(book)); //  should now be unavailable

        library.returnBook(book);
        book.updateBook(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(true), Optional.empty());

        assertEquals(true, library.bookAvailability(book));
    }


}

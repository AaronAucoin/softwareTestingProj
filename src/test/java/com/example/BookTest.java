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

public class BookTest {
    private Library library;
    private Book book;

    @BeforeEach
    void setup() {
        library = mock(Library.class);
        book = new Book(
                "The Lion, The Witch, and The Wardrobe",
                "CS Lewis",
                1950,
                "9780001831803",
                "Fantasy",
                library
        );
    }

    // test book availability when a book is available
    @Test
    public void testBookAvailability() {
        assertTrue(book.checkAvailability());
    }

    // test book availability when a book is not available
    @Test
    public void testBookNotAvailable() {
        book.updateBook(Optional.empty(), Optional.empty(),
                Optional.empty(),Optional.empty(),
                Optional.of(false),Optional.empty());
        assertFalse(book.checkAvailability());
    }

    // Test getting book values
    @Test
    public void gettingBookValues() {
        List<String> bookInfo = book.getBookInfo();
        assertEquals("The Lion, The Witch, and The Wardrobe", bookInfo.get(0));
        assertEquals("CS Lewis", bookInfo.get(1));
        assertEquals(1950, Integer.parseInt(bookInfo.get(2)));
        assertEquals("9780001831803", bookInfo.get(3));
        assertTrue(Boolean.parseBoolean(bookInfo.get(5)));
        assertEquals("Fantasy", bookInfo.get(6));
    }

    // test updating book values
    @Test
    public void updatingBook() {
        book.updateBook(Optional.of("The Hobbit"), Optional.of("JRR Tolkien"),
                Optional.of(1937),Optional.of("9780345445605"),
                Optional.of(false),Optional.of("Fantasy"));
        List<String> bookInfo = book.getBookInfo();
        assertEquals("The Hobbit", bookInfo.get(0));
        assertEquals("JRR Tolkien", bookInfo.get(1));
        assertEquals(1937, Integer.parseInt(bookInfo.get(2)));
        assertEquals("9780345445605", bookInfo.get(3));
        assertFalse(Boolean.parseBoolean(bookInfo.get(5)));
        assertEquals("Fantasy", bookInfo.get(6));
    }
}

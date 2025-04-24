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

public class LibrariansTest {

    Librarians librarians;
    @BeforeEach
    void setUp() {
        librarians = new Librarians();
    }

    @Test
    void testGetAllLibrarianInformation() {
        String librariansInfo = """
Name: Alice
Authentication code: [0, 0, 0, 0, 0, 0]
Total Cash: 0.0
Books purchased by librarian: {}

Name: Bob
Authentication code: [1, 1, 1, 1, 1, 1]
Total Cash: 0.0
Books purchased by librarian: {}

Name: Carol
Authentication code: [2, 2, 2, 2, 2, 2]
Total Cash: 0.0
Books purchased by librarian: {}

                """;
        assertEquals(librariansInfo, librarians.getAllLibrariansInformation());
    }

    @Test
    void testGetLibrarianAuthenticationCodePass() {
        assertArrayEquals(new short[]{0,0,0,0,0,0}, librarians.getLibrarianAuthenticationCode("Alice"));
    }

    @Test
    void testGetLibrarianAuthenticationCodeNull() {
        assertNull(librarians.getLibrarianAuthenticationCode("Bill"));
    }

    @Test
    void testCheckLibrarianAuthenticationCodeTrue() {
        assertTrue(librarians.checkLibrarianAuthenticationCode("Bob", new short[]{1,1,1,1,1,1}));
    }

    @Test
    void testCheckLibrarianAuthenticationCodeFalse() {
        assertFalse(librarians.checkLibrarianAuthenticationCode("Carol", new short[]{1,1,1,1,1,1}));
        assertFalse(librarians.checkLibrarianAuthenticationCode("Bob", new short[]{0,0,0,0,0}));
    }

    @Test
    void testCheckLibrarianAuthenticationCodeNull() {
        assertNull(librarians.checkLibrarianAuthenticationCode("Bill", new short[]{1,1,1,1,1,1}));
        assertNull(librarians.checkLibrarianAuthenticationCode("Carol", null));
    }

    @Test
    void testWithdrawnSalary() {
        assertEquals(1000.0, librarians.addWithdrawnSalary("Alice", 1000.0));
        assertEquals(300.0, librarians.addWithdrawnSalary("Bob", 300.0));
        assertEquals(1700.0, librarians.addWithdrawnSalary("Carol", 1700.0));
        assertEquals(600.0, librarians.addWithdrawnSalary("Bob", 300.0));

        assertEquals(1000.0, librarians.getWithdrawnSalary("Alice"));
        assertEquals(600.0, librarians.getWithdrawnSalary("Bob"));
        assertEquals(1700.0, librarians.getWithdrawnSalary("Carol"));
    }

    @Test
    void testBookPurchasing() {
        Library library = mock(Library.class);
        Book narniaBook = new Book(
                "The Lion, The Witch, and The Wardrobe",
                "CS Lewis",
                1950,
                "9780001831803",
                "Fantasy",
                library
        );
        Book harryPotterBook = new Book(
                "Harry Potter",
                "JK Rowling",
                2007,
                "5793084902",
                "Fantasy",
                library
        );

        assertEquals(Map.of(narniaBook, 50.0), librarians.purchaseBook("Alice", narniaBook, 50));
        assertEquals(Map.of(narniaBook, 50.0), librarians.purchaseBook("Bob", narniaBook, 50));

        assertEquals(Map.of(narniaBook, 50.0), librarians.purchaseBook("Carol", narniaBook, 50));
        assertEquals(Map.of(narniaBook, 50.0), librarians.purchaseBook("Alice", harryPotterBook, 15));
    }
}

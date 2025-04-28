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

    // these are variable that will be used by tests
    Librarians librarians;
    Book narniaBook;
    Book harryPotterBook;
    Library library;


    // declare the variables that will be used for tests
    @BeforeEach
    void setUp() {
        library = mock(Library.class); // need to mock this to make Book objects
        when(library.generateBookID()).thenReturn(1); // mock behavior
        librarians = new Librarians(); // real instance of librarians class to test on

        // two books that we can use to test book purchasing logic
        narniaBook = mock(Book.class);
        harryPotterBook = mock(Book.class);
    }

    // tests the getAllLibrarianInformation function which returns all the information about all librarians
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

    //
    @Test
    void testGetLibrarianAuthenticationCodePass() {
        assertArrayEquals(new short[]{0,0,0,0,0,0}, librarians.getLibrarianAuthenticationCode("Alice"));
    }

    // tests getLibrarianAuthenticationCode for an nonexistent librarian
    @Test
    void testGetLibrarianAuthenticationCodeNull() {
        assertNull(librarians.getLibrarianAuthenticationCode("Bill"));
    }

    // Tests the checkLibrarianAuthenticateCode for the case that should return true
    // The only case for is inputting the correct code for the correct librarian name
    @Test
    void testCheckLibrarianAuthenticationCodeTrue() {
        assertTrue(librarians.checkLibrarianAuthenticationCode("Bob", new short[]{1,1,1,1,1,1}));
    }

    // Tests the checkLibrarianAuthenticateCode for both cases that should return false
    // These cases are: an incorrect authentication code, and an authentication code that is too short
    @Test
    void testCheckLibrarianAuthenticationCodeFalse() {
        assertFalse(librarians.checkLibrarianAuthenticationCode("Carol", new short[]{1,1,1,1,1,1}));
        assertFalse(librarians.checkLibrarianAuthenticationCode("Bob", new short[]{0,0,0,0,0}));
    }


    // Tests the checkLibrarianAuthenticateCode for both cases that should return null
    // these cases are: name of non-existent librarian, or inputting a null authentication code
    @Test
    void testCheckLibrarianAuthenticationCodeNull() {
        assertFalse(librarians.checkLibrarianAuthenticationCode("Bill", new short[]{1,1,1,1,1,1}));
        assertFalse(librarians.checkLibrarianAuthenticationCode("Carol", null));
    }

    // tests the functionality of adding to and retrieving a librarian's withdrawn salary
    // the first function does the adding, while second retrieves the withdrawn salary amount
    // it makes sure this function works for all predefined librarians
    @Test
    void testWithdrawnSalary() {
        // adds withdrawn salary to each librarians total withdrawn salary and asserts that it was added correctly
        assertEquals(1000.0, librarians.addWithdrawnSalary("Alice", 1000.0));
        assertEquals(300.0, librarians.addWithdrawnSalary("Bob", 300.0));
        assertEquals(1700.0, librarians.addWithdrawnSalary("Carol", 1700.0));
        assertEquals(600.0, librarians.addWithdrawnSalary("Bob", 300.0));

        // asserts that the correct amount is returned by getWithdrawnSalary()
        assertEquals(1000.0, librarians.getWithdrawnSalary("Alice"));
        assertEquals(600.0, librarians.getWithdrawnSalary("Bob"));
        assertEquals(1700.0, librarians.getWithdrawnSalary("Carol"));
    }

    // tests that null is returned when total salary is added to a nonexistent librarian
    // and that null is return when the total salary of a nonexistent librarian is gotten
    @Test
    void testWithdrawnSalaryNull() {
        assertNull(librarians.addWithdrawnSalary("Bill", 1000.0));
        assertEquals(-1.0, librarians.getWithdrawnSalary("Bill"));
    }

    // tests all Librarian BookPurchasing functionality,
    // including: purchaseBook(), getMapBooksPurchasedByLibrarian(), getListLibrarianPurchasedBooks(), and getLibrarianPurchasedBooksTotalValue()
    // test is long but involves setup code and testing that setup
    // breaking up this code would mean the setup would have to be repeated
    @Test
    void testBookPurchasing() {
        // has each librarian purchase books and asserts that their purchased book maps are returned correctly
        assertEquals(Map.of(narniaBook, 50.0), librarians.purchaseBook("Alice", narniaBook, 50));
        assertEquals(Map.of(narniaBook, 50.0), librarians.purchaseBook("Bob", narniaBook, 50));
        assertEquals(Map.of(harryPotterBook, 15.0), librarians.purchaseBook("Carol", harryPotterBook, 15));
        assertEquals(Map.of(narniaBook, 50.0, harryPotterBook, 15.0), librarians.purchaseBook("Alice", harryPotterBook, 15));

        // asserts that the getBooksPurchasedByLibrarian method returns correct values for each librarian
        assertEquals(Map.of(narniaBook, 50.0, harryPotterBook, 15.0), librarians.getMapBooksPurchasedByLibrarian("Alice"));
        assertEquals(Map.of(narniaBook, 50.0), librarians.getMapBooksPurchasedByLibrarian("Bob"));
        assertEquals(Map.of(harryPotterBook, 15.0), librarians.getMapBooksPurchasedByLibrarian("Carol"));

        // asserts that getListLibrarianPurchasedBook correctly returns the list of Book objects the librarian purchased
        assertThat(librarians.getListLibrarianPurchasedBooks("Alice")).containsExactlyInAnyOrder(narniaBook, harryPotterBook);
        assertThat(librarians.getListLibrarianPurchasedBooks("Alice")).containsExactlyInAnyOrder(narniaBook, harryPotterBook);
        assertEquals(List.of(narniaBook), librarians.getListLibrarianPurchasedBooks("Bob"));
        assertEquals(List.of(harryPotterBook), librarians.getListLibrarianPurchasedBooks("Carol"));

        // asserts that the getLibrarianPurchasedBookTotalValue returns
        // the correct sum of book costs bought by the named librarian
        assertEquals(65.0,librarians.getLibrarianPurchasedBooksTotalValue("Alice"));
        assertEquals(50.0,librarians.getLibrarianPurchasedBooksTotalValue("Bob"));
        assertEquals(15.0,librarians.getLibrarianPurchasedBooksTotalValue("Carol"));
    }

    // asserts that all book purchasing functions return null when expected
    // they should return null IFF a nonexistent librarians name is entered
    @Test
    void testPurchaseBookNull() {
        assertNull(librarians.purchaseBook("Bill", narniaBook, 50));
        assertNull(librarians.getMapBooksPurchasedByLibrarian("Bill"));
        assertNull(librarians.getListLibrarianPurchasedBooks("Bill"));
        assertNull(librarians.getLibrarianPurchasedBooksTotalValue("Bill"));
    }
}

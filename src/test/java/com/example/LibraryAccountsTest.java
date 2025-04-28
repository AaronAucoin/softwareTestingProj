package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LibraryAccountsTest {

    private LibraryAccounts libraryAccounts;
    private Purchasing mockPurchasing;
    private Librarians mockLibrarians;
    private Book mockBook;
    private String librarianName = "Alice";

    @BeforeEach
    void setUp() {
        //create mock classes of all related classes
        mockPurchasing = Mockito.mock(Purchasing.class);
        mockLibrarians = Mockito.mock(Librarians.class);
        libraryAccounts = new LibraryAccounts(mockPurchasing, mockLibrarians);
        mockBook = Mockito.mock(Book.class);
        //stub so getName() always return "The Hobbit". Easier than calling every test;
        when(mockBook.getName()).thenReturn("The Hobbit");
    }


    //DONATION

    //Ensure that the valid donation is accepted
    @Test
    void validDonation(){
        double amount = 1000;
        String result = libraryAccounts.addDonation(amount);
        assertTrue(result.contains("Donation of $1000.00"));//ensure that the result contains the proper amount of money donated
    }

    //Make sure that donations < 0 will return NULL;
    @Test
    void invalidDonation(){
        String result = libraryAccounts.addDonation(-1);
        assertNull(result); //addDonation will return null if negative donation
    }


    //SALARY

    @Test
    void withDrawSalaryValid(){
        String result = libraryAccounts.withdrawSalary(librarianName, 5000.00);
        assertNotNull(result);
        assertTrue(result.contains("Salary withdrawn: $5000.00"));

        //mock correct librarian name and amount were called in function;
        verify(mockLibrarians).addWithdrawnSalary(librarianName, 5000.00);
    }

    //ensure that when negative $ is taken for salary it returns NULL
    @Test
    void withdrawSalaryInvalidNegative(){
        String result = libraryAccounts.withdrawSalary(librarianName, -5000.00);
        //function returns NULL;
        assertNull(result);

        //mock to ensure that addWithdrawSalary() was never called given these args
        verify(mockLibrarians, never()).addWithdrawnSalary(librarianName, -5000.00);
    }

    //Ensure that when trying to withdraw over the current balance it returns null
    @Test
    void withdrawSalaryInvalidTooMuch(){
        String result = libraryAccounts.withdrawSalary(librarianName, 1000000.00);
        assertNull(result);

        //mock function to ensure that the correct args were passed
        verify(mockLibrarians, never()).addWithdrawnSalary(librarianName, 1000000.00);
    }

    //BOOK ORDER

    // assert that a book object is ordered when the cost is positive
    // verify that the purchasing class purchaseBook() method is called correctly
    @Test
    void testOrderBookValid(){
        when(mockPurchasing.generateRandomBookCost()).thenReturn(25.0);
        String result = libraryAccounts.orderBook(librarianName, mockBook);

        assertTrue(result.contains("The Hobbit"));
        verify(mockPurchasing).generateRandomBookCost();
        verify(mockLibrarians).purchaseBook(librarianName, mockBook, 25.0);
    }

    // assert that null is returned when trying to order a negative cost book
    // verify that purchase book is not called when attempting to purchase a negative cost book
    @Test
    void testOrderBookCostZeoOrNegative(){
        when(mockPurchasing.generateRandomBookCost()).thenReturn(0.0);
        String result = libraryAccounts.orderBook(librarianName, mockBook);
        assertNull(result);
        verify(mockPurchasing).generateRandomBookCost();
        verify(mockLibrarians, never()).purchaseBook(librarianName, mockBook, 0);
    }

    // assert that correct error string is returned when orderBook cost is higher than operating budget
    // verify the purchaseBook() is not called when book cost is too high
    @Test
    void testOrderBookCostTooMuch(){
        libraryAccounts.withdrawSalary(librarianName, 39000.0);
        when(mockPurchasing.generateRandomBookCost()).thenReturn(15.0);
        String result = libraryAccounts.orderBook(librarianName, mockBook);
        assertEquals("Book cost: 15.00 is higher than operating balance: 0.00", result);
        verify(mockLibrarians, never()).purchaseBook(librarianName, mockBook, 15.0);
    }
}

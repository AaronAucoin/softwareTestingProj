package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class LibraryAccountsTest {

    private LibraryAccounts libraryAccounts;
    private Purchasing mockPurchasing;

    @BeforeEach
    void setUp() {
        mockPurchasing = Mockito.mock(Purchasing.class);
        libraryAccounts = new LibraryAccounts(mockPurchasing);
    }


    //DONATION

    //Ensure that the valid donation is accepted
    @Test
    void validDonation(){
        double amount = 1000;
        String result = libraryAccounts.addDonation(amount);
        assertTrue(result.contains("Donation of $1000.00"));//ensure that the result contains the proper amount of money donated
    }

    @Test
    void invalidDonation(){
        String result = libraryAccounts.addDonation(-1);
        assertNull(result); //addDonation will return null if negative donation
    }


    //SALARY

    @Test
    void withDrawSalaryValid(){
        String result = libraryAccounts.withdrawSalary(5000.00);
        assertNotNull(result);
        assertTrue(result.contains("Salary withdrawn: $5000.00"));
    }

    //ensure that when negative $ is taken for salary it returns NULL
    @Test
    void withdrawSalaryInvalidNegative(){
        String result = libraryAccounts.withdrawSalary(-5000.00);
        assertNull(result);
    }

    //Ensure that when trying to withdraw over the current balance it returns null
    @Test
    void withdrawSalaryInvalidTooMuch(){
        String result = libraryAccounts.withdrawSalary(1000000.00);
        assertNull(result);
    }

    //BOOK ORDER

    @Test
    void testOrderBookValid(){
       String result = libraryAccounts.orderBook("The Hobbit", 150);
       assertNotNull(result);
       assertTrue(result.contains("The Hobbit"));

       verify(mockPurchasing).purchaseBook("The Hobbit", 150);
    }

    @Test
    void testOrderBookCostNegative(){
        String result = libraryAccounts.orderBook("The Hobbit", -1);
        assertNull(result);
        verify(mockPurchasing, never()).purchaseBook(anyString(), anyDouble());
    }

    @Test
    void testOrderBookCostTooMuch(){
        String result = libraryAccounts.orderBook("The Hobbit", 1000000.00);
        assertEquals("Book cost is higher than operating balance", result);

        verify(mockPurchasing, never()).purchaseBook(anyString(), anyDouble());
    }
}

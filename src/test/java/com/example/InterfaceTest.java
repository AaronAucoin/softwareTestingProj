package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.SyncFailedException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InterfaceTest {
    private Library library;
    private LibraryAccounts mockLibraryAccounts;
    private Librarians librarians;


    @BeforeEach
    void setUp() {
        library = new Library();
        mockLibraryAccounts = Mockito.mock(LibraryAccounts.class);
        librarians = new Librarians();
        Scanner scanner = new Scanner(System.in);
    }

    // function for inputting fake input strings and getting the result of the interface from that
    private String fakeInput(String fakeInput) {
        // Step 1: Capture output first
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputBuffer));

        // Step 2: Simulate input (must be set before calling loadInterface)
        // Simulated input for the test
        System.setIn(new ByteArrayInputStream(fakeInput.getBytes()));

        // Step 3: Create a mock Scanner that reads from the simulated input
        Scanner mockScanner = new Scanner(System.in);

        // Step 4: Initialize the Interface with the mock Scanner
        Interface interfaceTest = new Interface(library, mockLibraryAccounts, librarians, mockScanner);

        // Step 5: Call the function to trigger login (or any other I/O operations in loadInterface)
        try {
            interfaceTest.loadInterface();
            // Adding a short delay to allow time for asynchronous tasks (if any)
            Thread.sleep(500); // 500ms delay; adjust if necessary
        } catch (Exception e) {
            System.err.println("Exception during loadInterface execution: " + e.getMessage());
            e.printStackTrace();
        }

        // Step 6: Check the output after running loadInterface
        String capturedOutput = outputBuffer.toString();
        return capturedOutput;
    }


    @Test
    public void testLogInNonFullTimeLibrarian() {
        PrintStream originalOut = System.out; // Save original System.out

        String capturedOutput = fakeInput("John\n0\n0\n");
        // Step 7: Perform assertions to check if the expected behavior occurred
        assertFalse(capturedOutput.contains("Authentication successful. Full-time librarian access granted."));
        assertTrue(capturedOutput.contains("Limited access granted"));
        assertTrue(capturedOutput.contains("--- Main Menu ---"));
        assertTrue(capturedOutput.contains("Exiting system. Goodbye!"));

        // Step 8: Restore original System.out to prevent side effects on other tests
        System.setOut(originalOut);
    }


    @Test
    public void testLogInFullTimeLibrarian() {
        PrintStream originalOut = System.out; // Save original System.out

        String capturedOutput = fakeInput("Alice\n000000\n0\n");

        // Step 7: Perform assertions to check if the expected behavior occurred
        assertTrue(capturedOutput.contains("Authentication successful. Full-time librarian access granted."));
        assertFalse(capturedOutput.contains("Limited access granted"));
        assertTrue(capturedOutput.contains("--- Main Menu ---"));

        // Step 8: Restore original System.out to prevent side effects on other tests
        System.setOut(originalOut);
    }

    // test any more log in stuff here

    // test functions 1-3 here

    // test functions 4-6 here

    // test functions 7-9 here
    // testing function removeMember (option 7)
    @Test
    public void testRemoveMember() {
        PrintStream originalOut = System.out; // Save original System.out

        String capturedOutput = fakeInput("Bob\n111111\n7\nSmith, John\n1\n0\n");

        // Step 7: Perform assertions to check if the expected behavior occurred
        assertTrue(capturedOutput.contains("Member removed successfully."));

        // Step 8: Restore original System.out to prevent side effects on other tests
        System.setOut(originalOut);
        System.out.println(capturedOutput);
    }

    // testing function printMemberInfo
    @Test
    public void testPrintMemberInfo() {
        PrintStream originalOut = System.out;

        String capturedOutput = fakeInput("Bob\n111111\n8\n1\n0\n");

        // Step 7: Perform assertions to check if the expected behavior occurred
        assertTrue(capturedOutput.contains("Name: Smith, John"));

        // Step 8: Restore original System.out to prevent side effects on other tests
        System.setOut(originalOut);
        System.out.println(capturedOutput);
    }

    // testing addDonation of valid (positive) amount
    @Test
    public void testAddDonationValidAmount() {
        PrintStream originalOut = System.out;

        when(mockLibraryAccounts.addDonation(1000)).thenReturn("Donation of $1000.00 added. New balance: $40000.00");
        String capturedOutput = fakeInput("Bob\n111111\n9\n1000\n0\n");

        // Step 7: Perform assertions to check if the expected behavior occurred
        assertTrue(capturedOutput.contains("Enter donation amount:"));
        assertTrue(capturedOutput.contains("Donation of $1000.00 added. New balance: $40000.00"));
        verify(mockLibraryAccounts).addDonation(1000);


        // Step 8: Restore original System.out to prevent side effects on other tests
        System.setOut(originalOut);
        System.out.println(capturedOutput);
    }

    // testing addDonation (Function 9) of valid (negative) amount
    @Test
    public void testAddDonationInvalidAmount() {
        PrintStream originalOut = System.out;

        when(mockLibraryAccounts.addDonation(-1000)).thenReturn(null);
        String capturedOutput = fakeInput("Bob\n111111\n9\n-1000\n0\n");

        // Step 7: Perform assertions to check if the expected behavior occurred
        assertTrue(capturedOutput.contains("Enter donation amount:"));
        assertTrue(capturedOutput.contains("Donation amount attempted is invalid: -1000"));
        verify(mockLibraryAccounts).addDonation(-1000);


        // Step 8: Restore original System.out to prevent side effects on other tests
        System.setOut(originalOut);
        System.out.println(capturedOutput);
    }

    // test functions 10-12 here
    // testing withdraw salary amount (option 10) with valid amount
    @Test
    public void testWithdrawSalaryValidAmount() {
        PrintStream originalOut = System.out; // Save original System.out

        // stub behavior of withdraw salary so correct string is returned
        when(mockLibraryAccounts.withdrawSalary("Alice", 100)).thenReturn("Salary withdrawn: $100.00. New balance: $38900.00");

        String capturedOutput = fakeInput("Alice\n000000\n10\n100\n0\n");

        // Step 7: Perform assertions to check if the expected behavior occurred
        verify(mockLibraryAccounts).withdrawSalary("Alice", 100);
        assertTrue(capturedOutput.contains("Enter salary amount to withdraw:"));
        assertTrue(capturedOutput.contains("Salary withdrawn: $100.00. New balance: $38900.00"));
        assertTrue(capturedOutput.contains("Exiting system. Goodbye!"));

        // Step 8: Restore original System.out to prevent side effects on other tests
        System.setOut(originalOut);
        System.out.println(capturedOutput);
    }

    // testing withdraw salary amount (option 10) with invalid amount because amount is negative
    @Test
    public void testWithdrawSalaryInvalidNegativeAmount() {
        PrintStream originalOut = System.out; // Save original System.out

        // stub behavior of withdraw salary so null is returned for invalid amount
        when(mockLibraryAccounts.withdrawSalary("Alice", -100)).thenReturn(null);

        String capturedOutput = fakeInput("Alice\n000000\n10\n-100\n0\n");

        // Step 7: Perform assertions to check if the expected behavior occurred
        verify(mockLibraryAccounts).withdrawSalary("Alice", -100);
        assertTrue(capturedOutput.contains("Enter salary amount to withdraw:"));
        assertFalse(capturedOutput.contains("Salary withdrawn: $100.00. New balance: $38900.00"));
        assertTrue(capturedOutput.contains("Salary amount attempted is invalid: " + -100));
        assertTrue(capturedOutput.contains("Exiting system. Goodbye!"));

        // Step 8: Restore original System.out to prevent side effects on other tests
        System.setOut(originalOut);
        System.out.println(capturedOutput);
    }

    // testing withdraw salary amount (option 10) with invalid amount because amount is too high
    @Test
    public void testWithdrawSalaryInvalidTooHighAmount() {
        PrintStream originalOut = System.out; // Save original System.out

        // stub behavior of withdraw salary so null is returned for invalid amount
        when(mockLibraryAccounts.withdrawSalary("Alice", 4000000)).thenReturn(null);

        String capturedOutput = fakeInput("Alice\n000000\n10\n4000000\n0\n");

        // Step 7: Perform assertions to check if the expected behavior occurred
        verify(mockLibraryAccounts).withdrawSalary("Alice", 4000000);
        assertTrue(capturedOutput.contains("Enter salary amount to withdraw:"));
        assertFalse(capturedOutput.contains("Salary withdrawn: $100.00. New balance: $38900.00"));
        assertTrue(capturedOutput.contains("Salary amount attempted is invalid: " + 4000000));
        assertTrue(capturedOutput.contains("Exiting system. Goodbye!"));

        // Step 8: Restore original System.out to prevent side effects on other tests
        System.setOut(originalOut);
        System.out.println(capturedOutput);
    }

    // testing view operating balance (option 11)
    @Test
    public void testViewOperatingBalance() {
        PrintStream originalOut = System.out; // Save original System.out

        // stub behavior of getOperatingCash so correct value is returned
        when(mockLibraryAccounts.getOperatingCash()).thenReturn(39000.0);

        String capturedOutput = fakeInput("Alice\n000000\n11\n0\n");

        // Step 7: Perform assertions to check if the expected behavior occurred
        assertTrue(capturedOutput.contains("Current Operating Balance: $39000.0"));
        assertTrue(capturedOutput.contains("Exiting system. Goodbye!"));

        // Step 8: Restore original System.out to prevent side effects on other tests
        System.setOut(originalOut);
        System.out.println(capturedOutput);
    }

    // testing viewPurchasesAndSalaries (option 12)
    @Test
    public void testViewPurchasesAndSalaries() {
        PrintStream originalOut = System.out;
        List<Book> bookList = new ArrayList<Book>() {{
            mock(Book.class);
        }};

        String capturedOutput = fakeInput("Alice\n000000\n12\n0\n");

        assertTrue(capturedOutput.contains("Purchased Books:"));

        System.setOut(originalOut);
        System.out.println(capturedOutput);
    }
}

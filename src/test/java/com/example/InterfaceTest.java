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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

public class InterfaceTest {
    private Library mockLibrary;
    private LibraryAccounts mockLibraryAccounts;
    private Librarians librarians;


    @BeforeEach
    void setUp() {
        mockLibrary = Mockito.mock(Library.class);
        mockLibraryAccounts = Mockito.mock(LibraryAccounts.class);
        librarians = new Librarians();
        Scanner scanner = new Scanner(System.in);
    }

    // function for inputting fake strings and getting the result of the interface from that
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
        Interface interfaceTest = new Interface(mockLibrary, mockLibraryAccounts, librarians, mockScanner);

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

    // test functions 10-12 here
    @Test
    public void testWithdrawSalaryValidAmount() {
        PrintStream originalOut = System.out; // Save original System.out

        when(mockLibraryAccounts.withdrawSalary("Alice", 100)).thenReturn("Salary withdrawn: $100.00. New balance: $38900.00");

        String capturedOutput = fakeInput("Alice\n000000\n10\n100\n0\n");

        // Step 7: Perform assertions to check if the expected behavior occurred
        assertTrue(capturedOutput.contains("Authentication successful. Full-time librarian access granted."));
        assertFalse(capturedOutput.contains("Limited access granted"));
        assertTrue(capturedOutput.contains("--- Main Menu ---"));
        assertTrue(capturedOutput.contains("Enter salary amount to withdraw:"));
        assertTrue(capturedOutput.contains("Salary withdrawn: $100.00. New balance: $38900.00"));
        assertTrue(capturedOutput.contains("Exiting system. Goodbye!"));

        // Step 8: Restore original System.out to prevent side effects on other tests
        System.setOut(originalOut);
        System.out.println(capturedOutput);
    }
}

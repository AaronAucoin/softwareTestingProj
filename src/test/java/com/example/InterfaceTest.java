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


    @Test
    public void testLogInNonFullTimeLibrarian() {
        // Step 1: Capture output first
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        PrintStream originalOut = System.out; // Save original System.out
        System.setOut(new PrintStream(outputBuffer));

        // Step 2: Simulate input (must be set before calling loadInterface)
        String simulatedInput = "John\n0\n";  // Simulated input for the test
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

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

        // Step 7: Perform assertions to check if the expected behavior occurred
        assertFalse(capturedOutput.contains("Authentication successful. Full-time librarian access granted."));
        assertTrue(capturedOutput.contains("Limited access granted"));
        assertTrue(capturedOutput.contains("--- Main Menu ---"));

        // Step 8: Restore original System.out to prevent side effects on other tests
        System.setOut(originalOut);
    }




    @Test
    public void testLogInFullTimeLibrarian() {
        // Step 1: Capture output first
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        PrintStream originalOut = System.out; // Save original System.out
        System.setOut(new PrintStream(outputBuffer));

        // Step 2: Simulate input (must be set before calling loadInterface)
        String simulatedInput = "Alice\n000000\n0\n";  // Simulated input for the test
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

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

        // Step 7: Perform assertions to check if the expected behavior occurred
        assertTrue(capturedOutput.contains("Authentication successful. Full-time librarian access granted."));
        assertFalse(capturedOutput.contains("Limited access granted"));
        assertTrue(capturedOutput.contains("--- Main Menu ---"));

        // Step 8: Restore original System.out to prevent side effects on other tests
        System.setOut(originalOut);
    }
}

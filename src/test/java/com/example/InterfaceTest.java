package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class InterfaceTest {
    private Library library;
    private LibraryAccounts mockLibraryAccounts;
    private Librarians librarians;
    private InputStream originalIn;
    private PrintStream originalOut;
    private Map<Integer, Book> books = new HashMap<>();

    @BeforeEach
    void setUp() {
        library = new Library();
        mockLibraryAccounts = mock(LibraryAccounts.class);
        librarians = new Librarians();
        originalIn = System.in;
        originalOut = System.out;
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
    // Test adding Book function
    @Test
    public void testAddBook() {
        PrintStream originalOut = System.out;
        String input = "Alice\n000000\n1\nTest Book\nTest Author\n2024\n1234567890\nFantasy\n0\n";
        String capturedOutput = fakeInput(input);

        // Verify output
        assertTrue(capturedOutput.contains("Book added successfully."));
        assertTrue(capturedOutput.contains("--- Main Menu ---"));

        System.setOut(originalOut);
    }
    // removing book
    @Test
    public void testRemoveBook() {
        PrintStream originalOut = System.out;

        // First, manually add a book so it exists
        Book book = new Book("Test Book", "Test Author", 2024, "1234567890", "Fantasy", library);
        library.addBook(book);

        // Simulate: login, option 2 (remove book), provide the title, then exit
        String input = "Alice\n000000\n2\nTest Book\n0\n";
        String capturedOutput = fakeInput(input);

        assertTrue(capturedOutput.contains("Book removed successfully.")
                || capturedOutput.contains("Book test book has been removed"));

        System.setOut(originalOut);
    }
    // then checking out the book
    @Test
    public void testCheckoutBook() {
        PrintStream originalOut = System.out;

        // Setup: Add a book and a member first
        Book book = new Book("Test Book", "Test Author", 2024, "1234567890", "Fantasy", library);
        library.addBook(book);
        Member member = library.addMember("John Doe", "john@example.com");

        // Corrected fake input
        String input = String.join("\n",
                "Alice",
                "000000",
                "3",
                "Test Book",
                "John Doe",
                String.valueOf(member.getMemberId()),  // member id
                "0"
        );
        String capturedOutput = fakeInput(input);

        assertTrue(capturedOutput.contains("Book checked out successfully."), "Captured output should contain expected checkout message.");
        System.setOut(originalOut);
    }


// test functions 4-6 here
    //option 4:
    //returningBook testing here
    public boolean returnBook(int bookId) {
        Book book = books.get(bookId);
        if (book == null || book.checkAvailability()) {
            return false;
        }
        Member member = book.getBorrowedBy();
        if (member == null) {
            return false;
        }
        member.removeBorrowedBook(book);
        book.updateBook(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(true), // mark available
                Optional.empty()
        );
        return true;
    }



    // testing printAllBooksList (option 5)
    @Test
    public void testPrintAllBooksList() {
        PrintStream originalOut = System.out;

        // Add book
        Book book = new Book("Sample Book", "Sample Author", 2024, "9876543210", "Science", library);
        library.addBook(book);

        // login fulltime, select 5 to print all books, then exit
        String input = "Alice\n000000\n5\n0\n";
        String capturedOutput = fakeInput(input);

        assertTrue(capturedOutput.contains("Sample Book"), "Should print the book name in the list");

        System.setOut(originalOut);
    }

    // testing addMember (option 6)
    @Test
    public void testAddMember() {
        PrintStream originalOut = System.out;

        // Simulate:   login fulltime, select 6 to add member, input name/email, then exit
        String input = "Alice\n000000\n6\nJohn Doe\njohn@example.com\n0\n";
        String capturedOutput = fakeInput(input);

        assertTrue(capturedOutput.contains("Member added successfully."), "Should confirm member added");

        System.setOut(originalOut);
    }

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

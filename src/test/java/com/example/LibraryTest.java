package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private Library library;
    private Book book;
    private Member member;
    private Interface UI;

    @BeforeEach
    public void setUp() {
        library = new Library();
        book = new Book("Test Book", "Author Name", 2020, "123456",  "Fiction", library);
        member = new Member("Doe, John", "john.doe@example.com", 1);
        UI = new Interface();
    }

    @Test
    public void testAddBook() {
        library.addBook(book);
        Book foundBook = library.findBookIdByName("Test Book");
        assertNotNull(foundBook);
        assertEquals("Test Book", foundBook.getBookInfo().get(0));
    }

    @Test
    public void testRemoveBook() {
        library.addBook(book);
        library.removeBook(book);
        assertNull(library.findBookIdByName("Test Book"));
    }

    @Test
    public void testAddMember() {
        Member addedMember = library.addMember("Doe, John", "john.doe@example.com");
        assertEquals("Doe, John", addedMember.getName());
        assertEquals(1, addedMember.getMemberId());
    }

    @Test
    public void testRemoveMember() {
        Member addedMember = library.addMember("Doe, John", "john.doe@example.com");
        library.removeMember(addedMember);
        assertNull(library.getMember("Doe, John", 1));
    }

    @Test
    public void testCheckoutAndReturnBook() {
        library.addBook(book);
        Member addedMember = library.addMember("Doe, John", "john.doe@example.com");
        library.checkoutBook(book, addedMember);

        assertFalse(book.checkAvailability()); // book availability not updated in code
        // To improve design: set `isAvailable = false` in `checkoutBook()` and `true` in `returnBook()`

        library.returnBook(book);
        // Again, `isAvailable` should be updated but isn't. Fix this in your main logic.
        // whoever wrote this comment could have fixed this lol
    }


    //CLI tests beyond this point. BEWARE
    @Test
    public void testCLIAddBookAndListBooks() throws Exception {
        String input = String.join(System.lineSeparator(),
                "1",                      // Choose "Add Book"
                "CLI Book",              // Title
                "CLI Author",            // Author
                "2022",                  // Year
                "999999",                // ISBN
                "Fantasy",               // Genre
                "10"                     // Exit
        );

        InputStream sysInBackup = System.in; // backup System.in
        PrintStream sysOutBackup = System.out; // backup System.out

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printOut = new PrintStream(out);

        System.setIn(in);
        System.setOut(printOut);

        Library cliLibrary = new Library();
        UI.doInterface(cliLibrary); // assuming your Interface class has mainMenu(Library)

        System.setIn(sysInBackup);
        System.setOut(sysOutBackup);

        String output = out.toString();

        assertNotNull(cliLibrary.findBookIdByName("cli book"));
    }

    @Test
    public void testCLIListBooksAfterAdding() throws Exception {
        Interface ui = new Interface();
        String input = String.join(System.lineSeparator(),
                "1",                 // Add Book
                "Listed Book",       // Title
                "List Author",       // Author
                "2021",              // Year
                "888888",            // ISBN
                "Sci-Fi",            // Genre
                "2",                 //Remove book
                "Listed Book",       //Name of removed book
                "10"                 //EXIT
        );

        InputStream sysInBackup = System.in;
        PrintStream sysOutBackup = System.out;

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printOut = new PrintStream(out);

        System.setIn(in);
        System.setOut(printOut);

        Library cliLibrary = new Library();
        ui.doInterface(cliLibrary);

        System.setIn(sysInBackup);
        System.setOut(sysOutBackup);

        String output = out.toString();

        assertNull(cliLibrary.findBookIdByName("Listed Book"));

//        assertTrue(output.contains("Listed Book"), "Expected book title not found in output");
//        assertTrue(output.contains("List Author"), "Expected author not found in output");
    }

    @Test
    public void testCLIAddMultipleBooks() throws Exception {
        Interface ui = new Interface();
        String input = String.join(System.lineSeparator(),
                "1",                 // Add First Book
                "First Book",        // Title
                "Author One",        // Author
                "2020",              // Year
                "123456",            // ISBN
                "Fiction",           // Genre
                "1",                 // Add Second Book
                "Second Book",       // Title
                "Author Two",        // Author
                "2021",              // Year
                "654321",            // ISBN
                "Non-Fiction",       // Genre
                "11",                // List Books
                "10"                 // Exit
        );

        InputStream sysInBackup = System.in;
        PrintStream sysOutBackup = System.out;

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printOut = new PrintStream(out);

        System.setIn(in);
        System.setOut(printOut);

        Library cliLibrary = new Library();
        ui.doInterface(cliLibrary);

        System.setIn(sysInBackup);
        System.setOut(sysOutBackup);

        String output = out.toString();

        assertTrue(output.contains("first book"), "First book");
        assertTrue(output.contains("second book"), "second book");
    }

    //test to make sure that book gets checkout out to proper member
    @Test
    public void testUserBook() throws Exception {
        Interface ui = new Interface();
        String input = String.join(System.lineSeparator(),
                "1",                 // Add First Book
                "First Book",        // Title
                "Author One",        // Author
                "2020",              // Year
                "123456",            // ISBN
                "Fiction",           // Genre
                "4",
                "Aucoin Aaron",
                "aaron@lsu.edu",
                "6",
                "First Book",
                "1",
                "10"                 // Exit
        );

        InputStream sysInBackup = System.in;
        PrintStream sysOutBackup = System.out;

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printOut = new PrintStream(out);

        System.setIn(in);
        System.setOut(printOut);

        Library cliLibrary = new Library();
        ui.doInterface(cliLibrary);

        System.setIn(sysInBackup);
        System.setOut(sysOutBackup);

        String output = out.toString();
//test to ensure that book is added to users checked out list
//        assertTrue(output.contains("book checked out successfully."),"checkout book success");
    }
}


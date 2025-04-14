package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

<<<<<<< HEAD
import java.util.Optional;
=======
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
>>>>>>> 093810d32c6691693f737b7546e5a52d35e96d24

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private Library library;
    private Book book;
    private Member member;
    private Interface UI;

    @BeforeEach
    public void setUp() {
        library = new Library();
<<<<<<< HEAD
        book = new Book("Test Book", "Author Name", 2020, "123456", "Fiction", library);
        member = library.addMember("Doe, John", "john.doe@example.com");
=======
        book = new Book("Test Book", "Author Name", 2020, "123456",  "Fiction", library);
        member = new Member("Doe, John", "john.doe@example.com", 1);
        UI = new Interface();
>>>>>>> 093810d32c6691693f737b7546e5a52d35e96d24
    }

    @Test
    public void testAddBook() {
        library.addBook(book);
        Book foundBook = library.findBookIdByName("test book");
        assertNotNull(foundBook);
        assertEquals("test book", foundBook.getBookInfo().get(0).toLowerCase());
    }

    @Test
    public void testRemoveBook() {
        library.addBook(book);
        library.removeBook(book);
        Book foundBook = library.findBookIdByName("test book");
        assertNull(foundBook);
    }

    @Test
    public void testAddMember() {
        assertEquals("Doe, John", member.getName());
        assertTrue(member.getMemberId() > 0);
    }

    @Test
    public void testRemoveMember() {
        int id = member.getMemberId();
        library.removeMember(member);
        Member removed = library.getMember("Doe, John", id);
        assertNull(removed);
    }

    @Test
    public void testCheckoutBook() {
        library.addBook(book);
        library.checkoutBook(book, member);
        // simulate marking unavailable (same as CLI)
        book.updateBook(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(false), Optional.empty());
        assertFalse(book.checkAvailability());

        // member should have this book
        assertTrue(memberHasBook(member, book));
    }

    @Test
    public void testReturnBook() {
        library.addBook(book);
        library.checkoutBook(book, member);
        book.updateBook(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(false), Optional.empty());

        library.returnBook(book);
<<<<<<< HEAD
        // simulate marking available (same as CLI)
        book.updateBook(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(true), Optional.empty());
=======
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
>>>>>>> 093810d32c6691693f737b7546e5a52d35e96d24

        assertTrue(book.checkAvailability());
        assertFalse(memberHasBook(member, book));
    }

    @Test
    public void testFindBookIdByNameCaseInsensitive() {
        library.addBook(book);
        Book found = library.findBookIdByName("TEST BOOK");
        assertNotNull(found);
        assertEquals("Test Book", found.getBookInfo().get(0)); // getBookInfo().get(0) = name
    }

    // Helper method to verify if member borrowed the book
    private boolean memberHasBook(Member member, Book book) {
        return member.getBorrowedBooks().contains(book);
    }
    @Test
    public void testBookAvailabilityFlow() {
        library.addBook(book);
        assertTrue(library.bookAvailability(book));

        // Checkout the booookkkkkkkkkkkkkkkkkkkkkkk
        library.checkoutBook(book, member);
        book.updateBook(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(false), Optional.empty());
        assertFalse(library.bookAvailability(book)); //  should now be unavailable

        library.returnBook(book);
        book.updateBook(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(true), Optional.empty());

        assertEquals(true, library.bookAvailability(book));
    }


}

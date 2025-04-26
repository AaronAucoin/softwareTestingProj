package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InterfaceRedoneTest {
    private InterfaceRedone ui;
    private Library mockLibrary;
    private Book mockBook;
    private Member mockMember;

    @BeforeEach
    public void setup() {
        ui = new InterfaceRedone();
        mockLibrary = mock(Library.class);
        mockBook = mock(Book.class);
        mockMember = mock(Member.class);
    }

    @Test
    public void testAddBook() {
        String input = "The Hobbit\nJ.R.R. Tolkien\n1937\n1234\nFantasy\n";
        Scanner scanner = new Scanner(input);

        // Simulate addBook calling lib.addBook(book)
        doNothing().when(mockLibrary).addBook(any(Book.class));

        ui.addBook(scanner, mockLibrary);

        verify(mockLibrary, times(1)).addBook(any(Book.class));
    }

    @Test
    public void testRemoveBook() {
        String input = "The Hobbit\n";
        Scanner scanner = new Scanner(input);
        when(mockLibrary.findBookIdByName("the hobbit")).thenReturn(mockBook);

        ui.removeBook(scanner, mockLibrary);

        verify(mockLibrary).removeBook(mockBook);
    }

    @Test
    public void testAddMember() {
        String input = "Doe, John\njohn@example.com\n";
        Scanner scanner = new Scanner(input);

        when(mockLibrary.addMember("Doe, John", "john@example.com")).thenReturn(mockMember);
        doNothing().when(mockMember).printMemberInfo();

        ui.addMember(scanner, mockLibrary);

        verify(mockLibrary).addMember("Doe, John", "john@example.com");
        verify(mockMember).printMemberInfo();
    }

    // @Test
    // public void testPrintAllBookNames() {
    // when(mockLibrary.getAllBooks()).thenReturn(List.of(mockBook));
    // doNothing().when(mockBook).getBookInfo();
    //
    // Scanner dummyScanner = new Scanner(""); // not used
    // ui.printAllBookNames(dummyScanner, mockLibrary);
    //
    // verify(mockBook, times(1)).getBookInfo();
    // }

    @Test
    public void testCheckoutBookValid() {
        String input = "The Hobbit\n123\n";
        Scanner scanner = new Scanner(input);

        when(mockLibrary.findBookIdByName("the hobbit")).thenReturn(mockBook);
        when(mockLibrary.getMember(null, 123)).thenReturn(mockMember);
        when(mockBook.checkAvailability()).thenReturn(true);
        doNothing().when(mockLibrary).checkoutBook(mockBook, mockMember);
        doNothing().when(mockBook).updateBook(any(), any(), any(), any(), any(), any());
        doNothing().when(mockMember).printMemberInfo();

        ui.checkoutBook(scanner, mockLibrary);

        verify(mockLibrary).checkoutBook(mockBook, mockMember);
        verify(mockMember).printMemberInfo();
    }

    @Test
    public void testPrintMemberInfoValid() {
        String input = "123\n";
        Scanner scanner = new Scanner(input);

        when(mockLibrary.getMember(null, 123)).thenReturn(mockMember);
        doNothing().when(mockMember).printMemberInfo();

        ui.printMemberInfo(scanner, mockLibrary);

        verify(mockMember).printMemberInfo();
    }

    @Test
    public void testPrintMemberInfoNotFound() {
        String input = "999\n";
        Scanner scanner = new Scanner(input);

        when(mockLibrary.getMember(null, 999)).thenReturn(null);

        ui.printMemberInfo(scanner, mockLibrary);
        // Nothing to verify, just check for graceful failure
    }
}

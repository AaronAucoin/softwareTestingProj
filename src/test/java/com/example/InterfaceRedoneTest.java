package com.example;

import org.h2.engine.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.devtools.v85.input.Input;

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

    //Test add member
    @Test
    public void testAddMember() {
        String input = "Doe, John\njohn@example.com\n";
        Scanner scanner = new Scanner(input);

        when(mockLibrary.addMember("Doe, John", "john@example.com")).thenReturn(mockMember); //when addMember is called with those args, return mockMember;
        doNothing().when(mockMember).printMemberInfo(); //when printMemberInfo() is called, doNothing();


        ui.addMember(scanner, mockLibrary); //calls the real addMember(). It takes in the fake scanner and member;

        verify(mockLibrary).addMember("Doe, John", "john@example.com"); //ensure that addMember() with those args is called;
        verify(mockMember).printMemberInfo(); //ensure that printMemberInfo() is also called;
    }

     @Test
     public void testPrintAllBookNames() {
     Library mockLibrary = mock(Library.class);
     Book mockBook = mock(Book.class);
     InterfaceRedone ui = new InterfaceRedone();

     when(mockLibrary.getAllBooks()).thenReturn(List.of(mockBook));

     Scanner dummyScanner = new Scanner("");
     ui.printAllBookNames(dummyScanner, mockLibrary);

     verify(mockBook, times(1)).getBookInfo();
     }

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

    @Test
    public void testRemoveMember(){
        Library mockLibrary = mock(Library.class);
        Member mockMember = mock(Member.class);
        InterfaceRedone ui = new InterfaceRedone();

        //Sim user input
        String input = "aucoin aaron\n123\n";
        Scanner scanner = new Scanner(input);

        //mocking reaction
        when(mockLibrary.getMember("aucoin aaron", 123)).thenReturn(mockMember);

        //actually run removeMember();
        ui.removeMember(scanner, mockLibrary);

        //verify that things did correctly
        verify(mockLibrary).getMember("aucoin aaron", 123);
        verify(mockLibrary).removeMember(mockMember);
    }

    @Test
    public void testUpdateBookTitle() {
        // Setup
        Library mockLibrary = mock(Library.class);
        Book mockBook = mock(Book.class);
        InterfaceRedone ui = new InterfaceRedone();

        //1. title to find
        //2. menu option 1 ("Update Title")
        // 3. new title "New Title"
        // 4. fourth input: exit ("9")
        String input = "some book title\n1\nNew Title\n9\n";
        Scanner scanner = new Scanner(input);

        // Mock the findBookIdByName behavior
        when(mockLibrary.findBookIdByName("some book title")).thenReturn(mockBook);

        // Act
        ui.updateBook(scanner, mockLibrary);

        // Assert
        verify(mockLibrary).findBookIdByName("some book title");
        verify(mockBook).updateBook(
                eq(Optional.of("New Title")),
                eq(Optional.empty()),
                eq(Optional.empty()),
                eq(Optional.empty()),
                eq(Optional.empty()),
                eq(Optional.empty())
        );
    }

}
